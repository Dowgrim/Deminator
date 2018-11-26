package fr.meusnnog.deminator.client.net.status;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.actions.receiveOnly.CommandLaunch;
import fr.meusnnog.deminator.client.net.actions.receiveOnly.CommandOption;
import fr.meusnnog.deminator.client.net.actions.receiveOnly.CommandQuit;
import fr.meusnnog.deminator.client.net.actions.sendAndRecieve.CommandNew;
import fr.meusnnog.deminator.client.net.actions.sendAndRecieve.CommandPing;
import fr.meusnnog.deminator.client.net.actions.sendAndRecieve.CommandPong;
import fr.meusnnog.deminator.client.net.actions.sendAndRecieve.CommandReady;
import fr.meusnnog.deminator.net.communication.ComStatus;

public class CComBefore extends ComStatus {

	public CComBefore(ClientFrame cF) {
		super();
		newReceiveAction(new CommandPing(cF));
		newReceiveAction(new CommandPong(cF));
		newReceiveAction(new CommandNew(cF));
		newReceiveAction(new CommandQuit(cF));
		newReceiveAction(new CommandReady(cF));
		newReceiveAction(new CommandOption(cF));
		newReceiveAction(new CommandLaunch(cF));
	}
}
