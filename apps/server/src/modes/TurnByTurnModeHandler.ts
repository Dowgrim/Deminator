import { GameRoom } from '../models/GameRoom.js';
import { ClientCell } from '../types/ClientCell.js';
import { GameModeHandler } from './GameModeHandler.js';

export class TurnByTurnModeHandler implements GameModeHandler {
  public name = 'turnByTurn';

  public onStartGame(room: GameRoom): void {
    this.startTimerForCurrentPlayer(room);
  }

  public revealCell(room: GameRoom, playerId: string, row: number, col: number): boolean {
    if (room.status !== 'playing') return false;
    const player = room.players.get(playerId);
    if (!player || !player.isAlive) return false;

    if (room.currentTurn !== playerId) return false;

    const cell = room.serverBoard[row][col];
    if (cell.isRevealed || cell.isFlagged) return false;

    room.clearTurnTimer();

    // On first click, generate board to ensure clicked spot is clean and has 0 neighbors
    if (!room.firstClickDone) {
      room.generateMines(row, col);
      room.firstClickDone = true;
    }

    if (cell.isMine) {
      // Boom! In turnByTurn mode, clicking a bomb is actually the goal!
      // The cell is revealed, player is still alive, gets +15 points, and keeps their turn.
      cell.isRevealed = true;
      cell.revealedBy = playerId;
      player.score += 15;
      player.minesFound += 1;
      room.minesRemaining--;

      room.checkGameEndState();
      this.startTimerForCurrentPlayer(room);
      return true;
    }

    // Reveal and cascade if empty
    room.revealCascade(row, col, playerId);

    room.advanceTurn();
    room.checkGameEndState();
    this.startTimerForCurrentPlayer(room);
    return true;
  }

  public toggleFlag(room: GameRoom, playerId: string, row: number, col: number): boolean {
    if (room.status !== 'playing') return false;
    const player = room.players.get(playerId);
    if (!player || !player.isAlive) return false;

    if (room.currentTurn !== playerId) return false;

    const cell = room.serverBoard[row][col];
    if (cell.isRevealed) return false;

    room.clearTurnTimer();

    if (cell.isFlagged) {
      if (cell.isMine) {
        const originalFlagger = room.players.get(cell.flaggedBy || '');
        if (originalFlagger) originalFlagger.score = Math.max(0, originalFlagger.score - 10);
        room.minesRemaining++;
      } else {
        const originalFlagger = room.players.get(cell.flaggedBy || '');
        if (originalFlagger) originalFlagger.score += 2;
      }
      cell.isFlagged = false;
      cell.flaggedBy = null;
    } else {
      cell.isFlagged = true;
      cell.flaggedBy = playerId;

      if (cell.isMine) {
        player.score += 10;
        room.minesRemaining--;
      } else {
        player.score = Math.max(0, player.score - 2);
      }
    }

    this.startTimerForCurrentPlayer(room);
    return true;
  }

  public getClientBoard(room: GameRoom, _playerId?: string): ClientCell[][] {
    return room.serverBoard.map(row =>
      row.map(cell => {
        const clientCell: ClientCell = {
          row: cell.row,
          col: cell.col,
          isRevealed: cell.isRevealed,
          isFlagged: cell.isFlagged,
          flaggedBy: cell.flaggedBy,
          revealedBy: cell.revealedBy,
        };

        if (cell.isRevealed || room.status === 'gameover' || room.status === 'won') {
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
    if (room.currentTurn === playerId) {
      room.clearTurnTimer();
      room.advanceTurn();
      this.startTimerForCurrentPlayer(room);
    }
    if (room.status === 'playing') {
      room.checkGameEndState();
    }
  }

  private startTimerForCurrentPlayer(room: GameRoom): void {
    if (room.status !== 'playing') return;
    room.startTurnTimer(() => {
      if (room.status !== 'playing') return;
      room.advanceTurn();
      room.checkGameEndState();
    });
  }
}
