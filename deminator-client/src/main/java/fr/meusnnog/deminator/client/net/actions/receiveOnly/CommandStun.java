package fr.meusnnog.deminator.client.net.actions.receiveOnly;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class CommandStun extends ClientDemReceiver {
	public CommandStun(ClientFrame d) {
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
