package fr.meusnnog.deminator.client.net.status;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.actions.sendAndRecieve.CommandPing;
import fr.meusnnog.deminator.client.net.actions.sendAndRecieve.CommandPong;
import fr.meusnnog.deminator.net.communication.ComStatus;

public class CComTest extends ComStatus {
	public CComTest(ClientFrame frame) {
		super();
		this.newReceiveAction(new CommandPing(frame));
		this.newReceiveAction(new CommandPong(frame));
	}
}
