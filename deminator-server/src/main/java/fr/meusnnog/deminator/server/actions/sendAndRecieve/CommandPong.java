package fr.meusnnog.deminator.server.actions.sendAndRecieve;

import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.menu.ServerFrame;
import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.util.List;

public class CommandPong extends ServerDemReceiver implements DemEmitter {
	private final String pingTime;

	/**
	 * For DemReceiver
	 *
	 * @param p Player who has send Pong
	 */
	public CommandPong(ServerFrame sF, PlayerController p) {
		super(sF, p);
		this.pingTime = null;
	}

	/**
	 * For DemEmitter
	 *
	 * @param pingTime Time when the ping was sent to throw it to the client
	 */
	public CommandPong(String pingTime) {
		super(null, null);
		this.pingTime = pingTime;
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

	@Override
	public String buildCommand() {
		return "PONG " + pingTime;
	}
}
