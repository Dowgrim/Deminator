import { GameRoom } from '../models/GameRoom.js';
import { ClientCell } from '../types/ClientCell.js';
import { GameModeHandler } from './GameModeHandler.js';

/**
 * "Chasse Évolutive" — like Chasse Simultanée but after each turn resolution,
 * any cell adjacent to a "finished" cell (all its neighboring mines are already
 * revealed) gets auto-revealed. This cascades until no more propagation is possible.
 */
export class SimultaneousAutoModeHandler implements GameModeHandler {
  public name = 'simultaneousAuto';

  public onStartGame(room: GameRoom): void {
    room.currentTurn = null;
    room.simultaneousPendingReveals.clear();
    room.simultaneousDonePlayers.clear();
    room.players.forEach(p => {
      p.isTurnDone = false;
    });
  }

  public revealCell(room: GameRoom, playerId: string, row: number, col: number): boolean {
    if (room.status !== 'playing') return false;
    const player = room.players.get(playerId);
    if (!player || !player.isAlive) return false;

    if (room.simultaneousDonePlayers.has(playerId)) return false;

    const cell = room.serverBoard[row][col];
    if (cell.isRevealed || cell.isFlagged) return false;

    const pending = room.simultaneousPendingReveals.get(playerId) || [];
    if (pending.some(c => c.row === row && c.col === col)) return false;

    if (!room.firstClickDone) {
      room.generateMines(row, col);
      room.firstClickDone = true;
    }

    if (cell.isMine) {
      pending.push({ row, col });
      room.simultaneousPendingReveals.set(playerId, pending);
      player.score += 15;
      player.minesFound += 1;
    } else {
      pending.push({ row, col });
      room.simultaneousPendingReveals.set(playerId, pending);
      room.simultaneousDonePlayers.add(playerId);
      player.isTurnDone = true;
    }

    this.checkAndResolveTurn(room);
    return true;
  }

  public toggleFlag(_room: GameRoom, _playerId: string, _row: number, _col: number): boolean {
    return false;
  }

  private checkAndResolveTurn(room: GameRoom) {
    const alivePlayers = Array.from(room.players.values()).filter(p => p.isAlive);
    const doneCount = alivePlayers.filter(p => room.simultaneousDonePlayers.has(p.id)).length;

    if (alivePlayers.length === 0 || doneCount === alivePlayers.length) {
      room.clearTurnTimer();
      this.resolveTurn(room);
    } else if (doneCount > 0 && !room.isTimerActive) {
      this.startTurnTimerForRoom(room);
    }
  }

  private startTurnTimerForRoom(room: GameRoom): void {
    room.startTurnTimer(() => {
      if (room.status !== 'playing') return;
      const alivePlayers = Array.from(room.players.values()).filter(p => p.isAlive);
      alivePlayers.forEach(p => {
        if (!room.simultaneousDonePlayers.has(p.id)) {
          room.simultaneousDonePlayers.add(p.id);
          p.isTurnDone = true;
        }
      });
      this.resolveTurn(room);
    });
  }

  private resolveTurn(room: GameRoom) {
    // First pass: collect all players who clicked each mine this turn
    const mineRevealersMap = new Map<string, string[]>();
    room.simultaneousPendingReveals.forEach((pendingCoords, playerId) => {
      pendingCoords.forEach(({ row, col }) => {
        const cell = room.serverBoard[row][col];
        if (cell.isMine && !cell.isRevealed) {
          const key = `${row},${col}`;
          if (!mineRevealersMap.has(key)) mineRevealersMap.set(key, []);
          mineRevealersMap.get(key)!.push(playerId);
        }
      });
    });

    // Second pass: apply all pending reveals to the main board
    room.simultaneousPendingReveals.forEach((pendingCoords, playerId) => {
      const player = room.players.get(playerId);
      if (!player) return;

      pendingCoords.forEach(({ row, col }) => {
        const cell = room.serverBoard[row][col];
        if (cell.isRevealed) return;

        if (cell.isMine) {
          cell.isRevealed = true;
          cell.revealedBy = playerId;
          cell.mineRevealers = mineRevealersMap.get(`${row},${col}`) ?? [playerId];
          room.minesRemaining--;
        } else {
          room.revealCascade(row, col, playerId);
        }
      });
    });

    // Auto-propagate: reveal cells adjacent to "finished" cells
    this.propagateFinishedCells(room);

    // Clear turn states
    room.simultaneousPendingReveals.clear();
    room.simultaneousDonePlayers.clear();
    room.players.forEach(p => {
      p.isTurnDone = false;
    });

    room.addChatMessage('system', 'Le tour est terminé ! Les cases adjacentes aux cases finies ont été révélées automatiquement.');
    room.checkGameEndState();
  }

