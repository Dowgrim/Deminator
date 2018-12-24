package fr.meusnnog.deminator.gameengine.model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public class Player {

	static private final Map<String, int[]> mapDir = new HashMap<>();

	static {
		mapDir.put("N", new int[]{0, 1});
		mapDir.put("S", new int[]{0, -1});
		mapDir.put("E", new int[]{-1, 0});
		mapDir.put("W", new int[]{1, 0});
	}


	private boolean ready = false;

	private int posX, posY;

	private String nick = "Unnamed";
	private int color; // 0 - 360

	private int points;

	private int shield;

	public Player(){
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setColor(int color) {
		this.color = color % 360;
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
		if (ready) {
			int[] xy = mapDir.get(dir);
			if (xy == null) {
				throw new RuntimeException(dir + " is not a valid move");
			}
			//server.movable(nick, xy[0], xy[1]);
		}
	}

	public void sendExplo(int x, int y) {
		//TODO
	}

	public void sendDisco(int x, int y, int i) {
		// TODO
	}



	public int getColorInt() {
		return color;
	}

	public Color getColor() {
		return new Color(Color.HSBtoRGB(color / 360, 1, 1));
	}

	public boolean toggleReady() {
		return ready = !ready;
	}
}
