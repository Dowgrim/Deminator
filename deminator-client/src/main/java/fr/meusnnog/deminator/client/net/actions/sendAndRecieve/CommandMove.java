package fr.meusnnog.deminator.client.net.actions.sendAndRecieve;

import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;
import fr.meusnnog.deminator.gameengine.model.Player;
import fr.meusnnog.deminator.net.DemEmitter;

import java.util.List;

public class CommandMove extends ClientDemReceiver implements DemEmitter {
	private final Player p;
	private String mov; // N; S; E; W

	public CommandMove(Player p) {
		super(null);
		this.p = p;
	}

	public CommandMove(String mov) {
		super(null);
		this.p = null;
		this.mov = mov;
	}

	@Override
	public void receive(List<String> params) {
		// MOV name x y
		String playerName = params.remove(0);
		int x = Integer.parseInt(params.remove(0));
		int y = Integer.parseInt(params.remove(0));

		p.sendMove(playerName, x, y);
	}

	@Override
	public String getCommandName() {
		return "MOV";
	}

	@Override
	public String buildCommand() {
		return "MOV " + mov;
	}
}
