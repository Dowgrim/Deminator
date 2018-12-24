package fr.meusnnog.deminator.client.net.actions.receive;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;
import fr.meusnnog.deminator.net.DemEmitter;

import java.util.List;

public class ReceiverPong extends ClientDemReceiver {

	public ReceiverPong(ClientFrame f) {
		super(f);
	}

	@Override
	public String getCommandName() {
		return "PONG";
	}

	@Override
	public void receive(List<String> params) {
		long ms = System.currentTimeMillis() - Long.parseLong(params.remove(0));

		System.out.println("Server latency: " + ms + "ms");
		frame.receivePong(ms);
	}
}
