import React from 'react';
import { MessageSquare, Send } from 'lucide-react';
import { GameState } from '../types/GameState.js';

interface ChatProps {
  gameState: GameState;
  chatMessage: string;
  setChatMessage: (msg: string) => void;
  handleSendMessage: (e: React.FormEvent) => void;
  chatEndRef: React.RefObject<HTMLDivElement>;
}

export default function Chat({
  gameState,
  chatMessage,
  setChatMessage,
  handleSendMessage,
  chatEndRef,
}: ChatProps) {
  return (
    <aside className="w-full md:w-80 bg-slate-900 border-t md:border-t-0 md:border-l border-slate-800 flex flex-col h-full">
      <div className="p-4 border-b border-slate-800 flex items-center gap-2 text-slate-400">
        <MessageSquare className="w-4 h-4" />
        <h2 className="font-semibold text-sm">Chat d'Escouade</h2>
      </div>

      {/* Chat Messages */}
      <div className="flex-1 overflow-y-auto p-4 space-y-3">
        {gameState.chat.map((msg) => {
          const isSystem = msg.sender === 'system';
          return (
            <div key={msg.id} className="text-xs">
              {isSystem ? (
                <div className="bg-slate-950/50 text-slate-500 border border-slate-800/50 px-3 py-1.5 rounded-xl font-mono text-center">
                  {msg.text}
                </div>
              ) : (
                <div className="space-y-1">
                  <div className="flex items-center gap-1.5">
                    <span 
                      className="font-bold font-mono"
                      style={{ color: gameState.players[msg.sender]?.color || '#94A3B8' }}
                    >
                      {msg.senderName}
                    </span>
                    <span className="text-[10px] text-slate-500">
                      {new Date(msg.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                    </span>
                  </div>
                  <p className="bg-slate-950/80 text-slate-200 border border-slate-800/40 px-3 py-2 rounded-xl rounded-tl-none inline-block max-w-[85%] break-words">
                    {msg.text}
                  </p>
                </div>
              )}
            </div>
          );
        })}
        <div ref={chatEndRef} />
      </div>

      {/* Chat Input */}
      <form onSubmit={handleSendMessage} className="p-3 border-t border-slate-800 bg-slate-950/40 flex gap-2">
        <input
          type="text"
          maxLength={100}
          value={chatMessage}
          onChange={(e) => setChatMessage(e.target.value)}
          placeholder="Envoyer un message..."
          className="flex-1 px-3.5 py-2 rounded-xl bg-slate-900 border border-slate-800 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:ring-1 focus:ring-red-500 focus:border-transparent"
        />
        <button
          type="submit"
          disabled={!chatMessage.trim()}
          className="p-2 bg-red-600 hover:bg-red-500 disabled:opacity-40 disabled:cursor-not-allowed text-white rounded-xl transition-all"
        >
          <Send className="w-4 h-4 fill-current" />
        </button>
      </form>
    </aside>
  );
}
