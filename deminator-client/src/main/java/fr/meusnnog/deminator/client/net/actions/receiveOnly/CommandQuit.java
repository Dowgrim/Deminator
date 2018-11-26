package fr.meusnnog.deminator.client.net.actions.receiveOnly;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class CommandQuit extends ClientDemReceiver {
	public CommandQuit(ClientFrame cF) {
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
