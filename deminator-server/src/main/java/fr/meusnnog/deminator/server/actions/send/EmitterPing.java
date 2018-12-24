package fr.meusnnog.deminator.server.actions.send;

import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.menu.ServerFrame;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.util.List;

public class EmitterPing implements DemEmitter {
	@Override
	public String getCommandName() {
		return "PING";
	}

	@Override
	public String buildCommand() {
		return "PING " + System.currentTimeMillis();
	}

	@Override
	public boolean isBroadcast() {
		return true;
	}
}
