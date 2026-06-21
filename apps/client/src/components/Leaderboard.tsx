import { useState, useEffect } from 'react';
import { Users, Crown, Skull, Timer, CheckCircle2, Crosshair } from 'lucide-react';
import { GameState } from '../types/GameState.js';

interface LeaderboardProps {
  gameState: GameState;
  myId: string | undefined;
}

export default function Leaderboard({ gameState, myId }: LeaderboardProps) {
  const me = gameState.players[myId || ''];
  const playersList = Object.values(gameState.players);
  const totalPlayers = playersList.length;
  const isSim = (gameState.gameMode === 'simultaneous' || gameState.gameMode === 'simultaneousAuto') && gameState.status === 'playing';
  const doneCount = isSim ? playersList.filter(p => p.isAlive && p.isTurnDone).length : 0;
  const aliveCount = isSim ? playersList.filter(p => p.isAlive).length : 0;

  const [elapsedTime, setElapsedTime] = useState(0);

  useEffect(() => {
    if (gameState.status !== 'playing' || !gameState.startTime) {
      if (gameState.status === 'waiting') {
        setElapsedTime(0);
      }
      return;
    }

    const start = gameState.startTime;
    setElapsedTime(Math.max(0, Math.floor((Date.now() - start) / 1000)));

    const interval = setInterval(() => {
      setElapsedTime(Math.max(0, Math.floor((Date.now() - start) / 1000)));
    }, 1000);

    return () => clearInterval(interval);
  }, [gameState.status, gameState.startTime]);

  const formatTime = (seconds: number) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
  };

  return (
    <aside className="w-full md:w-64 bg-slate-900 border-b md:border-b-0 md:border-r border-slate-800 p-5 flex flex-col gap-4">
      {gameState.gameMode === 'classic' && gameState.status !== 'waiting' && (
        <div className="flex items-center justify-between bg-slate-950/80 border border-slate-800 rounded-xl p-3 shadow-inner shrink-0">
          <div className="flex items-center gap-2 text-slate-400">
            <Timer className="w-4 h-4 text-red-500 animate-pulse" />
            <span className="text-xs font-semibold uppercase tracking-wider">Temps</span>
          </div>
          <span className="text-xl font-mono font-bold text-red-500 tracking-wider">
            {formatTime(elapsedTime)}
          </span>
        </div>
      )}

      <div className="flex items-center justify-between pb-2 border-b border-slate-800">
        <div className="flex items-center gap-2 text-slate-400">
          <Users className="w-4 h-4" />
          <h2 className="font-semibold text-sm">
            {gameState.gameMode === 'turnByTurn' ? 'Joueurs' : 'Survivants'} ({totalPlayers})
          </h2>
        </div>
        {isSim && (
          <span className="text-[10px] font-bold uppercase tracking-wider text-amber-400">
            {doneCount}/{aliveCount} prêts
          </span>
        )}
      </div>

      <div className="flex-1 overflow-y-auto space-y-2.5 max-h-48 md:max-h-none">
        {playersList
          .sort((a, b) => b.score - a.score)
          .map((p, idx) => {
            const isMe = p.id === myId;
            const isCurrentTurn = gameState.gameMode === 'turnByTurn' && gameState.currentTurn === p.id;
            const isDone = isSim && p.isAlive && p.isTurnDone;
            const isHunting = isSim && p.isAlive && !p.isTurnDone;
            return (
              <div
                key={p.id}
                style={{ borderLeftColor: p.color }}
                className={`flex flex-col gap-1.5 p-2.5 rounded-lg border-l-4 bg-slate-950/60 border border-slate-800/50 ${
                  isMe ? 'bg-slate-800/30 border-slate-700' : ''
                } ${isCurrentTurn ? 'ring-2 ring-emerald-500/50 bg-emerald-500/5 border-emerald-500/20' : ''
                } ${isDone ? 'opacity-60' : ''}`}
              >
                <div className="flex items-center justify-between w-full">
                  <div className="flex items-center gap-2 min-w-0">
                    {idx === 0 && <Crown className="w-4 h-4 text-yellow-500 shrink-0 fill-yellow-500" />}
                    <span className="font-medium text-sm truncate" style={{ color: p.color }}>
                      {p.name} {isMe ? '(Vous)' : ''}
                    </span>
                  </div>

                  <div className="flex items-center gap-2 shrink-0">
                    <span className="text-xs bg-slate-800 px-2 py-0.5 rounded font-mono font-bold text-slate-300">
                      {p.score} pts
                    </span>
                    {gameState.gameMode !== 'turnByTurn' && !isSim && (
                      p.isAlive ? (
                        <div className="w-2 h-2 rounded-full bg-emerald-500 animate-pulse" title="Vivant" />
                      ) : (
                        <span title="Mort !">
                          <Skull className="w-4 h-4 text-red-500" />
                        </span>
                      )
                    )}
                    {isDone && <CheckCircle2 className="w-4 h-4 text-amber-400 shrink-0" />}
                    {isHunting && <Crosshair className="w-4 h-4 text-red-400 shrink-0 animate-pulse" />}
                  </div>
                </div>

                {isCurrentTurn && (
                  <div className="text-[10px] font-bold text-emerald-400 uppercase tracking-widest flex items-center gap-1.5 mt-0.5 animate-pulse">
                    <span className="w-1.5 h-1.5 rounded-full bg-emerald-400"></span>
                    C'est son tour !
                  </div>
                )}
                {isDone && (
                  <div className="text-[10px] font-bold text-amber-400 uppercase tracking-widest flex items-center gap-1.5 mt-0.5">
                    <span className="w-1.5 h-1.5 rounded-full bg-amber-400"></span>
                    Tour validé
                  </div>
                )}
                {isHunting && (
                  <div className="text-[10px] font-bold text-red-400 uppercase tracking-widest flex items-center gap-1.5 mt-0.5 animate-pulse">
                    <span className="w-1.5 h-1.5 rounded-full bg-red-400"></span>
                    En chasse...
                  </div>
                )}
              </div>
            );
          })}
      </div>

      {me && !me.isAlive && gameState.status === 'playing' && (
        <div className="bg-red-500/10 border border-red-500/20 p-4 rounded-xl text-center">
          <Skull className="w-8 h-8 text-red-500 mx-auto mb-2 animate-bounce" />
          <h3 className="font-bold text-red-500 text-sm">Vous avez explosé !</h3>
          <p className="text-xs text-slate-400 mt-1">
            Attendez que vos coéquipiers finissent ou cliquez sur Recommencer !
          </p>
        </div>
      )}
    </aside>
  );
}
