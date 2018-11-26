package fr.meusnnog.deminator.client.net.actions.receiveOnly;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class CommandPoints extends ClientDemReceiver {
	public CommandPoints(ClientFrame d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// PTS playerName nbPts
		String playerName = params.remove(0);
		int pts = Integer.parseInt(params.remove(0));

		// TODO
	}

	@Override
	public String getCommandName() {
		return "PTS";
	}
}
