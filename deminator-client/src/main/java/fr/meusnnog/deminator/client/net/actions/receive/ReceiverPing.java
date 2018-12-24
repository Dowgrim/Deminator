package fr.meusnnog.deminator.client.net.actions.receive;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class ReceiverPing extends ClientDemReceiver {

	public ReceiverPing(ClientFrame f) {
		super(f);
	}

	@Override
	public String getCommandName() {
		return "PING";
	}

	@Override
	public void receive(List<String> params) {
		frame.receivePing(params.remove(0));
	}
}
