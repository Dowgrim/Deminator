package common;

import javax.swing.*;
import java.awt.*;

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

		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setMinimumSize(new Dimension(150, (int)getMinimumSize().getHeight()));
		setPreferredSize(new Dimension(150, (int)getMinimumSize().getHeight()));
	}

	public synchronized void putPlayerToList(String playerName, Color playerColor) {
		JPPlayer jpp = new JPPlayer(playerName, playerColor);

		for(int i=viewport.getComponentCount()-1; i>=0; i--) {
			Component c = viewport.getComponent(i);
			if(!c.equals(jpp))
				continue;
			viewport.remove(i);
			viewport.add(jpp, i);
			this.revalidate();
			this.repaint();
			return;
		}

		viewport.add(jpp);
		this.revalidate();
		this.repaint();
	}
	public void removePlayerFromList(String playerName) {
		JPPlayer jpp = new JPPlayer(playerName);

		for(int i=viewport.getComponentCount()-1; i>=0; i--) {
			Component c = viewport.getComponent(i);
			if(!c.equals(jpp))
				continue;
			viewport.remove(i);
		}

		this.revalidate();
		this.repaint();
	}

	private class JPPlayer extends JPanel {
		private String playerName;
		private JPPlayer(String name) {
			this(name, Color.BLACK);
		}
		private JPPlayer(String name, Color c) {
			super();

			this.playerName = name;

			JLabel jln = new JLabel(name);

			double lum = 0.0722 * c.getBlue() + 0.7152 * c.getGreen() + 0.2126 * c.getRed();

			int blue = c.getBlue(); blue = lum > 127 ?blue/2:(blue+256)/2;
			int red = c.getRed(); red = lum > 127 ?red/2:(red+256)/2;
			int green = c.getGreen(); green = lum > 127 ?green/2:(green+256)/2;
			jln.setForeground(new Color(red, green, blue));

			add(jln);
			setBackground(c);
		}

		@Override
		public boolean equals(Object obj) {
			if(obj instanceof JPPlayer){
				JPPlayer jpp = (JPPlayer) obj;
				return (jpp.playerName.equals(playerName));
			}
			else
				return false;
		}
	}
}
