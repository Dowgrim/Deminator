import { GameRoom } from '../models/GameRoom.js';
import { ClientCell } from '../types/ClientCell.js';

export interface GameModeHandler {
  name: string;
  onStartGame(room: GameRoom): void;
  revealCell(room: GameRoom, playerId: string, row: number, col: number): boolean;
  toggleFlag(room: GameRoom, playerId: string, row: number, col: number): boolean;
  getClientBoard(room: GameRoom, playerId?: string): ClientCell[][];
  onPlayerDisconnect(room: GameRoom, playerId: string): void;
}
