package dem.net.util;

import dem.net.client.actions.*;

public class CommBefore extends Communicator {
	public CommBefore() {
		super.newReceiveAction("NEW", new CommandNew());
		super.newReceiveAction("QUIT", new CommandQuit());
		super.newReceiveAction("PING", new CommandPing());
		super.newReceiveAction("RDY", new CommandReady());
		super.newReceiveAction("OPT", new CommandOPT());
		super.newReceiveAction("LDD", new CommandLDD());
	}
}
