package dem.server.net;

import dem.server.model.Grid;
import dem.server.model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public class OldServerDem extends ServerSocket implements Runnable {
	private Grid grid;
	private Map<String, Player> players;
	private final Object syncGrid = new Object();
	private dem.server.ServerDem jspPL;

	public OldServerDem(int port, dem.server.ServerDem jsp) throws IOException {
		super(port);
		jspPL = jsp;
		players = new HashMap<>();
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			try {
				this.accept(); // TODO something with that
				players.put(i + "", new Player(this));
			} catch(IOException e) {/*dem.MainClient*/}
		}
	}

	private void closeSocketServeur() {
		try {
			this.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void laucher() {
		grid = new Grid(4, 4, this);
	}

	public void modPlayer(String preNick, String nick, String col) {
		Player p = players.get(preNick);
		p.setNick(nick);
		p.setColor(Integer.parseInt(col));
		players.put(nick, p);
	}

	public void movable(String nick, int x, int y) {
		synchronized(syncGrid) {
			for(Player p : players.values()) {
				if(p.isOn(x, y)) {
					return;
				}
			}
			for(Player p : players.values()) {
				p.sendMove(nick, x, y);
			}
		}
	}

	public void explosion(String nick, int x, int y) {
		grid.discover(x, y);

		if(grid.isBomb(x, y)) {
			for(Player p : players.values()) {
				for(int i = -1; i <= 1; i++) {
					for(int j = -1; j <= 1; j++) {
						if(p.isOn((x + i) % grid.size(), (y + j) % grid.size())) {
							//TODO
						}
					}
				}
				p.sendExplo(x, y);
			}
		}
	}

	public void discover(int x, int y) {
		for(Player p : players.values()) {
			p.sendDisco(x, y, grid.get(x, y));
		}
	}
}
