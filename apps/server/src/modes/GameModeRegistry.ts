import { GameModeHandler } from './GameModeHandler.js';
import { ClassicModeHandler } from './ClassicModeHandler.js';
import { TurnByTurnModeHandler } from './TurnByTurnModeHandler.js';
import { SimultaneousModeHandler } from './SimultaneousModeHandler.js';

export class GameModeRegistry {
  private static handlers = new Map<string, GameModeHandler>([
    ['classic', new ClassicModeHandler()],
    ['turnByTurn', new TurnByTurnModeHandler()],
    ['simultaneous', new SimultaneousModeHandler()],
  ]);

  public static getHandler(mode: string): GameModeHandler {
    return this.handlers.get(mode) || this.handlers.get('classic')!;
  }
}
