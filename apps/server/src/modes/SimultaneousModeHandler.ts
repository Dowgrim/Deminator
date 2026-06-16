import { GameRoom } from '../models/GameRoom.js';
import { ClientCell } from '../types/ClientCell.js';
import { GameModeHandler } from './GameModeHandler.js';

export class SimultaneousModeHandler implements GameModeHandler {
  public name = 'simultaneous';

  public onStartGame(room: GameRoom): void {
    room.currentTurn = null;
    room.simultaneousPendingReveals.clear();
    room.simultaneousDonePlayers.clear();
  }

  public revealCell(room: GameRoom, playerId: string, row: number, col: number): boolean {
    if (room.status !== 'playing') return false;
    const player = room.players.get(playerId);
    if (!player || !player.isAlive) return false;

    // If already done with this turn, cannot click anymore
    if (room.simultaneousDonePlayers.has(playerId)) return false;

    const cell = room.serverBoard[row][col];
    // Cannot click cells already revealed on the main board or flagged
    if (cell.isRevealed || cell.isFlagged) return false;

    // Check if cell is already in player's pending reveals
    const pending = room.simultaneousPendingReveals.get(playerId) || [];
    const isAlreadyPending = pending.some(c => c.row === row && c.col === col);
    if (isAlreadyPending) return false;

    // Generate mines on first click in the room
    if (!room.firstClickDone) {
      room.generateMines(row, col);
      room.firstClickDone = true;
    }

    if (cell.isMine) {
      // It's a mine! Show locally immediately and reward points
      pending.push({ row, col });
      room.simultaneousPendingReveals.set(playerId, pending);
      player.score += 15; // Point reward for finding a bomb
      
      // Let's check if there are other cells to play. Turn is NOT done, they can click again.
    } else {
      // Safe cell! Turn is completed for this player
      pending.push({ row, col });
      room.simultaneousPendingReveals.set(playerId, pending);
      room.simultaneousDonePlayers.add(playerId);
    }

    // Check if all alive players are done
    this.checkAndResolveTurn(room);

    return true;
  }

  public toggleFlag(room: GameRoom, playerId: string, row: number, col: number): boolean {
    // Flagging is disabled in simultaneous turn-hunting mode
    return false;
  }

  private checkAndResolveTurn(room: GameRoom) {
    const alivePlayers = Array.from(room.players.values()).filter(p => p.isAlive);
    const allDone = alivePlayers.length > 0 && alivePlayers.every(p => room.simultaneousDonePlayers.has(p.id));

    if (allDone || alivePlayers.length === 0) {
      this.resolveTurn(room);
    }
  }

  private resolveTurn(room: GameRoom) {
    // Apply all pending reveals to the main board
    room.simultaneousPendingReveals.forEach((pendingCoords, playerId) => {
      const player = room.players.get(playerId);
      if (!player) return;

      pendingCoords.forEach(({ row, col }) => {
        const cell = room.serverBoard[row][col];
        if (cell.isRevealed) return; // Already revealed by someone else's resolve earlier in this loop

        if (cell.isMine) {
          cell.isRevealed = true;
          cell.revealedBy = playerId;
          room.minesRemaining--;
        } else {
          // Trigger cascade reveal
          room.revealCascade(row, col, playerId);
        }
      });
    });

    // Clear turn states
    room.simultaneousPendingReveals.clear();
    room.simultaneousDonePlayers.clear();

    // Broadcast system message
    room.addChatMessage('system', 'Le tour est terminé ! Les choix de tous les joueurs s\'appliquent à la grille.');

    // Check game end
    room.checkGameEndState();
  }

  public getClientBoard(room: GameRoom, playerId?: string): ClientCell[][] {
    const playerPending = playerId ? room.simultaneousPendingReveals.get(playerId) || [] : [];

    return room.serverBoard.map(row =>
      row.map(cell => {
        // Is this cell pending revealed by this player?
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

    if (room.status === 'playing') {
      this.checkAndResolveTurn(room);
      room.checkGameEndState();
    }
  }
}
