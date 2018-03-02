package dem.client.graphic.game;

import dem.client.net.OldClientDem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Michael on 16/06/2016.
 */
public class PlayerListener implements KeyListener {

	private OldClientDem cli;

	public PlayerListener(OldClientDem c) {
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
