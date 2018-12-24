package fr.meusnnog.deminator.server.actions.receive;

import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.menu.ServerFrame;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.util.List;

public class ReceiverPong extends ServerDemReceiver {
	/**
	 * For DemReceiver
	 *
	 * @param p Player who has send Pong
	 */
	public ReceiverPong(ServerFrame sF, PlayerController p) {
		super(sF, p);
	}

	@Override
	public void receive(List<String> params) {
		long currDate = System.currentTimeMillis();
		long ms = currDate - Long.parseLong(params.remove(0));
		System.out.println("Ping delay of " + playerController.getPlayer().getNick() + ": " + ms + "ms");
	}

	@Override
	public String getCommandName() {
		return "PONG";
	}
}
