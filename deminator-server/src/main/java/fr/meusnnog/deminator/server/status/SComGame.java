package fr.meusnnog.deminator.server.status;

import fr.meusnnog.deminator.gameengine.model.Player;
import fr.meusnnog.deminator.net.communication.ComStatus;
import fr.meusnnog.deminator.server.actions.sendAndRecieve.CommandMove;
import fr.meusnnog.deminator.server.actions.sendAndRecieve.CommandPing;
import fr.meusnnog.deminator.server.actions.sendAndRecieve.CommandPong;
import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.menu.ServerFrame;

public class SComGame extends ComStatus {
	public SComGame(ServerFrame sF, PlayerController p) {

		super.newReceiveAction(new CommandMove(p));
		super.newReceiveAction(new CommandPing(sF, p));
		super.newReceiveAction(new CommandPong(sF, p));
	}
}
