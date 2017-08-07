package net.server;

import server.Grid;
import server.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

/**
 * Created by Michael on 29/04/2016.
 */
public class ServerDem {
    private ServerSocket server;
    private Grid grid;
    private HashMap<String, Player> players;
    private Object syncGrid;



    private void waitingPlayer() {
        for(int i = 0; i < 10; i++) {
            try {
                players.put(i+"", new Player(this, server.accept()));
            }catch (IOException e){/*Yolo*/}
        }
    }

    private void closeSocketServeur() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void laucher(){
        grid = new Grid(4, 4, this);
    }

    public void modPlayer(String preNick, String nick, String col) {
        Player p = players.get(preNick);
        p.setNick(nick);
        p.setColor(col);
        players.put(nick, p);
    }

    public void movable(String nick, int x, int y) {
        synchronized (syncGrid){
            for(Player p : players.values()){
                if(p.isOn(x, y)){
                    return;
                }
            }
            for(Player p : players.values()){
                p.sendMove(nick, x, y);
            }
        }
    }

    public void stunBroadcast(String nick, int i) {
        for(Player p : players.values()){
            p.sendStun(nick, i);
        }
    }

    public void explosion(String nick, int x, int y) {
        grid.discover(x, y);

        if(grid.isBomb(x, y)){
            for(Player p : players.values()){
                for(int i = -1; i <= 1; i++){
                    for(int j = -1; j <= 1; j++){
                        if(p.isOn((x+i)%grid.getSize(), (y+j)%grid.getSize())){
                            p.stun();
                        }
                    }
                }
                p.sendExplo(x, y);
            }
        }
    }

    public void discover(int x, int y){
        for(Player p : players.values()){
            p.sendDisco(x, y, grid.get(x, y));
        }
    }

	public boolean start(int port) throws Exception {
		server = new ServerSocket(port);

		//waitingPlayer();
		return true;
	}
}
