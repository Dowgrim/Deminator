package client.view;

import common.DeminatorFrame;
import common.JSPPlayersList;
import menu.PanelSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Nathaël N on 01/05/16.
 */
public class DeminatorGame extends DeminatorFrame {

	private DeminatorGame() {
		super("Deminator !");

		// CONTENT
		setDefaultView();
	}

	public void setDefaultView() {
		setView(new PanelClientConnect(this));
	}
	public void setViewToSettings() { setView(new PanelSettings(this)); }

	// CLIENT MAIN
	public static void main(String[] args) {
		DeminatorGame dg = new DeminatorGame();

		// NEXT IS FOR TESTS ONLY
		new Thread() {
			@Override
			public void run() {
				try {
					int i=1000;
					while(--i > 0) {
						Thread.sleep((int)(Math.random()*1900+100));
						switch((int)(Math.random()*2)) {
							case 0:
								dg.removePlayerFromList("Player"+i%32);
								break;
							case 1:
								dg.putPlayerToList("Player"+(i%32),
										new Color((int) (Math.random() * 256),
												(int) (Math.random() * 256),
												(int) (Math.random() * 256)));
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.run();
	}
}