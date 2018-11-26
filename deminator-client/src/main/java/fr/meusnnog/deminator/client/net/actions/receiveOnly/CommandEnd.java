package fr.meusnnog.deminator.client.net.actions.receiveOnly;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class CommandEnd extends ClientDemReceiver {
	public CommandEnd(ClientFrame d) {
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
