package dem.net.client;

import dem.net.client.actions.CommandPing;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComPing extends Communicator {
	public ComPing(Deminator d) {
		CommandPing ping = new CommandPing();
		this.newSendAction("PING", ping);
		this.newReceiveAction("PONG", new CommandPing.CommandPong(d, ping));
	}
}
