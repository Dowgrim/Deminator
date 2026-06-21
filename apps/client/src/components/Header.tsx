import { Bomb, Share2, Check, Flag, RotateCcw } from 'lucide-react';
import { GameState } from '../types/GameState.js';
import { GAME_MODES, getGameModeConfig, GameModeType } from '../modes/gameModes.js';

interface HeaderProps {
  gameState: GameState;
  difficulty: 'easy' | 'medium' | 'hard' | 'expert' | 'giant' | 'colossal' | 'supreme' | 'custom';
  setDifficulty: (diff: 'easy' | 'medium' | 'hard' | 'expert' | 'giant' | 'colossal' | 'supreme' | 'custom') => void;
  customRows: number;
  setCustomRows: (rows: number) => void;
  customCols: number;
  setCustomCols: (cols: number) => void;
  customMines: number;
  setCustomMines: (mines: number) => void;
  gameMode: GameModeType;
  setGameMode: (mode: GameModeType) => void;
  turnTimer: number;
  setTurnTimer: (timer: number) => void;
  handleStartGame: () => void;
  copyRoomId: () => void;
  copied: boolean;
  myId: string | undefined;
}

export default function Header({
  gameState,
  difficulty,
  setDifficulty,
  customRows,
  setCustomRows,
  customCols,
  setCustomCols,
  customMines,
  setCustomMines,
  gameMode,
  setGameMode,
  turnTimer,
  setTurnTimer,
  handleStartGame,
  copyRoomId,
  copied,
  myId,
}: HeaderProps) {
  const currentModeConfig = getGameModeConfig(gameState.gameMode);
  const isHost = myId === gameState.hostId;

  return (
    <header className="bg-slate-900 border-b border-slate-800 py-4 px-6 shadow-md flex flex-wrap gap-4 items-center justify-between shrink-0">
      <div className="flex items-center gap-3">
        <div className="bg-red-500/10 p-2 rounded-xl border border-red-500/20">
          <Bomb className="w-6 h-6 text-red-500 animate-pulse" />
        </div>
        <div>
          <h1 className="text-xl font-bold tracking-wider text-red-500">DEMINATOR</h1>
          <p className="text-xs text-slate-400">Multijoueur Coopératif & Compétitif</p>
        </div>
      </div>

      {/* Room information & sharing */}
      <div className="flex items-center gap-2 bg-slate-950 border border-slate-800 px-4 py-2 rounded-xl">
        <span className="text-xs text-slate-500 font-semibold uppercase">Salon:</span>
        <span className="font-mono font-bold text-red-400 tracking-wider select-all">{gameState.roomId}</span>
        <button 
          onClick={copyRoomId} 
          className="p-1 hover:bg-slate-800 rounded text-slate-400 hover:text-slate-200 transition-all ml-1"
          title="Copier le code"
        >
          {copied ? <Check className="w-4 h-4 text-emerald-500" /> : <Share2 className="w-4 h-4" />}
        </button>
      </div>

      {/* Game Config / Controls */}
      <div className="flex items-center gap-4">
        {gameState.status === 'playing' && (
          <div className="flex gap-4 items-center">
            <div className="bg-slate-950 px-3 py-1.5 rounded-lg border border-slate-800 flex items-center gap-2 text-xs font-semibold uppercase text-slate-400">
              Mode: {currentModeConfig.label}
            </div>
            <div className="bg-slate-950 px-3 py-1.5 rounded-lg border border-slate-800 flex items-center gap-2">
              <Flag className="w-4 h-4 text-orange-500" />
              <span className="text-sm font-semibold">{gameState.minesRemaining} / {gameState.totalMines}</span>
            </div>
          </div>
        )}

        {isHost ? (
          <div className="flex items-center gap-2 flex-wrap">
            <select
              value={difficulty}
              onChange={(e) => setDifficulty(e.target.value as any)}
              className="bg-slate-800 border border-slate-700 rounded-lg px-3 py-1.5 text-sm focus:outline-none"
            >
              <option value="easy">Facile (10 Mines)</option>
              <option value="medium">Moyen (25 Mines)</option>
              <option value="hard">Difficile (50 Mines)</option>
              <option value="expert">Expert (30x30, 150 Mines)</option>
              <option value="giant">Géant (100x100, 1500 Mines)</option>
              <option value="colossal">Colossal (500x500, 35000 Mines)</option>
              <option value="supreme">Suprême (1000x1000, 150000 Mines)</option>
              <option value="custom">Personnalisé...</option>
            </select>

            {difficulty === 'custom' && (
              <div className="flex items-center gap-2 bg-slate-950 p-1.5 rounded-lg border border-slate-800 text-xs">
                <label className="text-slate-400">L:</label>
                <input
                  type="number"
                  min={1}
                  max={1000}
                  value={customRows}
                  onChange={(e) => setCustomRows(Math.max(1, Math.min(1000, parseInt(e.target.value) || 1)))}
                  className="w-12 bg-slate-800 border border-slate-700 rounded px-1.5 py-0.5 text-center"
                />
                <label className="text-slate-400">C:</label>
                <input
                  type="number"
                  min={1}
                  max={1000}
                  value={customCols}
                  onChange={(e) => setCustomCols(Math.max(1, Math.min(1000, parseInt(e.target.value) || 1)))}
                  className="w-12 bg-slate-800 border border-slate-700 rounded px-1.5 py-0.5 text-center"
                />
                <label className="text-slate-400">M:</label>
                <input
                  type="number"
                  min={1}
                  max={Math.max(1, customRows * customCols - 9)}
                  value={customMines}
                  onChange={(e) => setCustomMines(Math.max(1, Math.min(customRows * customCols - 9, parseInt(e.target.value) || 1)))}
                  className="w-14 bg-slate-800 border border-slate-700 rounded px-1.5 py-0.5 text-center"
                />
              </div>
            )}

            <select
              value={gameMode}
              onChange={(e) => setGameMode(e.target.value as any)}
              className="bg-slate-800 border border-slate-700 rounded-lg px-3 py-1.5 text-sm focus:outline-none"
            >
              {Object.values(GAME_MODES).map((mode) => (
                <option key={mode.key} value={mode.key}>
                  {mode.selectLabel}
                </option>
              ))}
            </select>

            {(gameMode === 'turnByTurn' || gameMode === 'simultaneous' || gameMode === 'simultaneousAuto') && (
              <select
                value={turnTimer}
                onChange={(e) => setTurnTimer(Number(e.target.value))}
                className="bg-slate-800 border border-slate-700 rounded-lg px-3 py-1.5 text-sm focus:outline-none"
                title="Timer par tour"
              >
                <option value={0}>Pas de timer</option>
                <option value={15}>Timer : 15s</option>
                <option value={30}>Timer : 30s</option>
                <option value={60}>Timer : 1 min</option>
                <option value={120}>Timer : 2 min</option>
              </select>
            )}

            <button
              onClick={handleStartGame}
              className="px-4 py-1.5 bg-red-600 hover:bg-red-500 font-semibold text-sm rounded-lg flex items-center gap-2 transition-all shadow-md shadow-red-900/10"
            >
              <RotateCcw className="w-4 h-4" />
              {gameState.status === 'playing' ? 'Recommencer' : 'Démarrer'}
            </button>
          </div>
        ) : (
          <div className="text-xs text-slate-400 font-medium flex items-center gap-2 max-w-xs md:max-w-none bg-slate-950 px-3 py-1.5 rounded-lg border border-slate-800">
            <span className="w-2 h-2 rounded-full bg-orange-500 animate-pulse shrink-0" />
            <span>Salon contrôlé par <span className="font-bold text-slate-200">{gameState.players[gameState.hostId || '']?.name || 'le créateur'}</span></span>
          </div>
        )}
      </div>
    </header>
  );
}
