import { Users, Play } from 'lucide-react';
import { GameState } from '../types/GameState.js';

interface BoardWaitingProps {
  gameState: GameState;
  isHost: boolean;
  handleStartGame: () => void;
}

export default function BoardWaiting({
  gameState,
  isHost,
  handleStartGame,
}: BoardWaitingProps) {
  return (
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
  );
}
