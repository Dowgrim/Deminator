package fr.meusnnog.deminator.server.actions.receive;

import fr.meusnnog.deminator.server.actions.send.EmitterReady;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.util.List;

public class ReceiverReady extends ServerDemReceiver {

	public ReceiverReady() {
		super(null, null);
	}

	@Override
	public void receive(List<String> params) {
		this.playerController.send(new EmitterReady(
				this.playerController.getPlayer().getNick(),
				this.playerController.getPlayer().toggleReady()));
	}

	@Override
	public String getCommandName() {
		return "RDY";
	}
}
