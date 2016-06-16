package net;

import util.Controller;
import view.game.Deminator;

/**
 * Created by Nathaël N on 04/05/16.
 */
public class Client {
	private final Controller control;
	private String pseudo;
	private Deminator view;

	public Client(Controller control, Deminator v) {
		this.control = control;
		view = v;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public boolean connectTo(String ip, int port) {
		return false;
	}

	public void stopConnecting() {

	}
}
