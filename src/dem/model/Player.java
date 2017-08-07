package dem.model;


import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import dem.net.server.ServerDem;

/**
 * Created by Michael on 29/04/2016.
 */
public class Player extends Thread{

    private int posX, posY;

    private ServerDem server;

    private String nick;

    private Color color;

    private int points;

    private int shield;

    private boolean ready = false;

    private Stun stun;

    private Socket socket;

    private BufferedReader input;

    private PrintWriter output;

    public Player(ServerDem serv , Socket s){
        server = serv;
        socket = s;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run(){
        String msg = null;
        try {
            while ((msg = input.readLine()) != null) {
                if (msg.equals("")) continue;
                new Analyser(msg).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public void sendStun(String nick, int i) {
        output.write("STU " + nick + " " + i);
    }

    public void sendMove(String nick, int x, int y) {
        output.write("MOV " + nick + " " + x + " " + y);
    }

    public void sendDisco(int x, int y, int val) {
        output.write("CommandDIS " + x + " " + y + " " + val);
    }

    public void stun() {
        stun.interrupt();
        stun = new Stun(server, this);
    }

    public void sendExplo(int x, int y) {
        output.write("CommandExp " + x + " " + y);
    }

    private class Analyser extends Thread{

        private String msg;

        public Analyser(String m) {
            msg = m;
        }


        @Override
        public void run(){
            analyse();
        }


        private void analyse() {
            StringTokenizer tokenizer;
            String[] tokens = new String[10];

            tokenizer = new StringTokenizer(msg);

            tokens[0] = tokenizer.nextToken();
            switch(tokens[0]){
                case "CommandNew":{
                    tokens[1] = tokenizer.nextToken();
                    tokens[2] = tokenizer.nextToken();
                    server.modPlayer(nick, tokens[1], tokens[2]);
                    break;
                }
                case "OK!":{

                    break;
                }
                case"CommandDEP":{
                    tokens[1] = tokenizer.nextToken();
                    move(tokens[1]);
                    break;
                }
                case"CommandExp":{
                    server.explosion(nick, posX, posY);
                    break;
                }
            }
        }
    }

}
