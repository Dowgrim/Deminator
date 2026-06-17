import express from 'express';
import { createServer } from 'http';
import { Server } from 'socket.io';
import cors from 'cors';
import { GameController } from './controllers/GameController.js';

const app = express();
app.use(cors());

const httpServer = createServer(app);
const io = new Server(httpServer, {
  cors: {
    origin: '*', // Allows clients from any origin (e.g. localhost:3000)
    methods: ['GET', 'POST'],
  },
});

// Initialize our GameController to handle MVC orchestration
const gameController = new GameController(io);

io.on('connection', (socket) => {
  gameController.handleConnection(socket);
});

const PORT = Number(process.env.PORT) || 4224;
const HOST = process.env.PORT ? '0.0.0.0' : 'localhost'; // Use 0.0.0.0 for Render / cloud deployment, localhost for local
httpServer.listen(PORT, HOST, () => {
  console.log(`[Server Running] Listening on http://${HOST}:${PORT}`);
});
