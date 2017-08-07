package dem.net.util;

import dem.net.actions.Ping;

public class ComPing extends Communicator {
	public ComPing() {
		Ping ping = new Ping();
		this.newSendAction("PING", ping);
		this.newReceiveAction("PONG", new Ping.Pong(ping));
		this.newReceiveAction("PING", ping);
	}
}
