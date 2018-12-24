package fr.meusnnog.deminator.server.actions.send;

import fr.meusnnog.deminator.net.DemEmitter;

public class EmitterStun implements DemEmitter {
	@Override
	public String getCommandName() {
		return "STUN";
	}

	@Override
	public String buildCommand() {
		// STUN playerName time

		// TODO
		return "";
	}
}
