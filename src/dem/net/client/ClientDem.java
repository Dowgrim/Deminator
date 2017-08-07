package dem.net.client;

import java.awt.Color;
import java.io.IOException;

import dem.net.util.ComPing;
import dem.net.util.SockCom;
import dem.view.game.Deminator;

/**
 * Created by Nathaël N on 04/05/16.
 */
public class ClientDem {
	private String pseudo;
	private Color color;
	private SockCom sock = null;
	private boolean gameInProgres;
	private Deminator view;

	public ClientDem() {}

	public void start(String host, int port, String pseudo) {
		this.pseudo = pseudo;
		try {
			sock = new SockCom(host, port);
			sock.setCommunicator(new ComPing()); // just for example (useless line since comPing is default value)
		} catch (IOException e) {
			e.printStackTrace();
		}
		view = new Deminator(this, 4, 4);
	}

	public void setPseudoAndColor(String pseudo, Color color) {
		this.pseudo = pseudo;
		this.color = color;
	}
}
