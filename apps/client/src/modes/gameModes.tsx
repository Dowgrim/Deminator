import React from 'react';
import { GameState } from '../types/GameState.js';
import { Player } from '../types/Player.js';

export type GameModeType = 'classic' | 'turnByTurn' | 'simultaneous';

export interface GameModeConfig {
  key: GameModeType;
  label: string;
  selectLabel: string;
  allowFlag: boolean;
  isDisabled(gameState: GameState, myId: string | undefined, me: Player | undefined): boolean;
  renderBanner(gameState: GameState, myId: string | undefined): React.ReactNode | null;
}

export const GAME_MODES: Record<GameModeType, GameModeConfig> = {
  classic: {
    key: 'classic',
    label: 'Classique',
    selectLabel: 'Mode Classique (Coop)',
    allowFlag: true,
    isDisabled: (gameState, _myId, me) => {
      return gameState.status !== 'playing' || (me ? !me.isAlive : false);
    },
    renderBanner: () => null,
  },
  turnByTurn: {
    key: 'turnByTurn',
    label: 'Tour par Tour',
    selectLabel: 'Mode Tour par Tour (Démineur)',
    allowFlag: true,
    isDisabled: (gameState, myId) => {
      return gameState.status !== 'playing' || gameState.currentTurn !== myId;
    },
    renderBanner: (gameState, myId) => {
      const activePlayer = gameState.players[gameState.currentTurn || ''];
      return (
        <div className="mb-6 px-6 py-2.5 bg-slate-900 border border-slate-800 rounded-2xl flex items-center gap-3 animate-fade-in text-center max-w-md shadow-lg">
          <span className="w-2.5 h-2.5 rounded-full bg-emerald-500 animate-pulse shrink-0" />
          <div className="text-sm font-semibold">
            {gameState.currentTurn === myId ? (
              <span className="text-emerald-400">C'est votre tour ! Cliquez sur des bombes.</span>
            ) : (
              <span className="text-slate-300">
                Tour de : <span style={{ color: activePlayer?.color || '#fff' }} className="font-bold">{activePlayer?.name || 'Inconnu'}</span>
              </span>
            )}
          </div>
        </div>
      );
    },
  },
  simultaneous: {
    key: 'simultaneous',
    label: 'Chasse Simultanée',
    selectLabel: 'Mode Chasse Simultanée',
    allowFlag: false,
    isDisabled: (gameState, _myId, me) => {
      if (gameState.status !== 'playing') return true;
      return me?.isTurnDone || false;
    },
    renderBanner: (gameState, myId) => {
      const me = gameState.players[myId || ''];
      const isMeDone = me?.isTurnDone || false;
      return (
        <div className="mb-6 px-6 py-2.5 bg-slate-900 border border-slate-800 rounded-2xl flex items-center gap-3 animate-fade-in text-center max-w-md shadow-lg">
          <span className={`w-2.5 h-2.5 rounded-full ${isMeDone ? 'bg-amber-500 animate-pulse' : 'bg-red-500 animate-bounce'} shrink-0`} />
          <div className="text-sm font-semibold">
            {isMeDone ? (
              <span className="text-amber-400 font-bold">Votre tour est validé ! En attente des autres démineurs...</span>
            ) : (
              <span className="text-red-400">
                Chasse simultanée active ! Trouvez un <span className="underline font-bold">maximum de bombes</span> puis cliquez sur une case vide.
              </span>
            )}
          </div>
        </div>
      );
    },
  },
};

export const getGameModeConfig = (mode: string): GameModeConfig => {
  return GAME_MODES[mode as GameModeType] || GAME_MODES.classic;
};
