import { Player } from '../types/Player.js';
import { ServerCell } from '../types/ServerCell.js';
import { ClientCell } from '../types/ClientCell.js';
import { ChatMessage } from '../types/ChatMessage.js';
import { GameState } from '../types/GameState.js';
import { GameConfig } from '../types/GameConfig.js';
import { GameModeRegistry } from '../modes/GameModeRegistry.js';

export class GameRoom {
  public roomId: string;
  public status: 'waiting' | 'playing' | 'gameover' | 'won' = 'waiting';
  public players: Map<string, Player> = new Map();
  public serverBoard: ServerCell[][] = [];
  public config: GameConfig = { rows: 12, cols: 15, mines: 25 }; // Standard medium size
  public minesRemaining: number = 0;
  public chat: ChatMessage[] = [];
  public firstClickDone: boolean = false;
  public gameMode: 'classic' | 'turnByTurn' | 'simultaneous' = 'classic';
  public currentTurn: string | null = null;
  public playerOrder: string[] = [];
  public hostId: string | null = null;
  public startTime: number | null = null;

  // Simultaneous game mode specific states
  public simultaneousPendingReveals: Map<string, { row: number; col: number }[]> = new Map();
  public simultaneousDonePlayers: Set<string> = new Set();

  private playerColors = [
    '#3B82F6', // Blue
    '#EF4444', // Red
    '#10B981', // Green
    '#F59E0B', // Yellow
    '#8B5CF6', // Purple
    '#EC4899', // Pink
    '#06B6D4', // Cyan
    '#14B8A6', // Teal
  ];

  constructor(roomId: string) {
    this.roomId = roomId;
    this.resetBoard();
  }

  public resetBoard() {
    this.serverBoard = [];
    for (let r = 0; r < this.config.rows; r++) {
      const row: ServerCell[] = [];
      for (let c = 0; c < this.config.cols; c++) {
        row.push({
          row: r,
          col: c,
          isMine: false,
          neighborMines: 0,
          isRevealed: false,
          isFlagged: false,
          flaggedBy: null,
          revealedBy: null,
        });
      }
      this.serverBoard.push(row);
    }
    this.minesRemaining = this.config.mines;
    this.firstClickDone = false;
  }

  public addPlayer(id: string, name: string): Player {
    const existingCount = this.players.size;
    const color = this.playerColors[existingCount % this.playerColors.length];
    const player: Player = {
      id,
      name: name || `Joueur ${existingCount + 1}`,
      score: 0,
      color,
      isAlive: true,
    };
    this.players.set(id, player);
    if (!this.hostId) {
      this.hostId = id;
    }
    if (this.status === 'playing') {
      this.playerOrder.push(id);
    }
    return player;
  }

  public removePlayer(id: string) {
    this.players.delete(id);
    const index = this.playerOrder.indexOf(id);
    if (index !== -1) {
      this.playerOrder.splice(index, 1);
    }
    
    if (this.hostId === id) {
      this.hostId = this.players.keys().next().value || null;
    }
    
    // Delegate player disconnect logic to current game mode handler
    const handler = GameModeRegistry.getHandler(this.gameMode);
    handler.onPlayerDisconnect(this, id);

    // If room is empty, or playing and all remaining players are dead, check game status
    if (this.status === 'playing') {
      this.checkGameEndState();
    }
  }

  public startGame(config?: GameConfig) {
    if (config) {
      const rows = Math.max(1, Math.min(1000, Number(config.rows) || 12));
      const cols = Math.max(1, Math.min(1000, Number(config.cols) || 15));
      const maxMines = Math.max(1, rows * cols - 9);
      const mines = Math.max(1, Math.min(maxMines, Number(config.mines) || 25));
      this.config = {
        rows,
        cols,
        mines,
        gameMode: config.gameMode
      };
    }
    this.gameMode = (this.config.gameMode as any) || 'classic';
    this.resetBoard();
    this.status = 'playing';
    this.startTime = Date.now();
    
    // Revive all players and reset scores
    for (const player of this.players.values()) {
      player.isAlive = true;
      player.score = 0;
    }

    this.playerOrder = Array.from(this.players.keys());
    this.currentTurn = this.playerOrder[0] || null;

    // Delegate game start logic to mode handler
    const handler = GameModeRegistry.getHandler(this.gameMode);
    handler.onStartGame(this);
  }

  public advanceTurn() {
    if (this.playerOrder.length === 0) {
      this.currentTurn = null;
      return;
    }
    const currentIndex = this.currentTurn ? this.playerOrder.indexOf(this.currentTurn) : -1;
    let nextIndex = (currentIndex + 1) % this.playerOrder.length;
    
    let found = false;
    for (let i = 0; i < this.playerOrder.length; i++) {
      const potentialPlayerId = this.playerOrder[nextIndex];
      const p = this.players.get(potentialPlayerId);
      if (p && p.isAlive) {
        this.currentTurn = potentialPlayerId;
        found = true;
        break;
      }
      nextIndex = (nextIndex + 1) % this.playerOrder.length;
    }
    if (!found) {
      this.currentTurn = null;
    }
  }

