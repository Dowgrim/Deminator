package dem.client.graphic.menu;

import dem.client.net.OldClientDem;
import dem.common.graphic.JSPPlayersList;
import dem.common.graphic.PanelSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientFrame extends JFrame {

	private OldClientDem cli;

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


		ps = new PanelSettings();

		sppl = new JSPPlayersList();

		pc = new PanelConnexion(sppl);

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
}
