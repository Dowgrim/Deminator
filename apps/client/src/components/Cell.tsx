import React, { memo } from 'react';

export interface CellProps {
  row: number;
  col: number;
  isRevealed: boolean;
  isMine: boolean;
  isFlagged: boolean;
  neighborMines?: number;
  revealerColor?: string;
  flaggerColor?: string;
  revealerName?: string;
  flaggerName?: string;
  cellSize: number;
  cellTotal: number;
  disabled: boolean;
  allowFlag: boolean;
  onClick: () => void;
  onContextMenu: (e: React.MouseEvent) => void;
  isZoomedOut: boolean;
}

const Cell = memo(function Cell({
  row,
  col,
  isRevealed,
  isMine,
  isFlagged,
  neighborMines,
  revealerColor,
  flaggerColor,
  revealerName,
  flaggerName,
  cellSize,
  cellTotal,
  disabled,
  allowFlag,
  onClick,
  onContextMenu,
  isZoomedOut,
}: CellProps) {
  if (isZoomedOut) {
    let bgClass = "absolute rounded-sm pointer-events-none ";
    if (isRevealed) {
      if (isMine) {
        bgClass += "bg-red-800";
      } else {
        bgClass += "bg-slate-950";
      }
    } else {
      if (isFlagged) {
        bgClass += "bg-orange-600";
      } else {
        bgClass += "bg-slate-800";
      }
    }

    const borderStyle: React.CSSProperties = {};
    if (isRevealed && revealerColor) {
      borderStyle.boxShadow = `inset 0 0 0 1px ${revealerColor}40`;
    } else if (isFlagged && flaggerColor) {
      borderStyle.borderColor = flaggerColor;
      borderStyle.borderWidth = '1px';
    }

    return (
      <div
        className={bgClass}
        style={{
          position: 'absolute',
          left: `${col * cellTotal}px`,
          top: `${row * cellTotal}px`,
          width: `${cellSize}px`,
          height: `${cellSize}px`,
          ...borderStyle,
        }}
      />
    );
  }

  let buttonClass = "grid-cell rounded-lg flex items-center justify-center select-none text-sm font-bold relative focus:outline-none transition-colors duration-100 ";
  let content: React.ReactNode = null;

  if (isRevealed) {
    if (isMine) {
      buttonClass += "bg-red-950/70 border border-red-500/30 text-red-500";
      content = (
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="text-red-500 shrink-0">
          <circle cx="12" cy="12" r="10" />
          <path d="m12 9 3 3m-3 0-3-3m3 3v6" />
        </svg>
      );
    } else {
      buttonClass += "bg-slate-950 border border-slate-800/50 text-slate-300";
      if (neighborMines && neighborMines > 0) {
        let numColor = 'text-slate-400';
        switch (neighborMines) {
          case 1: numColor = 'text-blue-400 font-bold'; break;
          case 2: numColor = 'text-emerald-400 font-bold'; break;
          case 3: numColor = 'text-rose-400 font-bold'; break;
          case 4: numColor = 'text-violet-400 font-bold'; break;
          case 5: numColor = 'text-amber-400 font-bold'; break;
          case 6: numColor = 'text-cyan-400 font-bold'; break;
          case 7: numColor = 'text-pink-400 font-bold'; break;
          case 8: numColor = 'text-indigo-400 font-bold'; break;
        }
        content = <span style={{ fontSize: `${cellSize * 0.45}px` }} className={numColor}>{neighborMines}</span>;
      }
    }
  } else {
    buttonClass += "bg-slate-800 hover:bg-slate-700/80 active:scale-95 cursor-pointer border border-slate-700/60 shadow-[inset_0_2px_4px_rgba(255,255,255,0.05)]";
    
    if (isFlagged) {
      content = (
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="text-orange-500 fill-orange-500 shrink-0">
          <path d="M4 15s1-1 4-1 5 2 8 2 4-1 4-1V3s-1 1-4 1-5-2-8-2-4 1-4 1z" />
          <line x1="4" y1="22" x2="4" y2="15" />
        </svg>
      );
    }
  }

  const borderStyle: React.CSSProperties = {};
  if (isRevealed && revealerColor) {
    borderStyle.boxShadow = `inset 0 0 0 2px ${revealerColor}40`;
  } else if (isFlagged && flaggerColor) {
    borderStyle.borderColor = flaggerColor;
  }

  const title = revealerName 
    ? `Découvert par ${revealerName}` 
    : flaggerName 
      ? `Signalé par ${flaggerName}` 
      : '';

  return (
    <button
      onClick={onClick}
      onContextMenu={(e) => {
        if (allowFlag) {
          onContextMenu(e);
        } else {
          e.preventDefault();
        }
      }}
      disabled={disabled}
      className={buttonClass}
      style={{
        position: 'absolute',
        left: `${col * cellTotal}px`,
        top: `${row * cellTotal}px`,
        width: `${cellSize}px`,
        height: `${cellSize}px`,
        ...borderStyle,
      }}
      title={title}
    >
      {content}
    </button>
  );
}, (prev, next) => {
  return prev.row === next.row &&
         prev.col === next.col &&
         prev.isRevealed === next.isRevealed &&
         prev.isMine === next.isMine &&
         prev.isFlagged === next.isFlagged &&
         prev.neighborMines === next.neighborMines &&
         prev.revealerColor === next.revealerColor &&
         prev.flaggerColor === next.flaggerColor &&
         prev.disabled === next.disabled &&
         prev.allowFlag === next.allowFlag &&
         prev.cellSize === next.cellSize &&
         prev.cellTotal === next.cellTotal &&
         prev.isZoomedOut === next.isZoomedOut;
});

export default Cell;
