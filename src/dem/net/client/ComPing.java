package dem.net.client;

import dem.net.client.actions.CommandPing;
import dem.net.util.ComStatus;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComPing extends ComStatus {
	public ComPing(Deminator d) {
		CommandPing ping = new CommandPing();
		this.newSendAction(ping);
		this.newReceiveAction(new CommandPing.CommandPong(d, ping));
	}
}
