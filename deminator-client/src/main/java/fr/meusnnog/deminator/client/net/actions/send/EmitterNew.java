package fr.meusnnog.deminator.client.net.actions.send;

import fr.meusnnog.deminator.net.DemEmitter;

public class EmitterNew implements DemEmitter {
	private String newName;
	private int newColor;

	public EmitterNew(String newName, int newColor) {
		this.newName = newName;
		this.newColor = newColor;
	}

	@Override
	public String getCommandName() {
		return "NEW";
	}

	@Override
	public String buildCommand() {
		return "NEW " + newName + " " + newColor;
	}
}
