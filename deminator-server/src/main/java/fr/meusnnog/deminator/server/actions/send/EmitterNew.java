package fr.meusnnog.deminator.server.actions.send;

import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.menu.ServerFrame;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;

import java.awt.*;
import java.util.List;

public class EmitterNew implements DemEmitter {
	private final String oldName;
	private final String newName;
	private final int newColor;

	public EmitterNew(String oldName, String newName, int newColor) {
		this.oldName = oldName;
		this.newName = newName;
		this.newColor = newColor;
	}

	@Override
	public String getCommandName() {
		return "NEW";
	}

	@Override
	public boolean isBroadcast() {
		return true;
	}

	@Override
	public String buildCommand() {
		// NEW name hue
		if(oldName == null) {
			return "NEW " + newName + " " + newColor;
		}

		return "NEW " + newName + " " + newColor + " " + oldName;
	}
}
