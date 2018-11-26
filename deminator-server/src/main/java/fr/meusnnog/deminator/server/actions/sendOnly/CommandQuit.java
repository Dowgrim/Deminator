package fr.meusnnog.deminator.server.actions.sendOnly;

import fr.meusnnog.deminator.net.DemEmitter;

public class CommandQuit implements DemEmitter {
	@Override
	public String getCommandName() {
		return null;
	}

	@Override
	public String buildCommand() {
		// QUIT playerName
		return "";
	}
}
