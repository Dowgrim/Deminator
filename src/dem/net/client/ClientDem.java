package dem.net.client;

import java.awt.Color;
import java.io.IOException;

import dem.net.util.SockCom;
import dem.view.game.Deminator;
import dem.view.menu.JSPPlayersList;

/**
 * Created by Nathaël N on 04/05/16.
 */
public class ClientDem {
	private String pseudo;
	private Color color;
	private SockCom sock = null;
	private boolean gameInProgres;
	private Deminator view;

	private JSPPlayersList jspPL;

	public ClientDem(String host, int port, String jtfPseudoText) {
		this.pseudo = pseudo;
		try {
			sock = new SockCom(host, port);
			sock.setCommunicator(new ComPing(view)); // just for example (useless line since comPing is default value)
			jspPL.putPlayerToList(pseudo, color);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPseudoAndColor(String pseudo, Color color) {
		this.pseudo = pseudo;
		this.color = color;
	}
}
