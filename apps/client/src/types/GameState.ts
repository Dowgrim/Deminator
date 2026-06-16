import { Player } from './Player.js';
import { ClientCell } from './ClientCell.js';
import { ChatMessage } from './ChatMessage.js';

export interface GameState {
  roomId: string;
  status: 'waiting' | 'playing' | 'gameover' | 'won';
  players: Record<string, Player>;
  board: ClientCell[][];
  minesRemaining: number;
  chat: ChatMessage[];
  totalMines: number;
  gameMode: 'classic' | 'turnByTurn' | 'simultaneous';
  currentTurn: string | null;
  hostId: string | null;
  startTime?: number | null;
}
