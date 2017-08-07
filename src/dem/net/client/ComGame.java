package dem.net.client;

import dem.net.client.actions.sendAndRecieve.CommandMove;
import dem.net.client.actions.receiveOnly.*;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComGame extends Communicator {
	public ComGame(Deminator d) {
		CommandMove cm = new CommandMove(d);

		super.newReceiveAction("MOV", cm);
		super.newReceiveAction("EXP", new CommandExplose(d));
		super.newReceiveAction("DIS", new CommandDiscover(d));
		super.newReceiveAction("STUN", new CommandStun(d));
		super.newReceiveAction("PTS", new CommandPoints(d));
		super.newReceiveAction("END", new CommandEnd(d));

		super.newSendAction("MOV", cm);
	}
}
