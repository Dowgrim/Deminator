package fr.meusnnog.deminator.server.status;

import fr.meusnnog.deminator.net.communication.ComStatus;
import fr.meusnnog.deminator.server.actions.receive.ReceiverMove;
import fr.meusnnog.deminator.server.actions.receive.ReceiverPing;
import fr.meusnnog.deminator.server.actions.receive.ReceiverPong;
import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.menu.ServerFrame;

public class SComGame extends ComStatus {
	public SComGame(ServerFrame sF, PlayerController p) {

		super.newReceiveAction(new ReceiverMove(p));
		super.newReceiveAction(new ReceiverPing(sF, p));
		super.newReceiveAction(new ReceiverPong(sF, p));
	}
}
