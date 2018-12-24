package fr.meusnnog.deminator.client.net.status;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.actions.receive.*;
import fr.meusnnog.deminator.client.net.actions.send.EmitterMove;
import fr.meusnnog.deminator.gameengine.model.Player;
import fr.meusnnog.deminator.net.communication.ComStatus;

public class CComGame extends ComStatus {
	public CComGame(Player p, ClientFrame cf) {
		super();
		super.newReceiveAction(new EmitterMove(p));
		super.newReceiveAction(new ReceiverExplode(cf));
		super.newReceiveAction(new ReceiverDiscover(cf));
		super.newReceiveAction(new ReceiverStun(cf));
		super.newReceiveAction(new ReceiverPoints(cf));
		super.newReceiveAction(new ReceiverEnd(cf));
	}
}
