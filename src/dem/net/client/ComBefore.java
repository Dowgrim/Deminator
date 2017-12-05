package dem.net.client;

import dem.net.client.actions.sendAndRecieve.CommandNew;
import dem.net.client.actions.sendAndRecieve.CommandReady;
import dem.net.client.actions.receiveOnly.CommandLaunch;
import dem.net.client.actions.receiveOnly.CommandOption;
import dem.net.client.actions.receiveOnly.CommandQuit;
import dem.net.util.ComStatus;
import dem.net.util.Communicator;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

public abstract class ComBefore extends ComStatus {
	public void setStatus(Communicator c) {

		CommandNew cmdNew = new CommandNew(d);
		CommandReady cmdRdy = new CommandReady(d);
		c.newReceiveAction("NEW", cmdNew);
		c.newReceiveAction("QUIT", new CommandQuit(d));
		c.newReceiveAction("RDY", cmdRdy);
		c.newReceiveAction("OPT", new CommandOption(d));
		c.newReceiveAction("LDD", new CommandLaunch(d));

		c.newSendAction(cmdNew);
		c.newSendAction(cmdRdy);
	}
}
