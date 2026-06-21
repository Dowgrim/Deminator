export interface GameConfig {
  rows: number;
  cols: number;
  mines: number;
  gameMode?: 'classic' | 'turnByTurn' | 'simultaneous' | 'simultaneousAuto';
  turnTimer?: number; // seconds, 0 = disabled
}
