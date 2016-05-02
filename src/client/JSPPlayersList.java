package client;

import menu.JPPlayer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Nathaël N on 02/05/16.
 */
public class JSPPlayersList extends JScrollPane {

	public JSPPlayersList() {
		JPanel viewport = new JPanel();
		setViewportView(viewport);

		viewport.setBackground(Color.WHITE);
		viewport.setLayout(new GridLayout(0, 1, 2, 2));

		// TODO remove > this is for test only
		for(int i=0; i<21; i++)
			viewport.add(new JPPlayer("PlayerName"+(2<<i),
					new Color((int)(Math.random()*256),
							(int)(Math.random()*256),
							(int)(Math.random()*256))));
		// TODO ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
}
