import { Bomb, Play } from 'lucide-react';
import type { FormEvent } from 'react';

interface LobbyProps {
  playerName: string;
  setPlayerName: (name: string) => void;
  roomId: string;
  setRoomId: (id: string) => void;
  handleJoin: (e: FormEvent) => void;
  createRandomRoom: () => void;
}

export default function Lobby({
  playerName,
  setPlayerName,
  roomId,
  setRoomId,
  handleJoin,
  createRandomRoom,
}: LobbyProps) {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-slate-950 p-6 text-slate-100">
      <div className="w-full max-w-md bg-slate-900 border border-slate-800 rounded-2xl p-8 shadow-2xl">
        <div className="flex flex-col items-center mb-8">
          <div className="w-20 h-20 bg-red-500/10 rounded-3xl flex items-center justify-center border border-red-500/20 mb-4 animate-bounce">
            <Bomb className="w-12 h-12 text-red-500" />
          </div>
          <h1 className="text-4xl font-extrabold tracking-tight bg-gradient-to-r from-red-500 via-orange-500 to-yellow-500 bg-clip-text text-transparent">
            DEMINATOR
          </h1>
          <p className="text-slate-400 text-sm mt-1">Démineur Multijoueur en Temps Réel</p>
        </div>

        <form onSubmit={handleJoin} className="space-y-5">
          <div>
            <label className="block text-sm font-semibold text-slate-300 mb-2">Votre Pseudo</label>
            <input
              type="text"
              required
              maxLength={15}
              value={playerName}
              onChange={(e) => setPlayerName(e.target.value)}
              placeholder="Entrez votre pseudo..."
              className="w-full px-4 py-3 rounded-xl bg-slate-800 border border-slate-700 text-slate-100 placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-transparent transition-all"
            />
          </div>

          <div>
            <label className="block text-sm font-semibold text-slate-300 mb-2">Code de la Partie</label>
            <div className="flex gap-2">
              <input
                type="text"
                required
                maxLength={10}
                value={roomId}
                onChange={(e) => setRoomId(e.target.value)}
                placeholder="CODE"
                className="w-full px-4 py-3 rounded-xl bg-slate-800 border border-slate-700 text-slate-100 placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-transparent transition-all uppercase font-mono tracking-widest text-center"
              />
              <button
                type="button"
                onClick={createRandomRoom}
                className="px-4 bg-slate-800 border border-slate-700 rounded-xl hover:bg-slate-700 hover:border-slate-600 font-semibold text-sm transition-all"
              >
                Nouveau
              </button>
            </div>
          </div>

          <button
            type="submit"
            disabled={!playerName.trim() || !roomId.trim()}
            className="w-full py-3.5 bg-gradient-to-r from-red-600 to-orange-600 hover:from-red-500 hover:to-orange-500 disabled:opacity-50 disabled:cursor-not-allowed text-white font-bold rounded-xl shadow-lg shadow-red-900/30 hover:shadow-red-800/40 transition-all flex items-center justify-center gap-2 mt-2"
          >
            <Play className="w-5 h-5 fill-current" />
            Rejoindre l'arène
          </button>
        </form>
      </div>
    </div>
  );
}
