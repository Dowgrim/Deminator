package fr.meusnnog.deminator.client.net.actions.receive;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class ReceiverQuit extends ClientDemReceiver {
	public ReceiverQuit(ClientFrame cF) {
		super(cF);
	}

	@Override
	public void receive(List<String> params) {
		// QUIT playerName
		String playerName = params.remove(0);

		// TODO
	}

	@Override
	public String getCommandName() {
		return "QUT";
	}
}
