package fr.meusnnog.deminator.server.actions.receive;

import fr.meusnnog.deminator.server.actions.send.EmitterPong;
import fr.meusnnog.deminator.server.menu.ServerFrame;
import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.util.List;

public class ReceiverPing extends ServerDemReceiver {
	public ReceiverPing(ServerFrame sF, PlayerController p) {
		super(sF, p);
	}

	@Override
	public void receive(List<String> params) {
		this.playerController.send(new EmitterPong(params.remove(0)));
	}

	@Override
	public String getCommandName() {
		return "PING";
	}
}
