package dem.net.util;

import dem.net.client.actions.CommandPing;

public class ComPing extends Communicator {
	public ComPing() {
		CommandPing ping = new CommandPing();
		this.newSendAction("PING", ping);
		this.newReceiveAction("PONG", new CommandPing.Pong(ping));
		this.newReceiveAction("PING", ping);
	}
}
