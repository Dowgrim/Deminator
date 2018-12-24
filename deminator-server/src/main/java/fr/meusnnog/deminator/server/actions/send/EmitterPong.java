package fr.meusnnog.deminator.server.actions.send;

import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.menu.ServerFrame;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.util.List;

public class EmitterPong implements DemEmitter {
	private final String pingTime;

	/**
	 * For DemEmitter
	 *
	 * @param pingTime Time when the ping was sent to throw it to the client
	 */
	public EmitterPong(String pingTime) {
		this.pingTime = pingTime;
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
