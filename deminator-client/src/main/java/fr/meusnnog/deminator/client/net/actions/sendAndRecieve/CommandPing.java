package fr.meusnnog.deminator.client.net.actions.sendAndRecieve;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;
import fr.meusnnog.deminator.net.DemEmitter;

import java.util.List;

public class CommandPing extends ClientDemReceiver implements DemEmitter {

	public CommandPing(ClientFrame f) {
		super(f);
	}

	public CommandPing() {
		super(null);
	}

	@Override
	public String getCommandName() {
		return "PING";
	}

	@Override
	public String buildCommand() {
		long pingDate = System.currentTimeMillis();

		return "PING " + pingDate;
	}

	@Override
	public void receive(List<String> params) {
		((ClientFrame)frame).receivePing(params.remove(0));
	}
}
