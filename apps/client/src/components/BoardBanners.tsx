import { Skull, Crown } from 'lucide-react';
import { GameState } from '../types/GameState.js';

interface BoardBannersProps {
  gameState: GameState;
  isZoomedOut: boolean;
  currentModeConfig: any;
  myId: string | undefined;
}

export default function BoardBanners({
  gameState,
  isZoomedOut,
  currentModeConfig,
  myId,
}: BoardBannersProps) {
  return (
    <>
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
    </>
  );
}
