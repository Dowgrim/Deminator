export interface ClientCell {
  row: number;
  col: number;
  neighborMines?: number;
  isRevealed: boolean;
  isFlagged: boolean;
  flaggedBy: string | null;
  revealedBy: string | null;
  isMine?: boolean;
  mineRevealers?: string[];
}
