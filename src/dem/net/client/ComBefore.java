package dem.net.client;

import dem.net.client.actions.sendAndRecieve.CommandNew;
import dem.net.client.actions.sendAndRecieve.CommandReady;
import dem.net.client.actions.receiveOnly.CommandLaunch;
import dem.net.client.actions.receiveOnly.CommandOption;
import dem.net.client.actions.receiveOnly.CommandQuit;
import dem.net.util.Communicator;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

public class ComBefore extends Communicator {
	public ComBefore(Deminator d) {

		CommandNew cmdNew = new CommandNew(d);
		CommandReady cmdRdy = new CommandReady(d);
		super.newReceiveAction("NEW", cmdNew);
		super.newReceiveAction("QUIT", new CommandQuit(d));
		super.newReceiveAction("RDY", cmdRdy);
		super.newReceiveAction("OPT", new CommandOption(d));
		super.newReceiveAction("LDD", new CommandLaunch(d));

		super.newSendAction("NEW", cmdNew);
		super.newSendAction("RDY", cmdRdy);
	}
}
