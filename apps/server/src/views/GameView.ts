import { Server } from 'socket.io';
import { GameRoom } from '../models/GameRoom.js';

export class GameView {
  /**
   * Broadcasts the current game state to all clients in a specific room,
   * customized for each player if the game mode requires it.
   */
  public static broadcastRoomState(io: Server, roomId: string, room: GameRoom) {
    room.players.forEach((player, playerId) => {
      io.to(playerId).emit('roomState', room.getClientState(playerId));
    });
  }
}
