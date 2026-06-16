import React, { useState, useEffect, useRef, memo } from 'react';
import type { MouseEvent } from 'react';
import { Users, Play, Crown, Skull } from 'lucide-react';
import { GameState } from '../types/GameState.js';
import { getGameModeConfig } from '../modes/gameModes.js';

interface CellProps {
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

const MemoizedCell = memo(function Cell({
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

interface BoardProps {
  gameState: GameState;
  myId: string | undefined;
  handleReveal: (row: number, col: number) => void;
  handleFlag: (e: MouseEvent, row: number, col: number) => void;
  handleStartGame: () => void;
}

export default function Board({
  gameState,
  myId,
  handleReveal,
  handleFlag,
  handleStartGame,
}: BoardProps) {
  const me = gameState.players[myId || ''];
  const currentModeConfig = getGameModeConfig(gameState.gameMode);

  const rows = gameState.board.length;
  const cols = gameState.board[0]?.length || 0;

  // Zoom and Pan States
  const [zoom, setZoom] = useState(1);
  const [panX, setPanX] = useState(0);
  const [panY, setPanY] = useState(0);
  const [isDragging, setIsDragging] = useState(false);
  
  const dragStart = useRef({ x: 0, y: 0 });
  const containerRef = useRef<HTMLDivElement>(null);
  
  const [dimensions, setDimensions] = useState({ width: 800, height: 600 });

  const cellSize = 36;
  const gap = 6;
  const cellTotal = cellSize + gap;

  // Track container dimensions
  useEffect(() => {
    if (!containerRef.current) return;
    const resizeObserver = new ResizeObserver((entries) => {
      for (let entry of entries) {
        setDimensions({
          width: entry.contentRect.width,
          height: entry.contentRect.height,
        });
      }
    });
    resizeObserver.observe(containerRef.current);
    return () => resizeObserver.disconnect();
  }, [gameState.status]);

  const resetView = () => {
    if (!containerRef.current || cols === 0 || rows === 0) return;
    const container = containerRef.current;
    const boardWidth = cols * cellTotal - gap;
    const boardHeight = rows * cellTotal - gap;
    
    // Fit to container with some padding
    const padding = 40;
    const fitZoom = Math.max(0.15, Math.min(
      1.2,
      Math.min((container.clientWidth - padding) / boardWidth, (container.clientHeight - padding) / boardHeight)
    ));
    
    setZoom(fitZoom);
    setPanX((container.clientWidth - boardWidth * fitZoom) / 2);
    setPanY((container.clientHeight - boardHeight * fitZoom) / 2);
  };

  // Center/Fit view when rows or cols change
  useEffect(() => {
    resetView();
  }, [rows, cols]);

  // Handle Wheel Zoom
  useEffect(() => {
    const container = containerRef.current;
    if (!container) return;

    const handleWheelEvent = (e: WheelEvent) => {
      const boardWidth = cols * cellTotal - gap;
      const boardHeight = rows * cellTotal - gap;
      const hasOverflow = boardWidth * zoom > container.clientWidth || boardHeight * zoom > container.clientHeight;

      // Only zoom if the grid is larger than container or already zoomed
      if (!hasOverflow && zoom === 1 && e.deltaY > 0) {
        return;
      }
      
      e.preventDefault();

      // Get mouse position relative to container to zoom towards cursor
      const rect = container.getBoundingClientRect();
      const mouseX = e.clientX - rect.left;
      const mouseY = e.clientY - rect.top;

      // Calculate cursor position in board local coordinates
      const boardX = (mouseX - panX) / zoom;
      const boardY = (mouseY - panY) / zoom;

      const zoomIntensity = 0.08;
      const delta = -e.deltaY * zoomIntensity * 0.01;
      
      setZoom((prevZoom) => {
        let nextZoom = prevZoom * (1 + delta);
        nextZoom = Math.max(0.15, Math.min(4, nextZoom));
        
        // Adjust pan so the point under the cursor stays under the cursor
        setPanX(mouseX - boardX * nextZoom);
        setPanY(mouseY - boardY * nextZoom);
        
        return nextZoom;
      });
    };

    container.addEventListener('wheel', handleWheelEvent, { passive: false });
    return () => {
      container.removeEventListener('wheel', handleWheelEvent);
    };
  }, [zoom, panX, panY, rows, cols]);

  // Mouse drag panning handlers
  const handleMouseDown = (e: React.MouseEvent) => {
    const isCell = (e.target as HTMLElement).closest('button.grid-cell');
    if (e.button === 0 && isCell) {
      return; // Standard reveal
    }
    e.preventDefault();
    setIsDragging(true);
    dragStart.current = { x: e.clientX - panX, y: e.clientY - panY };
  };

  const handleMouseMove = (e: React.MouseEvent) => {
    if (!isDragging) return;
    setPanX(e.clientX - dragStart.current.x);
    setPanY(e.clientY - dragStart.current.y);
  };

  const handleMouseUp = () => {
    setIsDragging(false);
  };

  const MIN_INTERACT_ZOOM = 0.4;
  const isZoomedOut = zoom < MIN_INTERACT_ZOOM;

  // Viewport calculation for cell virtualization
  const localLeft = -panX / zoom;
  const localRight = (dimensions.width - panX) / zoom;
  const localTop = -panY / zoom;
  const localBottom = (dimensions.height - panY) / zoom;

  // Use a smaller buffer when zoomed out to render fewer elements
  const buffer = isZoomedOut ? 3 : 8;
  const startCol = Math.max(0, Math.floor(localLeft / cellTotal) - buffer);
  const endCol = Math.min(cols - 1, Math.ceil(localRight / cellTotal) + buffer);
  const startRow = Math.max(0, Math.floor(localTop / cellTotal) - buffer);
  const endRow = Math.min(rows - 1, Math.ceil(localBottom / cellTotal) + buffer);

  // Gather only visible cells to render
  const visibleCells: { r: number; c: number; cell: any }[] = [];
  if (gameState.status !== 'waiting' && cols > 0 && rows > 0) {
    for (let r = startRow; r <= endRow; r++) {
      for (let c = startCol; c <= endCol; c++) {
        const cell = gameState.board[r]?.[c];
        if (cell) {
          visibleCells.push({ r, c, cell });
        }
      }
    }
  }

  const isHost = myId === gameState.hostId;

  return (
    <main className="flex-1 w-full h-full p-4 md:p-6 flex flex-col items-center justify-center bg-slate-950 overflow-hidden relative select-none">
      {/* Informative banner when scale is too wide to interact */}
      {gameState.status === 'playing' && isZoomedOut && (
        <div className="absolute top-4 left-1/2 -translate-x-1/2 px-4 py-2 bg-slate-900/95 border border-slate-800 text-slate-300 rounded-full text-xs font-medium shadow-lg z-20 animate-fade-in pointer-events-none flex items-center gap-2">
          <span className="w-2 h-2 rounded-full bg-orange-500 animate-pulse"></span>
          Zoomer pour interagir avec les cases
        </div>
      )}

      {/* Dynamic game mode banner */}
      {gameState.status === 'playing' && !isZoomedOut && currentModeConfig.renderBanner(gameState, myId)}

      {/* Status Message overlay or banner */}
      {gameState.status === 'gameover' && (
        <div className="mb-4 px-6 py-3 bg-red-600/10 border border-red-600/30 rounded-2xl flex items-center gap-3 animate-fade-in text-center max-w-md z-10 shrink-0">
          <Skull className="w-6 h-6 text-red-500 animate-bounce" />
          <div>
            <h2 className="font-bold text-red-500 text-sm md:text-base">Partie Terminée !</h2>
            <p className="text-xs text-slate-400">Tous les démineurs ont péri dans l'explosion...</p>
          </div>
        </div>
      )}

      {gameState.status === 'won' && (
        <div className="mb-4 px-6 py-3 bg-emerald-600/10 border border-emerald-600/30 rounded-2xl flex items-center gap-3 animate-fade-in text-center max-w-md z-10 shrink-0">
          <Crown className="w-6 h-6 text-emerald-400 animate-bounce fill-emerald-400" />
          <div>
            <h2 className="font-bold text-emerald-400 text-sm md:text-base">Victoire Légendaire !</h2>
            <p className="text-xs text-slate-400">La zone a été entièrement sécurisée avec succès.</p>
          </div>
        </div>
      )}

      {gameState.status === 'waiting' && (
        <div className="mb-4 flex flex-col items-center justify-center p-6 md:p-8 bg-slate-900 border border-slate-800 rounded-2xl text-center max-w-md z-10 shrink-0">
          <Users className="w-12 h-12 text-red-500 mb-3" />
          <h2 className="font-bold text-lg mb-1">En attente de joueurs...</h2>
          <p className="text-xs text-slate-400 mb-4">
            Partagez le code de salon <span className="font-mono font-bold text-red-400 bg-slate-950 px-2 py-0.5 rounded">{gameState.roomId}</span> avec vos amis !
          </p>
          {isHost ? (
            <button
              onClick={handleStartGame}
              className="px-5 py-2.5 bg-gradient-to-r from-red-600 to-orange-600 hover:from-red-500 hover:to-orange-500 text-white font-bold rounded-xl shadow-lg shadow-red-900/20 transition-all flex items-center gap-2"
            >
              <Play className="w-4 h-4 fill-current" />
              Lancer la partie
            </button>
          ) : (
            <div className="text-xs text-slate-400 bg-slate-950 px-3 py-1.5 rounded-lg border border-slate-800">
              En attente du créateur du salon...
            </div>
          )}
        </div>
      )}

      {/* Grid Container */}
      {gameState.status !== 'waiting' && (
        <div 
          ref={containerRef}
          onMouseDown={handleMouseDown}
          onMouseMove={handleMouseMove}
          onMouseUp={handleMouseUp}
          onMouseLeave={handleMouseUp}
          className={`flex-1 w-full bg-slate-900 border border-slate-800 rounded-3xl shadow-2xl overflow-hidden relative cursor-grab ${isDragging ? 'cursor-grabbing' : ''}`}
        >
          {/* Zoomable, Pannable inner world wrapper */}
          <div 
            className="absolute origin-top-left"
            style={{
              transform: `translate(${panX}px, ${panY}px) scale(${zoom})`,
              width: `${cols * cellTotal - gap}px`,
              height: `${rows * cellTotal - gap}px`,
            }}
          >
            {visibleCells.map(({ r, c, cell }) => {
              const cellRevealer = cell.revealedBy ? gameState.players[cell.revealedBy] : null;
              const cellFlagger = cell.flaggedBy ? gameState.players[cell.flaggedBy] : null;

              return (
                <MemoizedCell
                  key={`${r}-${c}`}
                  row={r}
                  col={c}
                  isRevealed={cell.isRevealed}
                  isMine={cell.isMine}
                  isFlagged={cell.isFlagged}
                  neighborMines={cell.neighborMines}
                  revealerColor={cellRevealer?.color}
                  flaggerColor={cellFlagger?.color}
                  revealerName={cellRevealer?.name}
                  flaggerName={cellFlagger?.name}
                  cellSize={cellSize}
                  cellTotal={cellTotal}
                  disabled={currentModeConfig.isDisabled(gameState, myId, me)}
                  allowFlag={currentModeConfig.allowFlag}
                  onClick={() => handleReveal(r, c)}
                  onContextMenu={(e) => handleFlag(e, r, c)}
                  isZoomedOut={isZoomedOut}
                />
              );
            })}
          </div>
        </div>
      )}

      {/* Zoom / Pan Control Panel overlay */}
      {gameState.status !== 'waiting' && (
        <div className="absolute bottom-4 md:bottom-6 right-4 md:right-6 flex items-center gap-2 bg-slate-900/90 border border-slate-800 p-2 rounded-2xl shadow-xl z-20 select-none">
          <button
            onClick={() => setZoom(prev => Math.min(4, prev * 1.2))}
            className="w-8 h-8 flex items-center justify-center bg-slate-800 hover:bg-slate-700 text-slate-200 rounded-lg text-lg font-bold transition-all"
            title="Zoomer"
          >
            +
          </button>
          <button
            onClick={() => setZoom(prev => Math.max(0.15, prev / 1.2))}
            className="w-8 h-8 flex items-center justify-center bg-slate-800 hover:bg-slate-700 text-slate-200 rounded-lg text-lg font-bold transition-all"
            title="Dézoomer"
          >
            -
          </button>
          <button
            onClick={resetView}
            className="px-2.5 h-8 flex items-center justify-center bg-slate-800 hover:bg-slate-700 text-slate-200 rounded-lg text-xs font-semibold transition-all"
            title="Réinitialiser la vue (Centrer)"
          >
            Centrer
          </button>
        </div>
      )}
    </main>
  );
}
