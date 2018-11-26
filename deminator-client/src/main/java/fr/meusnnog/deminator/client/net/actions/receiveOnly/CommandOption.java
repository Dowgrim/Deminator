package fr.meusnnog.deminator.client.net.actions.receiveOnly;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.util.List;

public class CommandOption extends ClientDemReceiver {
	public CommandOption(ClientFrame cF) {
		super(cF);
	}

	@Override
	public void receive(List<String> params) {
		// OPT option value
		String opt = params.remove(0);
		String val = params.remove(0);

		// TODO
	}

	@Override
	public String getCommandName() {
		return "OPT";
	}
}
