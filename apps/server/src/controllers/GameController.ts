import { Server, Socket } from 'socket.io';
import { GameRoom } from '../models/GameRoom.js';
import { GameConfig } from '../types/GameConfig.js';
import { GameView } from '../views/GameView.js';

export class GameController {
  private io: Server;
  // Map of roomId -> GameRoom
  private rooms: Map<string, GameRoom>;
  // Map of socketId -> roomId (to quickly find which room a player is in on disconnect)
  private socketToRoom: Map<string, string>;

  constructor(io: Server) {
    this.io = io;
    this.rooms = new Map<string, GameRoom>();
    this.socketToRoom = new Map<string, string>();
  }

  /**
   * Registers all websocket message listeners for a newly connected socket.
   */
  public handleConnection(socket: Socket) {
    console.log(`[Socket Connected] ID: ${socket.id}`);

    socket.on('joinRoom', (data: { roomId: string; playerName: string }) => {
      this.handleJoinRoom(socket, data);
    });

    socket.on('revealCell', (data: { roomId: string; row: number; col: number }) => {
      this.handleRevealCell(socket, data);
    });

    socket.on('toggleFlag', (data: { roomId: string; row: number; col: number }) => {
      this.handleToggleFlag(socket, data);
    });

    socket.on('startGame', (data: { roomId: string; config?: GameConfig }) => {
      this.handleStartGame(socket, data);
    });

    socket.on('sendMessage', (data: { roomId: string; text: string }) => {
      this.handleSendMessage(socket, data);
    });

    socket.on('disconnect', () => {
      this.handleDisconnect(socket);
    });
  }

  private handleJoinRoom(socket: Socket, { roomId, playerName }: { roomId: string; playerName: string }) {
    const cleanRoomId = roomId.trim().toUpperCase() || 'DEFAULT';
    const cleanPlayerName = playerName.trim();
    
    // Get or create GameRoom model
    let room = this.rooms.get(cleanRoomId);
    if (!room) {
      room = new GameRoom(cleanRoomId);
      room.broadcastCallback = () => {
        GameView.broadcastRoomState(this.io, cleanRoomId, room!);
      };
      this.rooms.set(cleanRoomId, room);
      console.log(`[Room Created] Room ID: ${cleanRoomId}`);
    } else {
      // Check if player name already exists in the room
      const nameExists = Array.from(room.players.values()).some(
        p => p.name.trim().toLowerCase() === cleanPlayerName.toLowerCase()
      );
      if (nameExists) {
        socket.emit('joinError', 'Ce pseudo est déjà utilisé dans ce salon.');
        return;
      }
    }

    // Join Socket.io room
    socket.join(cleanRoomId);

    const isReconnect = room.hasSnapshot(cleanPlayerName);

    // Add player to the room (restores snapshot if available)
    const player = room.addPlayer(socket.id, cleanPlayerName);
    this.socketToRoom.set(socket.id, cleanRoomId);

    console.log(`[Player ${isReconnect ? 'Reconnected' : 'Joined'}] Player "${player.name}" (${socket.id}) -> Room: ${cleanRoomId}`);

    // System message in chat
    room.addChatMessage('system', isReconnect ? `${player.name} est de retour !` : `${player.name} a rejoint la partie.`);

    // Broadcast updated state to all in the room via View
    GameView.broadcastRoomState(this.io, cleanRoomId, room);
  }

  private handleRevealCell(socket: Socket, { roomId, row, col }: { roomId: string; row: number; col: number }) {
    const room = this.rooms.get(roomId);
    if (!room) return;

    const changed = room.revealCell(socket.id, row, col);
    if (changed) {
      GameView.broadcastRoomState(this.io, roomId, room);
    }
  }

  private handleToggleFlag(socket: Socket, { roomId, row, col }: { roomId: string; row: number; col: number }) {
    const room = this.rooms.get(roomId);
    if (!room) return;

    const changed = room.toggleFlag(socket.id, row, col);
    if (changed) {
      GameView.broadcastRoomState(this.io, roomId, room);
    }
  }

  private handleStartGame(socket: Socket, { roomId, config }: { roomId: string; config?: GameConfig }) {
    const room = this.rooms.get(roomId);
    if (!room) return;

    if (room.hostId && room.hostId !== socket.id) {
      return;
    }

    room.startGame(config);
    const player = room.players.get(socket.id);
    const starterName = player ? player.name : 'Le créateur du salon';
    room.addChatMessage('system', `La partie a été démarrée par ${starterName} ! Bonne chance !`);
    
    GameView.broadcastRoomState(this.io, roomId, room);
  }

  private handleSendMessage(socket: Socket, { roomId, text }: { roomId: string; text: string }) {
    const room = this.rooms.get(roomId);
    if (!room) return;

    room.addChatMessage(socket.id, text);
    
    GameView.broadcastRoomState(this.io, roomId, room);
  }

  private handleDisconnect(socket: Socket) {
    const roomId = this.socketToRoom.get(socket.id);
    if (roomId) {
      const room = this.rooms.get(roomId);
      if (room) {
        const player = room.players.get(socket.id);
        const name = player ? player.name : 'Un joueur';
        
        room.removePlayer(socket.id);
        this.socketToRoom.delete(socket.id);
        
        console.log(`[Player Left] Player "${name}" (${socket.id}) left Room: ${roomId}`);

        if (room.players.size === 0) {
          // If no more players, clear timers and delete room to save memory
          room.clearTurnTimer();
          this.rooms.delete(roomId);
          console.log(`[Room Deleted] Room ${roomId} is empty.`);
        } else {
          room.addChatMessage('system', `${name} a quitté la partie.`);
          GameView.broadcastRoomState(this.io, roomId, room);
        }
      }
    }
  }
}
