package server;

import java.awt.*;
import java.net.Socket;

/**
 * Created by Michael on 29/04/2016.
 */
public class Player {

    private String name;

    private Color color;

    private int points;

    private int shield;

    private Socket socket;

    public Player(Socket s){
        socket = s;
    }


}
