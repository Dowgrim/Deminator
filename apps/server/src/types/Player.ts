export interface Player {
  id: string;
  name: string;
  score: number;
  color: string;
  isAlive: boolean;
  isTurnDone?: boolean;
  cellsRevealed: number;
  minesFound: number;
}
