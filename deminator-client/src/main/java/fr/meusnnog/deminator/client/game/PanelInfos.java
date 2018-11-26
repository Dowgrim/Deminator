package fr.meusnnog.deminator.client.game;

import fr.meusnnog.deminator.client.net.ClientDem;

import javax.swing.*;
import java.awt.*;

public class PanelInfos extends JPanel {

	private ClientDem cli;
	private JLabel pingInfo;

	public PanelInfos(ClientDem c) {
		cli = c;
		setBorder(BorderFactory.createEtchedBorder());

		//TODO en fonction de la taille de l'écran
		setMinimumSize(new Dimension(150, (int)getMinimumSize().getHeight()));
		setPreferredSize(new Dimension(150, (int)getMinimumSize().getHeight()));
		setMaximumSize(new Dimension(150, 1000));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new Button("Ping"));
		pingInfo = new JLabel("Ping inconnu");
		add(pingInfo);
		JPanel size = new JPanel();
		size.setLayout(new FlowLayout());
		size.add(new JLabel("x:10"));
		size.add(new JLabel("y:10"));
		add(size);

	}

	public void setPing(long ping) {
		pingInfo.setText("Ping:" + ping);
	}
}
