package server;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
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

    private Object stun;

    private Socket socket;

    private BufferedReader input;

    private PrintWriter output;

    public Player(Server serv , Socket s){
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
                case "OK!":{

                    break;
                }
                case"MOD":{
                    //Yolo osef pour le moment
                    break;
                }
                case"DEP":{
                    tokens[1] = tokenizer.nextToken();
                    move(tokens[1]);
                    break;
                }
                case"EXP":{
                    break;
                }
            }
        }
    }

    private void move(String dir) {
        switch(dir){
            case "N":{

                break;
            }
            case "E":{

                break;
            }
            case "S":{

                break;
            }
            case "W":{

                break;
            }
        }
    }

}