  /**
   * Iteratively reveals all safe cells adjacent to "finished" cells.
   * A cell is "finished" when the count of its revealed adjacent mines
   * equals its neighborMines value — meaning all remaining neighbors are safe.
   */
  private propagateFinishedCells(room: GameRoom): void {
    const rows = room.config.rows;
    const cols = room.config.cols;

    let changed = true;
    while (changed) {
      changed = false;

      for (let r = 0; r < rows; r++) {
        for (let c = 0; c < cols; c++) {
          const cell = room.serverBoard[r][c];
          if (!cell.isRevealed || cell.isMine || cell.neighborMines === 0) continue;

          // Count adjacent revealed mines
          let revealedMines = 0;
          const safeUnrevealed: [number, number][] = [];

          for (let dr = -1; dr <= 1; dr++) {
            for (let dc = -1; dc <= 1; dc++) {
              if (dr === 0 && dc === 0) continue;
              const nr = r + dr;
              const nc = c + dc;
              if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
              const neighbor = room.serverBoard[nr][nc];
              if (neighbor.isMine && neighbor.isRevealed) {
                revealedMines++;
              } else if (!neighbor.isRevealed && !neighbor.isFlagged && !neighbor.isMine) {
                safeUnrevealed.push([nr, nc]);
              }
            }
          }

          // Cell is "finished": all its mines are revealed → auto-reveal safe neighbors
          if (revealedMines === cell.neighborMines && safeUnrevealed.length > 0) {
            for (const [nr, nc] of safeUnrevealed) {
              if (!room.serverBoard[nr][nc].isRevealed) {
                // Use the original revealer's ID so the color attribution is consistent,
                // or 'system' if the cell was auto-revealed in a previous propagation.
                const revealerId = cell.revealedBy || 'system';
                room.revealCascade(nr, nc, revealerId);
                changed = true;
              }
            }
          }
        }
      }
    }
  }

  public getClientBoard(room: GameRoom, playerId?: string): ClientCell[][] {
    const playerPending = playerId ? room.simultaneousPendingReveals.get(playerId) || [] : [];

    return room.serverBoard.map(row =>
      row.map(cell => {
        const isPending = playerPending.some(c => c.row === cell.row && c.col === cell.col);

        const clientCell: ClientCell = {
          row: cell.row,
          col: cell.col,
          isRevealed: cell.isRevealed || isPending,
          isFlagged: cell.isFlagged,
          flaggedBy: cell.flaggedBy,
          revealedBy: cell.revealedBy || (isPending ? playerId || null : null),
        };

        if (cell.isRevealed || isPending || room.status === 'gameover' || room.status === 'won') {
          if (cell.isMine) {
            clientCell.isMine = true;
            if (cell.mineRevealers?.length) clientCell.mineRevealers = cell.mineRevealers;
          } else {
            clientCell.neighborMines = cell.neighborMines;
          }
        }

        return clientCell;
      })
    );
  }

  public onPlayerDisconnect(room: GameRoom, playerId: string): void {
    room.simultaneousDonePlayers.delete(playerId);
    room.simultaneousPendingReveals.delete(playerId);
    const player = room.players.get(playerId);
    if (player) {
      player.isTurnDone = false;
    }

    if (room.status === 'playing') {
      const alivePlayers = Array.from(room.players.values()).filter(p => p.isAlive);
      const doneCount = alivePlayers.filter(p => room.simultaneousDonePlayers.has(p.id)).length;
      if (doneCount === 0) {
        room.clearTurnTimer();
      }
      this.checkAndResolveTurn(room);
      room.checkGameEndState();
    }
  }
}
