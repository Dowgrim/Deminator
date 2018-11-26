package fr.meusnnog.deminator.server.net;

import fr.meusnnog.deminator.net.DemReceiver;
import fr.meusnnog.deminator.server.controllers.PlayerController;

import javax.swing.*;

public abstract class ServerDemReceiver extends DemReceiver {
	protected PlayerController playerController;
	public ServerDemReceiver(JFrame f, PlayerController pC) {
		super(f);
		playerController = pC;
	}
}
