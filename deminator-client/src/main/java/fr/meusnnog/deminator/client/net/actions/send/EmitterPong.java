package fr.meusnnog.deminator.client.net.actions.send;

import fr.meusnnog.deminator.net.DemEmitter;

public class EmitterPong implements DemEmitter {
	private final String pingTime;

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
