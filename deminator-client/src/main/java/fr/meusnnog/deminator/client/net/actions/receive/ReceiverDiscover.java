package fr.meusnnog.deminator.client.net.actions.receive;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class ReceiverDiscover extends ClientDemReceiver {
	public ReceiverDiscover(ClientFrame d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// DIS x y value
		int x = Integer.parseInt(params.remove(0));
		int y = Integer.parseInt(params.remove(0));
		int value = Integer.parseInt(params.remove(0));

		// TODO
	}

	@Override
	public String getCommandName() {
		return "DIS";
	}
}
