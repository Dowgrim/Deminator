package fr.meusnnog.deminator.client.net.status;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.actions.receiveOnly.*;
import fr.meusnnog.deminator.client.net.actions.sendAndRecieve.CommandMove;
import fr.meusnnog.deminator.gameengine.model.Player;
import fr.meusnnog.deminator.net.communication.ComStatus;

public class CComGame extends ComStatus {
	public CComGame(Player p, ClientFrame cf) {
		super();
		super.newReceiveAction(new CommandMove(p));
		super.newReceiveAction(new CommandExplode(cf));
		super.newReceiveAction(new CommandDiscover(cf));
		super.newReceiveAction(new CommandStun(cf));
		super.newReceiveAction(new CommandPoints(cf));
		super.newReceiveAction(new CommandEnd(cf));
	}
}
