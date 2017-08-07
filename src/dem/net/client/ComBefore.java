package dem.net.client;

import dem.net.client.actions.*;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComBefore extends Communicator {
	public ComBefore(Deminator d) {
		super.newReceiveAction("NEW", new CommandNew(d));
		super.newReceiveAction("QUIT", new CommandQuit(d));
		super.newReceiveAction("RDY", new CommandReady(d));
		super.newReceiveAction("OPT", new CommandOPT(d));
		super.newReceiveAction("LDD", new CommandLDD(d));
	}
}