  public generateMines(excludeRow: number, excludeCol: number) {
    const rows = this.config.rows;
    const cols = this.config.cols;
    let minesPlaced = 0;

    // Helper to check if a cell is adjacent to or is the excluded cell
    const isExcluded = (r: number, c: number) => {
      return Math.abs(r - excludeRow) <= 1 && Math.abs(c - excludeCol) <= 1;
    };

    while (minesPlaced < this.config.mines) {
      const r = Math.floor(Math.random() * rows);
      const c = Math.floor(Math.random() * cols);

      if (!this.serverBoard[r][c].isMine && !isExcluded(r, c)) {
        this.serverBoard[r][c].isMine = true;
        minesPlaced++;
      }
    }

    // Calculate neighbors
    for (let r = 0; r < rows; r++) {
      for (let c = 0; c < cols; c++) {
        if (!this.serverBoard[r][c].isMine) {
          let count = 0;
          for (let dr = -1; dr <= 1; dr++) {
            for (let dc = -1; dc <= 1; dc++) {
              const nr = r + dr;
              const nc = c + dc;
              if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                if (this.serverBoard[nr][nc].isMine) {
                  count++;
                }
              }
            }
          }
          this.serverBoard[r][c].neighborMines = count;
        }
      }
    }
  }

  public revealCell(playerId: string, row: number, col: number): boolean {
    const handler = GameModeRegistry.getHandler(this.gameMode);
    return handler.revealCell(this, playerId, row, col);
  }

  public revealCascade(row: number, col: number, playerId: string) {
    const queue: [number, number][] = [[row, col]];
    const visited = new Set<string>();
    visited.add(`${row},${col}`);
    const rows = this.config.rows;
    const cols = this.config.cols;
    const player = this.players.get(playerId);

    while (queue.length > 0) {
      const [r, c] = queue.shift()!;
      const cell = this.serverBoard[r][c];
      if (cell.isRevealed || cell.isFlagged) continue;

      cell.isRevealed = true;
      cell.revealedBy = playerId;
      if (player) {
        player.score += 1; // 1 point per safe cell revealed
      }

      if (cell.neighborMines === 0) {
        for (let dr = -1; dr <= 1; dr++) {
          for (let dc = -1; dc <= 1; dc++) {
            const nr = r + dr;
            const nc = c + dc;
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
              const neighbor = this.serverBoard[nr][nc];
              if (!neighbor.isRevealed && !neighbor.isMine && !neighbor.isFlagged) {
                const key = `${nr},${nc}`;
                if (!visited.has(key)) {
                  visited.add(key);
                  queue.push([nr, nc]);
                }
              }
            }
          }
        }
      }
    }
  }

  public toggleFlag(playerId: string, row: number, col: number): boolean {
    const handler = GameModeRegistry.getHandler(this.gameMode);
    return handler.toggleFlag(this, playerId, row, col);
  }

  public checkGameEndState() {
    // Check if all players are dead
    const alivePlayers = Array.from(this.players.values()).filter(p => p.isAlive);
    if (this.players.size > 0 && alivePlayers.length === 0) {
      this.status = 'gameover';
      this.revealAllMines();
      return;
    }

    // Check if won (all safe cells are revealed)
    let won = true;
    for (let r = 0; r < this.config.rows; r++) {
      for (let c = 0; c < this.config.cols; c++) {
        const cell = this.serverBoard[r][c];
        if (!cell.isMine && !cell.isRevealed) {
          won = false;
          break;
        }
      }
      if (!won) break;
    }

    if (won) {
      this.status = 'won';
      this.revealAllMines();
    }
  }

  public revealAllMines() {
    for (let r = 0; r < this.config.rows; r++) {
      for (let c = 0; c < this.config.cols; c++) {
        const cell = this.serverBoard[r][c];
        if (cell.isMine) {
          cell.isRevealed = true;
        }
      }
    }
  }

  public addChatMessage(senderId: string, text: string) {
    const player = this.players.get(senderId);
    const message: ChatMessage = {
      id: Math.random().toString(36).substring(2, 9),
      sender: senderId,
      senderName: player ? player.name : 'Système',
      text,
      timestamp: Date.now(),
    };
    this.chat.push(message);
    // Keep last 50 messages
    if (this.chat.length > 50) {
      this.chat.shift();
    }
  }

  public getClientState(playerId?: string): GameState {
    const handler = GameModeRegistry.getHandler(this.gameMode);
    const board = handler.getClientBoard(this, playerId);

    const playersRecord: Record<string, Player> = {};
    this.players.forEach((player, id) => {
      playersRecord[id] = player;
    });

    return {
      roomId: this.roomId,
      status: this.status,
      players: playersRecord,
      board,
      minesRemaining: Math.max(0, this.minesRemaining),
      chat: this.chat,
      totalMines: this.config.mines,
      gameMode: this.gameMode,
      currentTurn: this.currentTurn,
      hostId: this.hostId,
      startTime: this.startTime,
    };
  }
}
