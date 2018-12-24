package fr.meusnnog.deminator.client.net.actions.receive;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;
import fr.meusnnog.deminator.net.DemEmitter;

import java.util.List;

public class ReceiverReady extends ClientDemReceiver implements DemEmitter {
	private boolean isReady;

	public ReceiverReady(ClientFrame cF) {
		super(cF);
	}

	public ReceiverReady(boolean isReady) {
		super(null);
		this.isReady = isReady;
	}

	@Override
	public void receive(List<String> params) {
		// RDY playerName true/false
		String playerName = params.get(0);
		boolean isReady = Boolean.parseBoolean(params.get(1));

		// TODO

	}

	@Override
	public String getCommandName() {
		return "RDY";
	}

	@Override
	public String buildCommand() {
		return "RDY " + isReady;
	}
}
