package dem.net.server.actions;

import dem.net.server.actions.sendAndRecieve.CommandNew;
import dem.net.server.actions.sendAndRecieve.CommandReady;
import dem.net.server.actions.sendOnly.CommandLaunch;
import dem.net.server.actions.sendOnly.CommandOption;
import dem.net.server.actions.sendOnly.CommandQuit;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComBefore extends Communicator {
	public ComBefore(Deminator d) {

		CommandNew cmdNew = new CommandNew(d);
		CommandReady cmdRdy = new CommandReady(d);
		super.newReceiveAction("NEW", cmdNew);
		super.newReceiveAction("RDY", cmdRdy);

		super.newSendAction("NEW", cmdNew);
		super.newSendAction("RDY", cmdRdy);
		super.newSendAction("QUIT", new CommandQuit());
		super.newSendAction("OPT", new CommandOption());
		super.newSendAction("LDD", new CommandLaunch());
	}
}
