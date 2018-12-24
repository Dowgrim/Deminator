package fr.meusnnog.deminator.client.net.actions.receive;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class ReceiverEnd extends ClientDemReceiver {
	public ReceiverEnd(ClientFrame d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {

	}

	@Override
	public String getCommandName() {
		return "END";
	}
}
