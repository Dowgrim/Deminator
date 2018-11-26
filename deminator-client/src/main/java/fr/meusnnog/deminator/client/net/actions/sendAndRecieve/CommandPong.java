package fr.meusnnog.deminator.client.net.actions.sendAndRecieve;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;
import fr.meusnnog.deminator.net.DemEmitter;

import java.util.List;

public class CommandPong extends ClientDemReceiver implements DemEmitter {
	private String ms;

	public CommandPong(ClientFrame f) {
		super(f);
	}

	public CommandPong(String ms) {
		super(null);
		this.ms = ms;
	}

	@Override
	public String getCommandName() {
		return "PONG";
	}

	@Override
	public String buildCommand() {
		return "PONG " + ms;
	}

	@Override
	public void receive(List<String> params) {
		long ms = System.currentTimeMillis() - Long.parseLong(params.remove(0));

		System.out.println("Server latency: " + ms + "ms");
		((ClientFrame)frame).receivePong(ms);
	}
}
