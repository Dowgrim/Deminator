package dem.net.server.status;

import dem.net.server.actions.sendOnly.*;
import dem.net.server.actions.sendAndRecieve.*;
import dem.net.util.ComStatus;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComGame extends ComStatus {
	public ComGame(Deminator d) {
		CommandMove cm = new CommandMove(d);

		super.newReceiveAction(cm);

		super.newSendAction(cm);
		super.newSendAction(new CommandExplose());
		super.newSendAction(new CommandDiscover());
		super.newSendAction(new CommandStun());
		super.newSendAction(new CommandPoints());
		super.newSendAction(new CommandEnd());
	}
}
