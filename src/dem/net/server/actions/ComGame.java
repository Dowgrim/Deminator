package dem.net.server.actions;

import dem.net.server.actions.sendOnly.*;
import dem.net.server.actions.sendAndRecieve.*;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComGame extends Communicator {
	public ComGame(Deminator d) {
		CommandMove cm = new CommandMove(d);

		super.newReceiveAction("MOV", cm);

		super.newSendAction("MOV", cm);
		super.newSendAction("EXP", new CommandExplose());
		super.newSendAction("DIS", new CommandDiscover());
		super.newSendAction("STUN", new CommandStun());
		super.newSendAction("PTS", new CommandPoints());
		super.newSendAction("END", new CommandEnd());
	}
}
