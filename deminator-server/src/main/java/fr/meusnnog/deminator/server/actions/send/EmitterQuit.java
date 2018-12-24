package fr.meusnnog.deminator.server.actions.send;

import fr.meusnnog.deminator.net.DemEmitter;

public class EmitterQuit implements DemEmitter {
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
