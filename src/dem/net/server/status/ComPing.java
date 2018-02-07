package dem.net.server.status;

import dem.model.Player;
import dem.net.server.actions.sendAndRecieve.CommandPing;
import dem.net.util.ComStatus;

public class ComPing extends ComStatus {
	Player p;

	public ComPing(Player p) {
		this.p = p;
		CommandPing.CommandPong pong = new CommandPing.CommandPong();
		this.newReceiveAction(new CommandPing(p));
		this.newSendAction(pong);
	}
}
