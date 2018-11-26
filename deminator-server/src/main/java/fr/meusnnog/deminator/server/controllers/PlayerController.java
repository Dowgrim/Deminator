package fr.meusnnog.deminator.server.controllers;

import fr.meusnnog.deminator.gameengine.model.Player;
import fr.meusnnog.deminator.net.communication.Communicator;
import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.status.SComBefore;
import fr.meusnnog.deminator.server.menu.ServerFrame;
import fr.meusnnog.deminator.server.ServerDem;

import java.io.IOException;
import java.net.Socket;

public class PlayerController {

    private ServerDem serv;

    private Player player;

    private Communicator com;

    public PlayerController(ServerDem s, Socket sock, ServerFrame sF) throws IOException {
        serv = s;
        player = new Player();
        com = new Communicator(sock, new SComBefore(this, sF));
    }


    public void send(DemEmitter e) {
        if(e.isBroadcast()){
            serv.sendBroadcast(e.buildCommand());
        }
        else{
            com.send(e);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Communicator getCom() {
        return com;
    }
}


