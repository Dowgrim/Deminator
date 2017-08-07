package dem.net.server.actions;

import dem.net.server.actions.CommandPing;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComPing extends Communicator {
	public ComPing(Deminator d) {
		CommandPing.CommandPong pong = new CommandPing.CommandPong();
		this.newReceiveAction("PING", new CommandPing(d, pong));
		this.newSendAction("PONG", pong);
	}
}
