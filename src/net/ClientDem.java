package net;

import java.awt.Color;
import java.io.IOException;

import view.game.Deminator;

/**
 * Created by Nathaël N on 04/05/16.
 */
public class ClientDem implements Receiver{
	private String pseudo;
	private Color color;
	private SockCom sock = null;
	private boolean gameInProgres;
	private Deminator view;
	
	public void start(String host, int port, String pseudo) {
		this.pseudo = pseudo;
		try {
			sock = new SockCom(host, port, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		view = new Deminator(this, 4, 4);
	}

	public void setPseudoAndColor(String pseudo, Color color) {
		this.pseudo = pseudo;
		this.color = color;
	}

	@Override
	public void receive(String str) {
		String[] split = str.split(" ");
		if(gameInProgres) {
			actionDuringGames(split);
		}
		else {
			actionBeforeGames(split);
		}
			
	}
	
	private void actionDuringGames(String[] split) {
		switch(split[0]) {
			case "DEP":{
				break;
			}
			case "EXP":{
				break;
			}
			case "DIS":{
				break;
			}
			case "STUN":{
				break;
			}
			case "GAN":{
				break;
			}
			case "END":{
				break;
			}
		}
	}

	public void actionBeforeGames(String[] split) {
		switch(split[0]) {
			case "NEW":{
				break;
			}
			case "QUIT":{
				break;
			}
			case "PING":{
				break;
			}
			case "RDY":{
				break;
			}
			case "OPT":{
				break;
			}
			case "LDD":{
				break;
			}
		}
	}

    public void action(int keyCode) {
    }
}
