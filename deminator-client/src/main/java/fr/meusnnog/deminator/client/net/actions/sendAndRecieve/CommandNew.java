package fr.meusnnog.deminator.client.net.actions.sendAndRecieve;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;
import fr.meusnnog.deminator.net.DemEmitter;

import java.awt.*;
import java.util.List;

public class CommandNew extends ClientDemReceiver implements DemEmitter {

	private String nick;
	private int color;

	public CommandNew(ClientFrame f) {
		super(f);
	}

	public CommandNew(String text, int c) {
		super(null);
		this.nick = text;
		this.color = c;
	}

	@Override
	public String getCommandName() {
		return "NEW";
	}

	@Override
	public String buildCommand() {
		return "NEW " + nick + " " + color;
	}

	/**
	 * params 1 Pseudo
	 * params 2 Color (un seul int apparement)
	 */
	@Override
	public void receive(List<String> params) {
		((ClientFrame)frame).addPlayer(params.get(0), new Color(Color.HSBtoRGB((Float.parseFloat(params.get(1)) / 360), 1, 1)));
	}
}
