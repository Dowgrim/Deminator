export interface GameConfig {
  rows: number;
  cols: number;
  mines: number;
  gameMode?: 'classic' | 'turnByTurn' | 'simultaneous';
}
