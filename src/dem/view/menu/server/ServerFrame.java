package dem.view.menu.server;

import dem.net.client.ClientDem;
import dem.net.server.ServerDem;
import dem.view.menu.JSPPlayersList;
import dem.view.menu.PanelSettings;
import dem.view.menu.client.ClientFrame;
import dem.view.menu.client.PanelConnexion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ServerFrame extends JFrame {


	private final JPanel mainPanel;

	private final JPanel pCenter;

	private final PanelServer pc;

	private final PanelSettings ps;

	private final JSPPlayersList jsppl;

	/**
	 *
	 */
	private static final long serialVersionUID = -5930605691302577354L;


	public ServerFrame() {
		super("Deminator !!(Serveur)");

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		setContentPane(mainPanel);

		pCenter = new JPanel();
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));


		ps = new PanelSettings();

		jsppl = new JSPPlayersList();

		pc = new PanelServer(jsppl);

		pCenter.add(pc);
		pCenter.add(ps);

		mainPanel.add(pCenter);
		mainPanel.add(jsppl);

		pcInitialisation();
		psInitialisation();
		spplInitialisation();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
		jsppl.setBounds(mainPanel.getWidth() - 300, 0, 300, mainPanel.getHeight());
	}


	public static void main(String[] args) {
		new ServerFrame();
	}

}
