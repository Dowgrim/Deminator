package fr.meusnnog.deminator.client.menu;

import fr.meusnnog.deminator.client.net.ClientDem;
import fr.meusnnog.deminator.client.net.actions.sendAndRecieve.CommandPong;
import fr.meusnnog.deminator.graphics.menu.JSPPlayersList;
import fr.meusnnog.deminator.graphics.menu.PanelSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientFrame extends JFrame {

	private ClientDem cli;

	private final JPanel mainPanel;

	private final JPanel pCenter;

	private final PanelConnexion pc;

	private final PanelSettings ps;

	private final JSPPlayersList sppl;

	/**
	 *
	 */
	private static final long serialVersionUID = -5930605691302577354L;


	public ClientFrame() {
		super("Deminator !!(Client)");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		setContentPane(mainPanel);

		pCenter = new JPanel();
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));


		ps = new PanelSettings(PanelSettings.Rank.CLIENT);

		sppl = new JSPPlayersList();

		pc = new PanelConnexion(this);

		pCenter.add(pc);
		pCenter.add(ps);

		mainPanel.add(pCenter);
		mainPanel.add(sppl);

		pcInitialisation();
		psInitialisation();
		spplInitialisation();

		setSize(600, 600);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void pcInitialisation() {
		pc.setBorder(new EmptyBorder(10, 10, 10, 10));
		pc.setBounds(0, 0, 0, 150);
		pc.setMaximumSize(new Dimension(2000, 250));
	}

	private void psInitialisation() {

	}

	private void spplInitialisation() {
		sppl.setBounds(mainPanel.getWidth() - 300, 0, 300, mainPanel.getHeight());
	}

	public void addPlayer(String pseudo, Color color) {
		sppl.putPlayerToList(pseudo, color);
	}

	public void makePlayerReady(boolean isReady){
		sppl.makePlayerReady(cli.nickName, isReady);
	}

	public void receivePong(long ms) {
		pc.showMessage("Receive PONG " + ms + " ms");
	}

	public void receivePing(String ms) {
		cli.sendCommand(new CommandPong(ms));
	}

	public void setClient(ClientDem client) {
		this.cli = client;
	}

	public static void main(String[] args) {
		new ClientFrame();
	}
}
