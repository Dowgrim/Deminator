package fr.meusnnog.deminator.server.actions.sendOnly;

import fr.meusnnog.deminator.net.DemEmitter;

public class CommandStun implements DemEmitter {
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
