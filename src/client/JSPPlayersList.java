package client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nathaël N on 02/05/16.
 */
public class JSPPlayersList extends JScrollPane {

	private JPanel viewport;

	public JSPPlayersList() {
		viewport = new JPanel();
		setViewportView(viewport);

		viewport.setBackground(Color.WHITE);
		viewport.setLayout(new GridLayout(0, 1, 2, 2));

		// TODO remove > this is for test only
		for(int i=0; i<21; i++)
			putPlayerToList("PlayerName"+(2<<i),
					new Color((int)(Math.random()*256),
							(int)(Math.random()*256),
							(int)(Math.random()*256)));
		// TODO ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		Runnable task = () -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			putPlayerToList("PlayerName8", Color.black);
		};
		task.run();
	}

	public synchronized void putPlayerToList(String playerName, Color playerColor) {
		JPPlayer jpp = new JPPlayer(playerName, playerColor);

		for(int i=viewport.getComponentCount()-1; i>=0; i--) {
			Component c = viewport.getComponent(i);
			if(!(c instanceof JPPlayer))
				continue;

			JPPlayer jppp = (JPPlayer)c;
			if (jppp.playerName.equals(playerName)) {
				viewport.remove(i);
				viewport.add(jpp, i);
				return;
			}
		}

		viewport.add(jpp);
	}

	public void removePlayerFromList(String playerName) {
	}

	private class JPPlayer extends JPanel {
		private String playerName;
		private JPPlayer(String name, Color c) {
			super();

			this.playerName = name;

			JLabel jln = new JLabel(name);

			double lum = 0.0722 * c.getBlue() + 0.7152 * c.getGreen() + 0.2126 * c.getRed();

			jln.setForeground((lum > 128)
					?( (lum > 128+64)?c.darker().darker():c.darker() )
					:( (lum < 64) ? c.brighter().brighter():c.brighter()));

			add(jln);
			setBackground(c);
		}
	}
}
