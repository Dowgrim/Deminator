import React, { useState, useEffect, useRef } from 'react';
import { io, Socket } from 'socket.io-client';
import { Bomb, Users, MessageSquare, ChevronLeft, ChevronRight } from 'lucide-react';
import { GameState } from './types/GameState.js';

// Import refactored components
import Lobby from './components/Lobby.js';
import Header from './components/Header.js';
import Leaderboard from './components/Leaderboard.js';
import Board from './components/Board.js';
import Chat from './components/Chat.js';

// Connect to the backend server
const SOCKET_URL = import.meta.env.VITE_SOCKET_URL || (window.location.hostname === 'localhost' 
  ? 'http://localhost:4224' 
  : `${window.location.protocol}//${window.location.hostname}:4224`);

export default function App() {
  const [socket, setSocket] = useState<Socket | null>(null);
  const [playerName, setPlayerName] = useState(() => {
    return localStorage.getItem('deminator_name') || '';
  });
  const [roomId, setRoomId] = useState('');
  const [isJoined, setIsJoined] = useState(false);
  const [gameState, setGameState] = useState<GameState | null>(null);
  const [chatMessage, setChatMessage] = useState('');
  const [difficulty, setDifficulty] = useState<'easy' | 'medium' | 'hard' | 'expert' | 'giant' | 'colossal' | 'supreme' | 'custom'>('medium');
  const [customRows, setCustomRows] = useState(20);
  const [customCols, setCustomCols] = useState(20);
  const [customMines, setCustomMines] = useState(40);
  const [gameMode, setGameMode] = useState<'classic' | 'turnByTurn' | 'simultaneous' | 'simultaneousAuto'>('classic');
  const [turnTimer, setTurnTimer] = useState(0); // seconds, 0 = no timer
  const [copied, setCopied] = useState(false);
  const [activeTab, setActiveTab] = useState<'board' | 'players' | 'chat'>('board');
  const [showLeftSidebar, setShowLeftSidebar] = useState(true);
  const [showRightSidebar, setShowRightSidebar] = useState(true);
  const [unreadCount, setUnreadCount] = useState(0);
  const [isMobile, setIsMobile] = useState(window.innerWidth < 768);
  const [error, setError] = useState<string | null>(null);
  
  const chatEndRef = useRef<HTMLDivElement>(null);
  const prevChatLengthRef = useRef(0);

  const isChatVisible = isMobile ? activeTab === 'chat' : showRightSidebar;

  const scrollToBottom = (behavior: 'smooth' | 'auto' = 'smooth') => {
    chatEndRef.current?.scrollIntoView({ behavior });
  };

  // Track resizing to detect mobile / desktop layout
  useEffect(() => {
    const handleResize = () => {
      setIsMobile(window.innerWidth < 768);
    };
    window.addEventListener('resize', handleResize);
    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  // Scroll to bottom of chat or increment unread count when new messages arrive
  useEffect(() => {
    if (!gameState?.chat) return;

    const currentLength = gameState.chat.length;
    const prevLength = prevChatLengthRef.current;

    if (currentLength > prevLength) {
      if (!isChatVisible) {
        setUnreadCount((prev) => prev + (currentLength - prevLength));
      } else {
        scrollToBottom('smooth');
      }
    }

    prevChatLengthRef.current = currentLength;
  }, [gameState?.chat, isChatVisible]);

  // Clear unread count and scroll to bottom when chat becomes visible ("on arrive dessus")
  useEffect(() => {
    if (isChatVisible) {
      setUnreadCount(0);
      const timer = setTimeout(() => {
        scrollToBottom('auto');
      }, 50);
      return () => clearTimeout(timer);
    }
  }, [isChatVisible]);

  // Clear errors when the player name or room ID changes
  useEffect(() => {
    setError(null);
  }, [playerName, roomId]);

  // Initialize socket connection
  useEffect(() => {
    const newSocket = io(SOCKET_URL, {
      transports: ['websocket'],
    });
    setSocket(newSocket);

    newSocket.on('connect', () => {
      console.log('Connecté au serveur WebSocket');
    });

    newSocket.on('roomState', (state: GameState) => {
      setGameState(state);
      setIsJoined(true);
      setError(null);
    });

    newSocket.on('joinError', (message: string) => {
      setError(message);
    });

    return () => {
      newSocket.disconnect();
    };
  }, []);

  const handleJoin = (e: React.FormEvent) => {
    e.preventDefault();
    if (!socket || !playerName.trim() || !roomId.trim()) return;

    localStorage.setItem('deminator_name', playerName.trim());
    socket.emit('joinRoom', {
      roomId: roomId.trim().toUpperCase(),
      playerName: playerName.trim(),
    });
  };

  const createRandomRoom = () => {
    const randomCode = Math.random().toString(36).substring(2, 8).toUpperCase();
    setRoomId(randomCode);
  };

  const handleReveal = (row: number, col: number) => {
    if (!socket || !gameState) return;
    socket.emit('revealCell', { roomId: gameState.roomId, row, col });
  };

  const handleFlag = (e: React.MouseEvent, row: number, col: number) => {
    e.preventDefault(); // Prevent standard context menu
    if (!socket || !gameState) return;
    socket.emit('toggleFlag', { roomId: gameState.roomId, row, col });
  };

  const handleStartGame = () => {
    if (!socket || !gameState) return;
    
    const timer = turnTimer;
    let config: any = { rows: 12, cols: 15, mines: 25, gameMode, turnTimer: timer }; // Medium
    if (difficulty === 'easy') {
      config = { rows: 8, cols: 10, mines: 10, gameMode, turnTimer: timer };
    } else if (difficulty === 'hard') {
      config = { rows: 16, cols: 20, mines: 50, gameMode, turnTimer: timer };
    } else if (difficulty === 'expert') {
      config = { rows: 30, cols: 30, mines: 150, gameMode, turnTimer: timer };
    } else if (difficulty === 'giant') {
      config = { rows: 100, cols: 100, mines: 1500, gameMode, turnTimer: timer };
    } else if (difficulty === 'colossal') {
      config = { rows: 500, cols: 500, mines: 35000, gameMode, turnTimer: timer };
    } else if (difficulty === 'supreme') {
      config = { rows: 1000, cols: 1000, mines: 150000, gameMode, turnTimer: timer };
    } else if (difficulty === 'custom') {
      config = { rows: customRows, cols: customCols, mines: customMines, gameMode, turnTimer: timer };
    }

    socket.emit('startGame', { roomId: gameState.roomId, config });
  };

  const handleSendMessage = (e: React.FormEvent) => {
    e.preventDefault();
    if (!socket || !gameState || !chatMessage.trim()) return;

    socket.emit('sendMessage', { roomId: gameState.roomId, text: chatMessage.trim() });
    setChatMessage('');
  };

  const copyRoomId = () => {
    if (!gameState) return;
    navigator.clipboard.writeText(gameState.roomId);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  // Lobby rendering
  if (!isJoined) {
    return (
      <Lobby
        playerName={playerName}
        setPlayerName={setPlayerName}
        roomId={roomId}
        setRoomId={setRoomId}
        handleJoin={handleJoin}
        createRandomRoom={createRandomRoom}
        error={error}
      />
    );
  }

  // Loading Room rendering
  if (!gameState) {
    return (
      <div className="flex flex-col items-center justify-center min-h-screen bg-slate-950 text-slate-400">
        <Bomb className="w-12 h-12 text-red-500 animate-spin mb-4" />
        <p>Connexion à la partie en cours...</p>
      </div>
    );
  }

  const myId = socket?.id;

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 flex flex-col h-screen overflow-hidden">
      <Header
        gameState={gameState}
        difficulty={difficulty}
        setDifficulty={setDifficulty}
        customRows={customRows}
        setCustomRows={setCustomRows}
        customCols={customCols}
        setCustomCols={setCustomCols}
        customMines={customMines}
        setCustomMines={setCustomMines}
        gameMode={gameMode}
        setGameMode={setGameMode}
        turnTimer={turnTimer}
        setTurnTimer={setTurnTimer}
        handleStartGame={handleStartGame}
        copyRoomId={copyRoomId}
        copied={copied}
        myId={myId}
      />

      <div className="flex-1 flex flex-col md:flex-row overflow-hidden relative">
        {/* Left Sidebar (Leaderboard) */}
        <div className={`${activeTab === 'players' ? 'flex flex-col flex-1 h-full' : 'hidden'} ${showLeftSidebar ? 'md:flex' : 'md:hidden'} md:flex-col md:h-full md:w-64 md:flex-none`}>
          <Leaderboard 
            gameState={gameState} 
            myId={myId} 
          />
        </div>

        {/* Main Board Container */}
        <div className={`${activeTab === 'board' ? 'flex flex-col flex-1 h-full' : 'hidden'} md:flex md:flex-col md:h-full md:flex-1 relative`}>
          {/* Toggle buttons for sidebars (visible on desktop) */}
          <div className="hidden md:flex absolute top-4 left-4 z-30">
            <button
              onClick={() => setShowLeftSidebar(!showLeftSidebar)}
              className="p-2 bg-slate-900/90 hover:bg-slate-800 text-slate-200 border border-slate-800 rounded-xl shadow-lg transition-all flex items-center justify-center gap-1"
              title={showLeftSidebar ? "Masquer la zone de gauche" : "Afficher la zone de gauche"}
            >
              {showLeftSidebar ? (
                <>
                  <ChevronLeft className="w-4 h-4 text-red-500" />
                  <span className="text-xs font-semibold pr-1">Masquer</span>
                </>
              ) : (
                <>
                  <ChevronRight className="w-4 h-4 text-emerald-500" />
                  <span className="text-xs font-semibold pr-1">Afficher Gauche</span>
                </>
              )}
            </button>
          </div>

          <div className="hidden md:flex absolute top-4 right-4 z-30">
            <button
              onClick={() => setShowRightSidebar(!showRightSidebar)}
              className="relative p-2 bg-slate-900/90 hover:bg-slate-800 text-slate-200 border border-slate-800 rounded-xl shadow-lg transition-all flex items-center justify-center gap-1"
              title={showRightSidebar ? "Masquer le chat" : "Afficher le chat"}
            >
              {showRightSidebar ? (
                <>
                  <span className="text-xs font-semibold pl-1">Masquer</span>
                  <ChevronRight className="w-4 h-4 text-red-500" />
                </>
              ) : (
                <>
                  <span className="text-xs font-semibold pl-1">Afficher Chat</span>
                  <ChevronLeft className="w-4 h-4 text-emerald-500" />
                  {unreadCount > 0 && (
                    <span className="absolute -top-1.5 -right-1.5 bg-red-600 text-white text-[9px] font-bold h-4 min-w-[16px] px-1 flex items-center justify-center rounded-full border border-slate-950 shadow-md">
                      {unreadCount}
                    </span>
                  )}
                </>
              )}
            </button>
          </div>

          <Board
            gameState={gameState}
            myId={myId}
            handleReveal={handleReveal}
            handleFlag={handleFlag}
            handleStartGame={handleStartGame}
          />
        </div>

        {/* Right Sidebar (Chat) */}
        <div className={`${activeTab === 'chat' ? 'flex flex-col flex-1 h-full' : 'hidden'} ${showRightSidebar ? 'md:flex' : 'md:hidden'} md:flex-col md:h-full md:w-80 md:flex-none`}>
          <Chat
            gameState={gameState}
            chatMessage={chatMessage}
            setChatMessage={setChatMessage}
            handleSendMessage={handleSendMessage}
            chatEndRef={chatEndRef}
          />
        </div>
      </div>

      {/* Mobile Tab Bar */}
      <div className="md:hidden flex border-t border-slate-800 bg-slate-900 text-slate-400 shrink-0 select-none z-10">
        <button
          onClick={() => setActiveTab('players')}
          className={`flex-1 py-3 text-center text-xs font-semibold flex flex-col items-center gap-1 border-r border-slate-800 ${
            activeTab === 'players' ? 'text-red-500 bg-slate-950/40 font-bold' : 'hover:text-slate-200'
          }`}
        >
          <Users className="w-5 h-5" />
          Survivants ({Object.keys(gameState.players).length})
        </button>
        <button
          onClick={() => setActiveTab('board')}
          className={`flex-1 py-3 text-center text-xs font-semibold flex flex-col items-center gap-1 ${
            activeTab === 'board' ? 'text-red-500 bg-slate-950/40 font-bold' : 'hover:text-slate-200'
          }`}
        >
          <Bomb className="w-5 h-5" />
          Grille
        </button>
        <button
          onClick={() => setActiveTab('chat')}
          className={`flex-1 py-3 text-center text-xs font-semibold flex flex-col items-center gap-1 border-l border-slate-800 ${
            activeTab === 'chat' ? 'text-red-500 bg-slate-950/40 font-bold' : 'hover:text-slate-200'
          }`}
        >
          <div className="relative">
            <MessageSquare className="w-5 h-5" />
            {unreadCount > 0 && activeTab !== 'chat' && (
              <span className="absolute -top-1.5 -right-1.5 bg-red-600 text-white text-[9px] font-bold h-4 min-w-[16px] px-1 flex items-center justify-center rounded-full border border-slate-950 shadow-md">
                {unreadCount}
              </span>
            )}
          </div>
          Chat
        </button>
      </div>
    </div>
  );
}
