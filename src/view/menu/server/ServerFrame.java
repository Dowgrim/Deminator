package view.menu.server;

import net.client.ClientDem;
import net.server.ServerDem;
import view.menu.JSPPlayersList;
import view.menu.PanelSettings;
import view.menu.client.ClientFrame;
import view.menu.client.PanelConnexion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ServerFrame extends JFrame {


	private final JPanel mainPanel;

	private final JPanel pCenter;

	private final PanelServer pc;

	private final PanelSettings ps;

	private final JSPPlayersList sppl;

	/**
	 *
	 */
	private static final long serialVersionUID = -5930605691302577354L;


	public ServerFrame() {
		super("Deminator !!(Client)");

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		setContentPane(mainPanel);

		pCenter = new JPanel();
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));

		ServerDem ser = new ServerDem();
		pc = new PanelServer(ser);

		ps = new PanelSettings();

		sppl = new JSPPlayersList();

		pCenter.add(pc);
		pCenter.add(ps);

		mainPanel.add(pCenter);
		mainPanel.add(sppl);

		pcInitialisation();
		psInitialisation();
		spplInitialisation();

		setSize(600, 600);
		setVisible(true);
	}

	private void pcInitialisation() {
		pc.setBorder(new EmptyBorder(10, 10, 10, 10));
		pc.setBounds(0, 0, 0, 150);
		pc.setMaximumSize(new Dimension(2000,250));
	}

	private void psInitialisation() {

	}

	private void spplInitialisation() {
		sppl.setBounds(mainPanel.getWidth() - 300, 0, 300, mainPanel.getHeight());
	}


	public static void main(String[] args) {
		new ServerFrame();
	}

}
