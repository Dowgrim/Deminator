package dem.model;


import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import dem.net.server.status.ComPing;
import dem.net.server.ServerDem;
import dem.net.util.Communicator;
import dem.net.util.actions.Emitter;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public class Player {

	static private final Map<String, int[]> mapDir = new HashMap<>();

	static {
		mapDir.put("N", new int[] {0, 1});
		mapDir.put("S", new int[] {0, -1});
		mapDir.put("E", new int[] {-1, 0});
		mapDir.put("W", new int[] {1, 0});
	}


	private ServerDem server;

	private boolean ready = false;

	private int posX, posY;

	private String nick;
	private Color color;

	private int points;

	private int shield;

	private Communicator com;

	// TODO: sale le socket ici
	public Player(ServerDem serv, Socket s) throws IOException {
		server = serv;
		com = new Communicator(s, new ComPing(this));
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setColor(int color) {
		this.color = new Color(color);
	}

	public boolean isOn(int x, int y) {
		return x == posX && y == posY;
	}

	public String getNick() {
		return nick;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public void sendMove(String nick, int x, int y) {
		// TODO Commande!!
	}

	// TODO: Move String parsing outside Player
	private void move(String dir) {
		if(ready) {
			int[] xy = mapDir.get(dir);
			if(xy == null) {
				throw new RuntimeException(dir + " is not a valid move");
			}
			server.movable(nick, xy[0], xy[1]);
		}
	}

	public void sendExplo(int x, int y) {
		//TODO
	}

	public void sendDisco(int x, int y, int i) {
		// TODO
	}

	public void send(Emitter e) {
		com.send(e);
	}
}
