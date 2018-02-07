package dem.net.client.status;

import dem.net.client.actions.sendAndRecieve.CommandMove;
import dem.net.client.actions.receiveOnly.*;
import dem.net.util.ComStatus;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComGame extends ComStatus {
	public ComGame(Deminator d) {
		CommandMove cm = new CommandMove(d);

		super.newReceiveAction(cm);
		super.newReceiveAction(new CommandExplose(d));
		super.newReceiveAction(new CommandDiscover(d));
		super.newReceiveAction(new CommandStun(d));
		super.newReceiveAction(new CommandPoints(d));
		super.newReceiveAction(new CommandEnd(d));

		super.newSendAction(cm);
	}
}
