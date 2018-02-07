package dem.model;


import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import dem.net.server.status.ComPing;
import dem.net.server.ServerDem;
import dem.net.util.Communicator;
import dem.net.util.actions.Emitter;

/**
 * Created by Michael on 29/04/2016.
 */
public class Player {

    private ServerDem server;

    private boolean ready = false;

    private int posX, posY;

    private String nick;
    private Color color;

    private int points;

    private int shield;

    private Communicator com;

    public Player(ServerDem serv , Socket s){
        System.out.println("Création du joueur");
        server = serv;
        try {
            System.out.println("Ah");
            com = new Communicator(s, new ComPing(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Bh");
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setColor(String color) {
        this.color = new Color(Integer.parseInt(color));
    }

    public boolean isOn(int x, int y) {
        return (x == posX && y == posY);
    }

    public String getNick() {
        return nick;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void sendMove(String nick, int x, int y){
        // TODO Commande!!
    }

    private void move(String dir) {
        if(ready) {
            int x = 0, y = 0;
            switch (dir) {
                case "N": {
                    y = 1;
                    break;
                }
                case "E": {
                    x = 1;
                    break;
                }
                case "S": {
                    y = -1;
                    break;
                }
                case "W": {
                    x = -1;
                    break;
                }
            }
            server.movable(nick, x, y);
        }
    }

    public void sendExplo(int x, int y) {
        //TODO
    }

    public void sendDisco(int x, int y, int i) {
        // TODO
    }

    public void send(Emitter e){
        com.send(e);
    }
}
