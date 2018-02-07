package dem.net.client.status;

import dem.net.client.actions.sendAndRecieve.CommandPing;
import dem.net.util.ComStatus;

public class ComPing extends ComStatus {
	public ComPing() {
		CommandPing ping = new CommandPing();
		this.newSendAction(ping);
		this.newReceiveAction(new CommandPing.CommandPong(ping));
	}
}
