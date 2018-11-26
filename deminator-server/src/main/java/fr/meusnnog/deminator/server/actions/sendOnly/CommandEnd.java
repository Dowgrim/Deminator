package fr.meusnnog.deminator.server.actions.sendOnly;

import fr.meusnnog.deminator.net.DemEmitter;

public class CommandEnd implements DemEmitter {
	@Override
	public String getCommandName() {
		return null;
	}

	@Override
	public String buildCommand() {
		return "";
	}
}
