package dem.view.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dem.net.client.ClientDem;

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