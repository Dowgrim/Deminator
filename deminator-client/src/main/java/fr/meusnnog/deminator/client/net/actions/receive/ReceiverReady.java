package fr.meusnnog.deminator.client.net.actions.receive;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;
import fr.meusnnog.deminator.net.DemEmitter;

import java.util.List;

public class ReceiverReady extends ClientDemReceiver {
	public ReceiverReady(ClientFrame cF) {
		super(cF);
	}

	@Override
	public void receive(List<String> params) {
		// RDY playerName true/false
		String playerName = params.get(0);
		boolean isReady = Boolean.parseBoolean(params.get(1));
		frame.setReady(playerName, isReady);

	}

	@Override
	public String getCommandName() {
		return "RDY";
	}
}
