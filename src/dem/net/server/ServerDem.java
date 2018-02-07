package dem.net.server;

import dem.model.Grid;
import dem.model.Player;
import dem.view.menu.JSPPlayersList;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

/**
 * Created by Michael on 29/04/2016.
 */
public class ServerDem extends ServerSocket implements  Runnable{
    private Grid grid;
    private HashMap<String, Player> players;
    private Object syncGrid;
    private JSPPlayersList jspPL;

    public ServerDem(int port, JSPPlayersList jsp) throws IOException {
        super(port);
        jspPL = jsp;
        syncGrid = new Object();
        players = new HashMap<>();
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            try {
                players.put(i+"", new Player(this, this.accept()));
            }catch (IOException e){/*dem.MainClient*/}
        }
    }

    private void closeSocketServeur() {
        try {
            this.close();
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

    public void explosion(String nick, int x, int y) {
        grid.discover(x, y);

        if(grid.isBomb(x, y)){
            for(Player p : players.values()){
                for(int i = -1; i <= 1; i++){
                    for(int j = -1; j <= 1; j++){
                        if(p.isOn((x+i)%grid.getSize(), (y+j)%grid.getSize())){
                            //TODO
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





}
