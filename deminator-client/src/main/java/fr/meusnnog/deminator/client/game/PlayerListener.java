package fr.meusnnog.deminator.client.game;

import fr.meusnnog.deminator.client.net.ClientDem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Michael on 16/06/2016.
 */
public class PlayerListener implements KeyListener {

	private ClientDem cli;

	public PlayerListener(ClientDem c) {
		cli = c;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
