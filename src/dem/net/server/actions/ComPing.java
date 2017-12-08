package dem.net.server.actions;

import dem.net.server.actions.CommandPing;
import dem.net.util.ComStatus;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComPing extends ComStatus {
	public ComPing(Deminator d) {
		CommandPing.CommandPong pong = new CommandPing.CommandPong();
		this.newReceiveAction(new CommandPing(d, pong));
		this.newSendAction(pong);
	}
}
