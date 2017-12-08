package dem.net.server.actions;

import dem.net.server.actions.sendAndRecieve.CommandNew;
import dem.net.server.actions.sendAndRecieve.CommandReady;
import dem.net.server.actions.sendOnly.CommandLaunch;
import dem.net.server.actions.sendOnly.CommandOption;
import dem.net.server.actions.sendOnly.CommandQuit;
import dem.net.util.ComStatus;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComBefore extends ComStatus {
	public ComBefore(Deminator d) {

		CommandNew cmdNew = new CommandNew(d);
		CommandReady cmdRdy = new CommandReady(d);
		super.newReceiveAction(cmdNew);
		super.newReceiveAction(cmdRdy);

		super.newSendAction(cmdNew);
		super.newSendAction(cmdRdy);
		super.newSendAction(new CommandQuit());
		super.newSendAction(new CommandOption());
		super.newSendAction(new CommandLaunch());
	}
}
