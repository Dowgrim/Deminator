package fr.meusnnog.deminator.client.net;


import fr.meusnnog.deminator.client.game.Deminator;
import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.status.CComBefore;
import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.net.communication.Communicator;

import java.awt.*;
import java.io.IOException;

public class ClientDem {
	public String nickName;
	private Color color;
	private Communicator com = null;
	private boolean gameInProgres;
	private Deminator view;

	private ClientFrame clientFrame;

	public ClientDem(ClientFrame cF) {
		this.clientFrame = cF;
	}

	public void createPlayer(String nickName, Color color) {
		this.nickName = nickName;
		this.color = color;
	}

	public void sendCommand(DemEmitter emitter) {
		com.send(emitter);
	}

	public void closeClient() {
		com.stop();
		com.close();
		com = null;
	}

	public void startClient(String host, int port) throws IOException{
		com = new Communicator(host, port, new CComBefore(clientFrame));
	}
}
