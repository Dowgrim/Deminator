package fr.meusnnog.deminator.graphics.menu;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nathaël Noguès
 * @since 2016-05-02
 */
public class JSPPlayersList extends JScrollPane {

	private JPanel viewportList;

	private Map<String, JPPlayer> jpPlayers = new HashMap<>();

	public JSPPlayersList() {
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		viewportList = new JPanel();
		jp.add(viewportList, BorderLayout.NORTH);
		setViewportView(jp);

		viewportList.setLayout(new GridLayout(0, 1, 2, 2));

		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setMinimumSize(new Dimension(150, (int)getMinimumSize().getHeight()));
		setPreferredSize(new Dimension(150, (int)getMinimumSize().getHeight()));
	}

	public synchronized void putPlayerToList(String playerName, Color playerColor) {
		JPPlayer jpp = new JPPlayer(playerName, playerColor);
        jpPlayers.put(playerName, jpp);

		for (int i = viewportList.getComponentCount() - 1; i >= 0; i--) {
			Component c = viewportList.getComponent(i);
			if (!c.equals(jpp))
				continue;
			viewportList.remove(i);
			viewportList.add(jpp, i);
			this.revalidate();
			this.repaint();
			return;
		}

		viewportList.add(jpp);
		this.revalidate();
		this.repaint();
	}

	public void removePlayerFromList(String playerName) {
		JPPlayer jpp = new JPPlayer(playerName);

		for (int i = viewportList.getComponentCount() - 1; i >= 0; i--) {
			Component c = viewportList.getComponent(i);
			if (!c.equals(jpp))
				continue;
			viewportList.remove(i);
		}

		this.revalidate();
		this.repaint();
	}

	public void setReady(String playerName, boolean isReady) {
		JPPlayer jpp = jpPlayers.get(playerName);
		jpp.jCBIsReady.setSelected(isReady);
		this.revalidate();
		this.repaint();
	}

	private class JPPlayer extends JPanel {
		private String playerName;

		private JPPlayer(String name) {
			this(name, Color.BLACK);
		}

		private JCheckBox jCBIsReady;

		private JPPlayer(String name, Color c) {
			super();

			setLayout(new BorderLayout());

			this.playerName = name;

			JLabel jln = new JLabel(name);

			jCBIsReady = new JCheckBox("", false);
			jCBIsReady.setEnabled(false);
			jCBIsReady.setBackground(c);

			/*double lum = 0.0722 * c.getBlue() + 0.7152 * c.getGreen() + 0.2126 * c.getRed();

			int blue = c.getBlue();
			blue = lum > 127 ? blue << 1 : (blue + 256) << 1;
			int red = c.getRed();
			red = lum > 127 ? red << 1 : (red + 256) / 2;
			int green = c.getGreen();
			green = lum > 127 ? green << 1 : (green + 256) << 1;
			jln.setForeground(new Color(red, green, blue));*/

			add(jln, BorderLayout.CENTER);
			add(jCBIsReady, BorderLayout.EAST);
			setBackground(c);
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof JPPlayer && ((JPPlayer)obj).playerName.equals(playerName);
		}
	}
}
