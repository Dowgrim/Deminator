package fr.meusnnog.deminator.server;

import fr.meusnnog.deminator.gameengine.model.Grid;
import fr.meusnnog.deminator.server.controllers.PlayerController;
import fr.meusnnog.deminator.server.menu.ServerFrame;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public class ServerDem extends ServerSocket implements Runnable {
    private Grid grid;
    private Map<String, PlayerController> players;
    private final Object syncGrid = new Object();
    private ServerFrame serverFrame;

    public ServerDem(int port, ServerFrame sF) throws IOException {
        super(port);
        serverFrame = sF;
        players = new HashMap<>();
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("Accepting a player...");
                players.put(Integer.toString(i), new PlayerController(this, this.accept(), serverFrame));
                System.out.println("Got a new player: " + i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		System.out.println("ServerDem no more running.");
	}

	public void closeSocketServeur() {
		try {
			this.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void laucher() {
		grid = new Grid(4, 4);
	}

	public void modPlayer(String preNick, String nick, String col) {
		PlayerController pC = players.get(preNick);
		pC.getPlayer().setNick(nick);
		pC.getPlayer().setColor(Integer.parseInt(col));
		players.put(nick, pC);
	}

	public void movable(String nick, int x, int y) {
		/*synchronized (syncGrid) {
			for (Player p : players.values()) {
				if (p.isOn(x, y)) {
					return;
				}
			}
			for (Player p : players.values()) {
				p.sendMove(nick, x, y);
			}
		}*/
	}

	public void explosion(String nick, int x, int y) {
		/*grid.discover(x, y);

		if (grid.isBomb(x, y)) {
			for (Player p : players.values()) {
				for (int i = 0; i < 9; i++) {
					if (p.isOn((x + i % 3) % grid.size(), (y + i / 3) % grid.size())) {
						//TODO
					}
				}
				p.sendExplo(x, y);
			}
		}*/
	}

	public void discover(int x, int y) {
		/*for (Player p : players.values()) {
			p.sendDisco(x, y, grid.get(x, y));
		}*/
	}

    public void sendBroadcast(String cmd) {
        for(PlayerController pC : players.values()) {
            pC.getCom().send(cmd);
        }
    }

}
