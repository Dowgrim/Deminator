package server;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by Michael on 29/04/2016.
 */
public class ServerView extends JFrame {

    private ServerSocket server;

    private Grid grid;

    private ArrayList<Player> players;

    public ServerView(){
        

        try {
            server = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitingPlayer();
    }

    private void waitingPlayer() {
        for(int i = 0; i < 10; i++) {
            try {
                players.add(new Player(server.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
