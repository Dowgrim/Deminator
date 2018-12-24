package fr.meusnnog.deminator.client.net.actions.receive;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class ReceiverStun extends ClientDemReceiver {
	public ReceiverStun(ClientFrame d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// STUN playerName time
		String playerName = params.remove(0);
		int time = Integer.parseInt(params.remove(0));

		// TODO
	}

	@Override
	public String getCommandName() {
		return "STN";
	}
}
