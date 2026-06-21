import { useEffect, useState } from "react";

export function TurnTimerCountdown({ turnTimerEnd }: { turnTimerEnd: number }) {
  const [remaining, setRemaining] = useState(Math.max(0, Math.ceil((turnTimerEnd - Date.now()) / 1000)));

  useEffect(() => {
    const interval = setInterval(() => {
      setRemaining(Math.max(0, Math.ceil((turnTimerEnd - Date.now()) / 1000)));
    }, 200);
    return () => clearInterval(interval);
  }, [turnTimerEnd]);

  const isUrgent = remaining <= 5;
  return (
    <span className={`font-mono font-bold ml-2 ${isUrgent ? 'text-red-400 animate-pulse' : 'text-slate-300'}`}>
      {remaining}s
    </span>
  );
}