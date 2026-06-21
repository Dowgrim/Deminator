import { useState } from 'react';
import { Crown, Skull, Trophy, Eye, RefreshCw, ChevronDown, Bomb, Grid2x2 } from 'lucide-react';
import { GameState } from '../types/GameState.js';

interface BoardResultsProps {
  gameState: GameState;
  myId: string | undefined;
  isHost: boolean;
  setShowResults: (show: boolean) => void;
  handleStartGame: () => void;
}

export default function BoardResults({
  gameState,
  myId,
  isHost,
  setShowResults,
  handleStartGame,
}: BoardResultsProps) {
  const [expandedId, setExpandedId] = useState<string | null>(null);

  const toggle = (id: string) => setExpandedId(prev => prev === id ? null : id);

  return (
    <div className="fixed inset-0 bg-slate-950/85 backdrop-blur-md flex items-center justify-center z-50 p-4 animate-fade-in">
      <div className="bg-slate-900 border border-slate-800 rounded-3xl p-6 md:p-8 max-w-lg w-full shadow-2xl shadow-black/80 flex flex-col gap-6 relative max-h-[90vh] overflow-y-auto">

        {/* Header / Game Status */}
        <div className="text-center flex flex-col items-center gap-3">
          {gameState.status === 'won' ? (
            <div className="relative">
              <div className="absolute inset-0 bg-emerald-500/20 blur-xl rounded-full"></div>
              <Crown className="w-16 h-16 text-emerald-400 fill-emerald-400 relative animate-bounce" />
            </div>
          ) : (
            <div className="relative">
              <div className="absolute inset-0 bg-red-500/20 blur-xl rounded-full"></div>
              <Skull className="w-16 h-16 text-red-500 relative animate-bounce" />
            </div>
          )}

          <div>
            <h1 className={`text-3xl md:text-4xl font-extrabold tracking-tight bg-clip-text text-transparent bg-gradient-to-r ${
              gameState.status === 'won' ? 'from-emerald-400 to-teal-400' : 'from-red-500 to-orange-500'
            }`}>
              {gameState.status === 'won' ? 'Victoire !' : 'Partie Terminée'}
            </h1>
            <p className="text-sm text-slate-400 mt-1">
              {gameState.status === 'won'
                ? 'Toutes les bombes ont été sécurisées !'
                : "L'explosion a emporté les derniers démineurs..."}
            </p>
          </div>
        </div>

        {/* Leaderboard Results */}
        <div className="flex flex-col gap-3">
          <h2 className="text-xs font-bold uppercase text-slate-400 tracking-wider flex items-center gap-2 border-b border-slate-800 pb-2">
            <Trophy className="w-4 h-4 text-amber-500" />
            Classement final
          </h2>

          <div className="space-y-2 max-h-[40vh] overflow-y-auto pr-1">
            {Object.values(gameState.players)
              .sort((a, b) => b.score - a.score)
              .map((player, idx) => {
                const isMePlayer = player.id === myId;
                const isExpanded = expandedId === player.id;

                return (
                  <div
                    key={player.id}
                    className={`rounded-xl border border-slate-800/60 bg-slate-950/40 overflow-hidden relative ${
                      isMePlayer ? 'ring-1 ring-slate-700/80' : ''
                    }`}
                    style={{ borderLeft: `4px solid ${player.color}` }}
                  >
                    {/* Main row — clickable to expand */}
                    <button
                      className="w-full flex items-center justify-between p-3 text-left hover:bg-slate-800/30 transition-colors"
                      onClick={() => toggle(player.id)}
                    >
                      <div className="flex items-center gap-3">
                        <span className="font-mono font-extrabold text-base w-6 text-center text-slate-400">
                          {idx === 0 ? '🥇' : idx === 1 ? '🥈' : idx === 2 ? '🥉' : `${idx + 1}`}
                        </span>
                        <span className="font-bold text-sm" style={{ color: player.color }}>
                          {player.name} {isMePlayer ? '(Vous)' : ''}
                        </span>
                      </div>

                      <div className="flex items-center gap-3">
                        <span className="font-mono font-bold text-sm text-slate-200">
                          {player.score} pts
                        </span>
                        {gameState.gameMode !== 'turnByTurn' && (
                          player.isAlive ? (
                            <span className="text-[10px] bg-emerald-500/10 text-emerald-400 border border-emerald-500/20 px-1.5 py-0.5 rounded-full font-semibold">
                              Vivant
                            </span>
                          ) : (
                            <span className="text-[10px] bg-red-500/10 text-red-400 border border-red-500/20 px-1.5 py-0.5 rounded-full font-semibold flex items-center gap-1">
                              <Skull className="w-3 h-3" /> Mort
                            </span>
                          )
                        )}
                        <ChevronDown
                          className={`w-4 h-4 text-slate-500 transition-transform duration-200 ${isExpanded ? 'rotate-180' : ''}`}
                        />
                      </div>
                    </button>

                    {/* Expandable stats */}
                    {isExpanded && (
                      <div className="px-4 pb-3 pt-1 border-t border-slate-800/60 grid grid-cols-2 gap-2">
                        <div className="flex items-center gap-2 bg-slate-900/60 rounded-lg px-3 py-2">
                          <Grid2x2 className="w-4 h-4 text-blue-400 shrink-0" />
                          <div>
                            <p className="text-[10px] text-slate-500 uppercase tracking-wider font-semibold">Cases révélées</p>
                            <p className="text-sm font-bold text-blue-400">{player.cellsRevealed ?? 0}</p>
                          </div>
                        </div>
                        <div className="flex items-center gap-2 bg-slate-900/60 rounded-lg px-3 py-2">
                          <Bomb className="w-4 h-4 text-red-400 shrink-0" />
                          <div>
                            <p className="text-[10px] text-slate-500 uppercase tracking-wider font-semibold">Mines trouvées</p>
                            <p className="text-sm font-bold text-red-400">{player.minesFound ?? 0}</p>
                          </div>
                        </div>
                      </div>
                    )}
                  </div>
                );
              })}
          </div>
        </div>

        {/* Action Buttons */}
        <div className="flex flex-col sm:flex-row gap-3 mt-2">
          <button
            onClick={() => setShowResults(false)}
            className="flex-1 px-4 py-3 bg-slate-800 hover:bg-slate-700 text-slate-200 font-bold rounded-2xl border border-slate-700/50 transition-all text-sm flex items-center justify-center gap-2"
          >
            <Eye className="w-4 h-4" />
            Inspecter la grille
          </button>

          {isHost ? (
            <button
              onClick={handleStartGame}
              className="flex-1 px-5 py-3 bg-gradient-to-r from-red-600 to-orange-600 hover:from-red-500 hover:to-orange-500 text-white font-bold rounded-2xl shadow-xl shadow-red-950/10 hover:shadow-red-950/20 transition-all text-sm flex items-center justify-center gap-2"
            >
              <RefreshCw className="w-4 h-4" />
              Recommencer
            </button>
          ) : (
            <div className="flex-1 px-4 py-3 bg-slate-950 text-slate-400 border border-slate-800 rounded-2xl text-xs flex items-center justify-center font-medium">
              En attente de l'hôte...
            </div>
          )}
        </div>

      </div>
    </div>
  );
}
