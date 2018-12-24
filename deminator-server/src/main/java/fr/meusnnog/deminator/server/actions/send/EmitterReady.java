package fr.meusnnog.deminator.server.actions.send;

import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.util.List;

public class EmitterReady implements DemEmitter {

	private final String playerName;
	private final boolean b;

	public EmitterReady(String playerName, boolean b) {
		this.playerName = playerName;
		this.b = b;
	}

	@Override
	public String getCommandName() {
		return "RDY";
	}

	@Override
	public String buildCommand() {
		// RDY playerName true/false
		return "RDY " + playerName + b; // MOCK
	}

	@Override
	public boolean isBroadcast() {
		return true;
	}
}
