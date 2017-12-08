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
	public void ComBefore(Deminator d) {
		CommandNew cmdNew = new CommandNew(d);
		CommandReady cmdRdy = new CommandReady(d);
		newReceiveAction(cmdNew);
		newReceiveAction(new CommandQuit(d));
		newReceiveAction(cmdRdy);
		newReceiveAction(new CommandOption(d));
		newReceiveAction(new CommandLaunch(d));

		newSendAction(cmdNew);
		newSendAction(cmdRdy);
	}
}
