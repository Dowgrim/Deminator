package fr.meusnnog.deminator.server.actions.sendAndRecieve;

import fr.meusnnog.deminator.server.menu.ServerFrame;
import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.util.List;

public class CommandPing extends ServerDemReceiver implements DemEmitter {
	private PlayerController p;

	public CommandPing(ServerFrame sF, PlayerController p) {
		super(sF, p);
	}

	public CommandPing() {
		super(null, null);
	}

	@Override
	public void receive(List<String> params) {
		p.send(new CommandPong(params.remove(0)));
	}

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
