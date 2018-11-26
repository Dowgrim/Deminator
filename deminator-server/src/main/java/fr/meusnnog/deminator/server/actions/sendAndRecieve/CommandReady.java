package fr.meusnnog.deminator.server.actions.sendAndRecieve;

import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.util.List;

public class CommandReady extends ServerDemReceiver implements DemEmitter {

	private boolean isReady;

	public CommandReady() {
		super(null, null);
	}

	public CommandReady(boolean isReady) {
		super(null, null);
		this.isReady = isReady;
	}

	@Override
	public void receive(List<String> params) {
		// RDY true/false
		boolean isReady = Boolean.parseBoolean(params.remove(0));

		// TODO
	}

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
