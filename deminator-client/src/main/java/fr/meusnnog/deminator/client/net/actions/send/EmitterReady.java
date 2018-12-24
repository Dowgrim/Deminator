package fr.meusnnog.deminator.client.net.actions.send;

import fr.meusnnog.deminator.net.DemEmitter;

public class EmitterReady implements DemEmitter {
	@Override
	public String getCommandName() {
		return "RDY";
	}

	@Override
	public String buildCommand() {
		return "RDY";
	}
}
