package dem.net.client;

import java.awt.Color;
import java.io.IOException;

import dem.net.client.actions.sendAndRecieve.CommandPing;
import dem.net.client.status.ComPing;
import dem.net.util.Communicator;
import dem.view.game.Deminator;
import dem.view.menu.JSPPlayersList;

/**
 * Created by Nathaël N on 04/05/16.
 */
public class ClientDem {
	private String pseudo;
	private Color color;
	private Communicator com = null;
	private boolean gameInProgres;
	private Deminator view;

	private JSPPlayersList jspPL;

	public ClientDem(String host, int port, String jtfPseudoText, Color c, JSPPlayersList jspPL) {
		this.pseudo = jtfPseudoText;
		this.jspPL = jspPL;
		color = c;
		try {
			com = new Communicator(host, port, new ComPing());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPseudoAndColor(String pseudo, Color color) {
		this.pseudo = pseudo;
		this.color = color;
	}
}
