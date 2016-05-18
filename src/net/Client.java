package net;

import util.Controller;

/**
 * Created by Nathaël N on 04/05/16.
 */
public class Client {
	private final Controller control;
	private String pseudo;

	public Client(Controller control) {
		this.control = control;

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
