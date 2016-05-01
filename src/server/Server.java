package server;

import menu.SettingFrame;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Michael on 29/04/2016.
 */
public class Server {

    private SettingFrame setF;

    private ServerSocket server;

    private Grid grid;

    private HashMap<String, Player> players;

    public Server(){
        setF = new SettingFrame(true);
        try {
            server = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitingPlayer();
    }

    private void closeServeur() {
    }

    private void waitingPlayer() {
        for(int i = 0; i < 10; i++) {
            try {
                players.put(i+"", new Player(this, server.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
