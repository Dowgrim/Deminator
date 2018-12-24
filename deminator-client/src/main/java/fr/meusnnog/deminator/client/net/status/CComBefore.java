package fr.meusnnog.deminator.client.net.status;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.actions.receive.*;
import fr.meusnnog.deminator.net.communication.ComStatus;

public class CComBefore extends ComStatus {

	public CComBefore(ClientFrame cF) {
		super();
		newReceiveAction(new ReceiverPing(cF));
		newReceiveAction(new ReceiverPong(cF));
		newReceiveAction(new ReceiverNew(cF));
		newReceiveAction(new ReceiverQuit(cF));
		newReceiveAction(new ReceiverReady(cF));
		newReceiveAction(new ReceiverOption(cF));
		newReceiveAction(new RecieverLaunch(cF));
	}
}
