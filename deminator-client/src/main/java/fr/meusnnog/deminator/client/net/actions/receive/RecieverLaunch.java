package fr.meusnnog.deminator.client.net.actions.receive;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class RecieverLaunch extends ClientDemReceiver {
	public RecieverLaunch(ClientFrame cF) {
		super(cF);
	}

	@Override
	public void receive(List<String> params) {
		// LDD name x y shields
		String playerName = params.remove(0);
		int x = Integer.parseInt(params.remove(0));
		int y = Integer.parseInt(params.remove(0));
		int shields = Integer.parseInt(params.remove(0));

		// TODO
	}

	@Override
	public String getCommandName() {
		return "LDD";
	}
}
