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
        setF = new SettingFrame(this);
        try {
            server = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitingPlayer();
    }

    private void closeSocketServeur() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitingPlayer() {
        for(int i = 0; i < 10; i++) {
            try {
                players.put(i+"", new Player(this, server.accept()));
            }catch (IOException e){/*Yolo*/}
        }
    }


    public void laucher(/*setting from the settingFrame*/){
        grid = new Grid(/*setting from the settingFrame*/4, 4);
    }

    public void modPlayer(String preNick, String nick, String col) {
        Player p = players.get(preNick);
        p.setName(nick);
        players.put(nick, p);
    }
}
