package fr.meusnnog.deminator.server.status;

import fr.meusnnog.deminator.server.menu.ServerFrame;
import fr.meusnnog.deminator.net.communication.ComStatus;
import fr.meusnnog.deminator.server.actions.receive.ReceiveNew;
import fr.meusnnog.deminator.server.actions.receive.ReceiverPing;
import fr.meusnnog.deminator.server.actions.receive.ReceiverPong;
import fr.meusnnog.deminator.server.actions.receive.ReceiverReady;
import fr.meusnnog.deminator.server.controllers.PlayerController;

public class SComBefore extends ComStatus {
	public SComBefore(PlayerController p, ServerFrame sF) {
		newReceiveAction(new ReceiverPing(sF, p));
		newReceiveAction(new ReceiverPong(sF, p));
		newReceiveAction(new ReceiveNew(sF, p));
		newReceiveAction(new ReceiverReady());
	}
}
