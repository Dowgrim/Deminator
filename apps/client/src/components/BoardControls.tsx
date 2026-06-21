interface BoardControlsProps {
  status: string;
  showResults: boolean;
  setShowResults: (show: boolean) => void;
  setZoom: (zoom: number | ((prev: number) => number)) => void;
  resetView: () => void;
}

export default function BoardControls({
  status,
  showResults,
  setShowResults,
  setZoom,
  resetView,
}: BoardControlsProps) {
  return (
    <div className="absolute bottom-4 md:bottom-6 right-4 md:right-6 flex items-center gap-2 bg-slate-900/90 border border-slate-800 p-2 rounded-2xl shadow-xl z-20 select-none">
      {(status === 'won' || status === 'gameover') && !showResults && (
        <button
          onClick={() => setShowResults(true)}
          className="px-3 h-8 flex items-center justify-center bg-red-600 hover:bg-red-500 text-white font-bold rounded-lg text-xs transition-all animate-pulse mr-1"
          title="Voir les résultats"
        >
          🏆 Résultats
        </button>
      )}
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
  );
}
