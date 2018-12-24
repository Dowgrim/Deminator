package fr.meusnnog.deminator.client.net.actions.receive;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class ReceiverPoints extends ClientDemReceiver {
	public ReceiverPoints(ClientFrame d) {
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
