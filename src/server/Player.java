package server;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Created by Michael on 29/04/2016.
 */
public class Player extends Thread{

    private Server server;

    private String name;

    private Color color;

    private int points;

    private int shield;

    private Socket socket;

    private BufferedReader input;

    public Player(Server serv , Socket s){
        server = serv;
        socket = s;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                //new Analyser(msg, workers).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Analyser extends Thread{

        private String msg;

        public void Analyser(String m) {
            msg = m;
        }


        @Override
        public void run(){
            analyse();
        }


        private void analyse() {

        }
    }

}
