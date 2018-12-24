package fr.meusnnog.deminator.server.net;

import fr.meusnnog.deminator.net.DemReceiver;
import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.menu.ServerFrame;

import javax.swing.*;

public abstract class ServerDemReceiver extends DemReceiver<ServerFrame> {
	protected PlayerController playerController;
	public ServerDemReceiver(ServerFrame f, PlayerController pC) {
		super(f);
		playerController = pC;
	}
}
