package dem.client.net;

import dem.client.graphic.game.Deminator;
import dem.common.Communicator;
import dem.common.graphic.JSPPlayersList;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Nathaël N on 04/05/16.
 */
public class OldClientDem {
	private String pseudo;
	private Color color;
	private Communicator com = null;
	private boolean gameInProgres;
	private Deminator view;

	private JSPPlayersList jspPL;

	public OldClientDem(String host, int port, String jtfPseudoText, Color c, JSPPlayersList jspPL) {
		this.pseudo = jtfPseudoText;
		this.jspPL = jspPL;
		color = c;
		try {
			com = new Communicator(host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPseudoAndColor(String pseudo, Color color) {
		this.pseudo = pseudo;
		this.color = color;
	}
}
