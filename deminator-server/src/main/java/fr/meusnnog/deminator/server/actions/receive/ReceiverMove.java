package fr.meusnnog.deminator.server.actions.receive;

import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.util.List;

public class ReceiverMove extends ServerDemReceiver implements DemEmitter {

	public ReceiverMove(PlayerController p) {
		super(null, p);
	}

	@Override
	public void receive(List<String> params) {
		// MOV direction
		char direction = params.remove(0).trim().charAt(0); // 'N' 'S' 'E' 'W'

		int dirX = direction == 'E'?1:direction=='W'?-1:0;
		int dirY = direction == 'N' ? 1 : direction == 'S' ? -1 : 0;
		//TODO send
	}

	@Override
	public String getCommandName() {
		return "MOV";
	}

	@Override
	public String buildCommand() {
		return null;
	}
}
