package fr.meusnnog.deminator.client.net.actions.send;

import fr.meusnnog.deminator.net.DemEmitter;

public class EmitterPing implements DemEmitter {
	@Override
	public String getCommandName() {
		return "PING";
	}

	@Override
	public String buildCommand() {
		long pingDate = System.currentTimeMillis();

		return "PING " + pingDate;
	}
}
