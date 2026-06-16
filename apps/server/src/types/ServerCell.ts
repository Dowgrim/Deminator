export interface ServerCell {
  row: number;
  col: number;
  isMine: boolean;
  neighborMines: number;
  isRevealed: boolean;
  isFlagged: boolean;
  flaggedBy: string | null;
  revealedBy: string | null;
}
