import React, { useState, useEffect, useRef } from 'react';
import { createPortal } from 'react-dom';
import type { MouseEvent } from 'react';
import { GameState } from '../types/GameState.js';
import { getGameModeConfig } from '../modes/gameModes.js';
import Cell from './Cell.js';
import BoardWaiting from './BoardWaiting.js';
import BoardResults from './BoardResults.js';
import BoardBanners from './BoardBanners.js';
import BoardControls from './BoardControls.js';

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
  
  const [showResults, setShowResults] = useState(false);

  type TooltipState = { x: number; y: number; lines: { text: string; color?: string }[] };
  const [tooltip, setTooltip] = useState<TooltipState | null>(null);

  type MinePopupState = { x: number; y: number; revealerIds: string[] };
  const [minePopup, setMinePopup] = useState<MinePopupState | null>(null);

  // When status becomes 'won' or 'gameover', automatically show results
  useEffect(() => {
    if (gameState.status === 'won' || gameState.status === 'gameover') {
      setShowResults(true);
    } else {
      setShowResults(false);
    }
  }, [gameState.status]);
  const cols = gameState.board[0]?.length || 0;

  // Zoom and Pan States
  const [zoom, setZoom] = useState(1);
  const [panX, setPanX] = useState(0);
  const [panY, setPanY] = useState(0);
  const [isDragging, setIsDragging] = useState(false);
  
  const dragStart = useRef({ x: 0, y: 0 });
  const containerRef = useRef<HTMLDivElement>(null);

  // Touch references for mobile pan and pinch-zoom
  const touchStartPos = useRef({ x: 0, y: 0 });
  const hasDraggedRef = useRef(false);
  const initialPinchDistance = useRef<number | null>(null);
  const initialPinchZoom = useRef<number>(1);
  const initialPinchMidpointLocal = useRef<{ x: number; y: number } | null>(null);
  
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

  // Keyboard pan with arrow keys and ZQSD
  const zoomRef = useRef(zoom);
  useEffect(() => { zoomRef.current = zoom; }, [zoom]);

  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      const target = e.target as HTMLElement;
      if (target.tagName === 'INPUT' || target.tagName === 'TEXTAREA' || target.isContentEditable) return;

      // Step = 3 cells in screen pixels at current zoom
      const step = cellTotal * zoomRef.current * 3;

      switch (e.key) {
        case 'ArrowLeft':  case 'q': case 'Q': e.preventDefault(); setPanX(p => p + step); break;
        case 'ArrowRight': case 'd': case 'D': e.preventDefault(); setPanX(p => p - step); break;
        case 'ArrowUp':    case 'z': case 'Z': e.preventDefault(); setPanY(p => p + step); break;
        case 'ArrowDown':  case 's': case 'S': e.preventDefault(); setPanY(p => p - step); break;
      }
    };

    window.addEventListener('keydown', handleKeyDown);
    return () => window.removeEventListener('keydown', handleKeyDown);
  }, []);

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

  // Handle Touch Pan and 2-Finger Pinch Zoom on mobile/touch devices
  useEffect(() => {
    const container = containerRef.current;
    if (!container) return;

    const handleTouchStart = (e: TouchEvent) => {
      if (e.touches.length === 1) {
        const touch = e.touches[0];
        setIsDragging(true);
        dragStart.current = { x: touch.clientX - panX, y: touch.clientY - panY };
        touchStartPos.current = { x: touch.clientX, y: touch.clientY };
        hasDraggedRef.current = false;
        initialPinchDistance.current = null;
        initialPinchMidpointLocal.current = null;
      } else if (e.touches.length === 2) {
        setIsDragging(false);
        hasDraggedRef.current = true; // prevent accidental clicks/taps

        const t1 = e.touches[0];
        const t2 = e.touches[1];
        const dist = Math.hypot(t1.clientX - t2.clientX, t1.clientY - t2.clientY);
        initialPinchDistance.current = dist;
        initialPinchZoom.current = zoom;

        const midX = (t1.clientX + t2.clientX) / 2;
        const midY = (t1.clientY + t2.clientY) / 2;
        const rect = container.getBoundingClientRect();
        const containerMidX = midX - rect.left;
        const containerMidY = midY - rect.top;

        initialPinchMidpointLocal.current = {
          x: (containerMidX - panX) / zoom,
          y: (containerMidY - panY) / zoom,
        };
      }
    };

    const handleTouchMove = (e: TouchEvent) => {
      if (e.touches.length === 1 && !initialPinchDistance.current) {
        const touch = e.touches[0];
        const dx = touch.clientX - touchStartPos.current.x;
        const dy = touch.clientY - touchStartPos.current.y;
        const dist = Math.hypot(dx, dy);

        if (dist > 6) {
          hasDraggedRef.current = true;
        }

        setPanX(touch.clientX - dragStart.current.x);
        setPanY(touch.clientY - dragStart.current.y);

        if (e.cancelable) {
          e.preventDefault();
        }
      } else if (e.touches.length === 2 && initialPinchDistance.current && initialPinchMidpointLocal.current) {
        const t1 = e.touches[0];
        const t2 = e.touches[1];
        const dist = Math.hypot(t1.clientX - t2.clientX, t1.clientY - t2.clientY);

        const scale = dist / initialPinchDistance.current;
        let nextZoom = initialPinchZoom.current * scale;
        nextZoom = Math.max(0.15, Math.min(4, nextZoom));

        const midX = (t1.clientX + t2.clientX) / 2;
        const midY = (t1.clientY + t2.clientY) / 2;
        const rect = container.getBoundingClientRect();
        const containerMidX = midX - rect.left;
        const containerMidY = midY - rect.top;

        setZoom(nextZoom);
        setPanX(containerMidX - initialPinchMidpointLocal.current.x * nextZoom);
        setPanY(containerMidY - initialPinchMidpointLocal.current.y * nextZoom);

        if (e.cancelable) {
          e.preventDefault();
        }
      }
    };

    const handleTouchEnd = (e: TouchEvent) => {
      if (e.touches.length === 0) {
        setIsDragging(false);
        initialPinchDistance.current = null;
        initialPinchMidpointLocal.current = null;
        // Keep hasDraggedRef.current true briefly so subsequent click triggers are ignored
        setTimeout(() => {
          hasDraggedRef.current = false;
        }, 80);
      } else if (e.touches.length === 1) {
        const touch = e.touches[0];
        dragStart.current = { x: touch.clientX - panX, y: touch.clientY - panY };
        touchStartPos.current = { x: touch.clientX, y: touch.clientY };
        initialPinchDistance.current = null;
        initialPinchMidpointLocal.current = null;
      }
    };

    container.addEventListener('touchstart', handleTouchStart, { passive: false });
    container.addEventListener('touchmove', handleTouchMove, { passive: false });
    container.addEventListener('touchend', handleTouchEnd, { passive: false });

    return () => {
      container.removeEventListener('touchstart', handleTouchStart);
      container.removeEventListener('touchmove', handleTouchMove);
      container.removeEventListener('touchend', handleTouchEnd);
    };
  }, [panX, panY, zoom]);

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
      <BoardBanners
        gameState={gameState}
        isZoomedOut={isZoomedOut}
        currentModeConfig={currentModeConfig}
        myId={myId}
      />

      {gameState.status === 'waiting' && (
        <BoardWaiting
          gameState={gameState}
          isHost={isHost}
          handleStartGame={handleStartGame}
        />
      )}

      {/* Centered Big Results Overlay */}
      {showResults && (gameState.status === 'won' || gameState.status === 'gameover') && (
        <BoardResults
          gameState={gameState}
          myId={myId}
          isHost={isHost}
          setShowResults={setShowResults}
          handleStartGame={handleStartGame}
        />
      )}

      {/* Grid Container */}
      {gameState.status !== 'waiting' && (
        <div
          ref={containerRef}
          onMouseDown={handleMouseDown}
          onMouseMove={handleMouseMove}
          onMouseUp={handleMouseUp}
          onMouseLeave={() => { handleMouseUp(); setTooltip(null); }}
          onContextMenu={(e) => {
            const isSim = gameState.gameMode === 'simultaneous' || gameState.gameMode === 'simultaneousAuto';
            if (!isSim) return;
            const btn = (e.target as HTMLElement).closest('button.grid-cell') as HTMLElement | null;
            if (!btn) return;
            const r = parseInt(btn.getAttribute('data-row') || '');
            const c = parseInt(btn.getAttribute('data-col') || '');
            if (isNaN(r) || isNaN(c)) return;
            const cell = gameState.board[r]?.[c];
            if (!cell?.isMine || !cell.mineRevealers?.length) return;
            e.preventDefault();
            const rect = btn.getBoundingClientRect();
            setMinePopup({ x: rect.left + rect.width / 2, y: rect.top - 6, revealerIds: cell.mineRevealers });
          }}
          onMouseOver={(e) => {
            const btn = (e.target as HTMLElement).closest('button.grid-cell') as HTMLElement | null;
            if (!btn) { setTooltip(null); return; }
            const r = parseInt(btn.getAttribute('data-row') || '');
            const c = parseInt(btn.getAttribute('data-col') || '');
            if (isNaN(r) || isNaN(c)) return;
            const cell = gameState.board[r]?.[c];
            if (!cell) return;
            const revealer = cell.revealedBy ? gameState.players[cell.revealedBy] : null;
            const flagger = cell.flaggedBy ? gameState.players[cell.flaggedBy] : null;
            if (!revealer && !flagger) { setTooltip(null); return; }
            const lines: { text: string; color?: string }[] = [];
            if (revealer) lines.push({ text: cell.isMine ? `💣 Trouvée par ${revealer.name}` : `Révélée par ${revealer.name}`, color: revealer.color });
            if (flagger) lines.push({ text: `🚩 Signalée par ${flagger.name}`, color: flagger.color });
            const rect = btn.getBoundingClientRect();
            setTooltip({ x: rect.left + rect.width / 2, y: rect.top - 6, lines });
          }}
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
                <Cell
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
                  onClick={() => {
                    if (hasDraggedRef.current) return;
                    handleReveal(r, c);
                  }}
                  onContextMenu={(e) => {
                    if (hasDraggedRef.current) {
                      e.preventDefault();
                      return;
                    }
                    handleFlag(e, r, c);
                  }}
                  isZoomedOut={isZoomedOut}
                />
              );
            })}
          </div>
        </div>
      )}

      {/* Zoom / Pan Control Panel overlay */}
      {gameState.status !== 'waiting' && (
        <BoardControls
          status={gameState.status}
          showResults={showResults}
          setShowResults={setShowResults}
          setZoom={setZoom}
          resetView={resetView}
        />
      )}

      {minePopup && createPortal(
        <>
          {/* backdrop to close on click outside */}
          <div className="fixed inset-0 z-[9998]" onClick={() => setMinePopup(null)} />
          <div
            className="fixed z-[9999] pointer-events-auto"
            style={{ left: minePopup.x, top: minePopup.y, transform: 'translate(-50%, -100%)' }}
          >
            <div className="bg-slate-950 border border-slate-700 rounded-xl shadow-xl px-3 py-2.5 flex flex-col gap-1.5 min-w-[160px]">
              <p className="text-[10px] font-semibold uppercase tracking-wider text-slate-500 mb-0.5">💣 Trouvée par</p>
              {minePopup.revealerIds.map((id) => {
                const player = gameState.players[id];
                if (!player) return null;
                return (
                  <div key={id} className="flex items-center gap-2 text-xs font-medium">
                    <span className="w-2.5 h-2.5 rounded-full shrink-0" style={{ backgroundColor: player.color }} />
                    <span style={{ color: player.color }}>{player.name}</span>
                  </div>
                );
              })}
            </div>
            <div className="w-2 h-2 bg-slate-950 border-b border-r border-slate-700 rotate-45 mx-auto -mt-1" />
          </div>
        </>,
        document.body
      )}

      {tooltip && createPortal(
        <div
          className="fixed z-[9999] pointer-events-none"
          style={{ left: tooltip.x, top: tooltip.y, transform: 'translate(-50%, -100%)' }}
        >
          <div className="bg-slate-950 border border-slate-700 rounded-xl shadow-xl px-3 py-2 flex flex-col gap-1 text-xs font-medium whitespace-nowrap">
            {tooltip.lines.map((line, i) => (
              <span key={i} style={{ color: line.color ?? '#cbd5e1' }}>{line.text}</span>
            ))}
          </div>
          <div className="w-2 h-2 bg-slate-950 border-b border-r border-slate-700 rotate-45 mx-auto -mt-1" />
        </div>,
        document.body
      )}
    </main>
  );
}
