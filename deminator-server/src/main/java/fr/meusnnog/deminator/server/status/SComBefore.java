package fr.meusnnog.deminator.server.status;

import fr.meusnnog.deminator.server.menu.ServerFrame;
import fr.meusnnog.deminator.net.communication.ComStatus;
import fr.meusnnog.deminator.server.actions.sendAndRecieve.CommandNew;
import fr.meusnnog.deminator.server.actions.sendAndRecieve.CommandPing;
import fr.meusnnog.deminator.server.actions.sendAndRecieve.CommandPong;
import fr.meusnnog.deminator.server.actions.sendAndRecieve.CommandReady;
import fr.meusnnog.deminator.server.controllers.PlayerController;

public class SComBefore extends ComStatus {
	public SComBefore(PlayerController p, ServerFrame sF) {
		newReceiveAction(new CommandPing(sF, p));
		newReceiveAction(new CommandPong(sF, p));
		newReceiveAction(new CommandNew(sF, p));
		newReceiveAction(new CommandReady());
	}
}
