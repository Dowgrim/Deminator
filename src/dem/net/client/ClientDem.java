package dem.net.client;

import java.awt.Color;
import java.io.IOException;

import dem.net.client.actions.sendAndRecieve.CommandNew;
import dem.net.util.Communicator;
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

	public ClientDem(String host, int port, String jtfPseudoText, Color c, JSPPlayersList jspPL, Communicator com) {
		this.pseudo = jtfPseudoText;
		this.jspPL = jspPL;
		color = c;
		try {
			sock = new SockCom(host, port);
			sock.setCommunicator(com);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sock.send(new CommandNew());
	}

	public void setPseudoAndColor(String pseudo, Color color) {
		this.pseudo = pseudo;
		this.color = color;
	}
}
