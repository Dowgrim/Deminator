package fr.meusnnog.deminator.client.net.status;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.actions.receive.ReceiverPing;
import fr.meusnnog.deminator.client.net.actions.receive.ReceiverPong;
import fr.meusnnog.deminator.client.net.actions.send.EmitterPing;
import fr.meusnnog.deminator.client.net.actions.send.EmitterPong;
import fr.meusnnog.deminator.net.communication.ComStatus;

public class CComTest extends ComStatus {
	public CComTest(ClientFrame frame) {
		super();
		this.newReceiveAction(new ReceiverPing(frame));
		this.newReceiveAction(new ReceiverPong(frame));
	}
}
