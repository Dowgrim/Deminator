package fr.meusnnog.deminator.server.actions.send;

import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.util.List;

public class EmitterReady implements DemEmitter {
	@Override
	public String getCommandName() {
		return "RDY";
	}

	@Override
	public String buildCommand() {
		// RDY playerName true/false

		// TODO
		return null;
	}

	@Override
	public boolean isBroadcast() {
		return true;
	}
}
