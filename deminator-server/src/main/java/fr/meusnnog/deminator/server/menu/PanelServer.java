package fr.meusnnog.deminator.server.menu;

import fr.meusnnog.deminator.graphics.util.Util;
import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.ServerDem;
import fr.meusnnog.deminator.server.actions.sendAndRecieve.CommandPing;

import javax.swing.*;
import java.awt.*;
import java.net.SocketException;

/**
 * Created by Nathaël N on 30/04/2016.
 */
public class PanelServer extends JPanel {

	private ServerDem server;
	private final JTextField jtfPortS;
	private final JButton jbCancelServ, jbLaunchServ, jbPingAllPlayers;
	private final JLabel jlStatus;

	private final ServerFrame serverFrame;

	public PanelServer(ServerFrame sF) {
		setLayout(new BorderLayout());

		this.serverFrame = sF;
		jtfPortS = new JTextField("4224", 5);
		jtfPortS.setMaximumSize(new Dimension(50, 200));
		jbCancelServ = new JButton("Cancel");
		jbLaunchServ = new JButton("Launch server");
		jbPingAllPlayers = new JButton("Ping All");
		jlStatus = new JLabel("Waiting to push button");
		jlStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		JPanel jpRight = generateRight();
		JPanel jpBas = new JPanel();
		{
			jpBas.setLayout(new BoxLayout(jpBas, BoxLayout.Y_AXIS));
			jpBas.add(new JSeparator());
			jpBas.add(jlStatus);
		}

		add(jpRight, BorderLayout.CENTER);
		add(jpBas, BorderLayout.SOUTH);
	}

	private JPanel generateRight() {
		JPanel jpRight = new JPanel();
		jpRight.setLayout(new BoxLayout(jpRight, BoxLayout.Y_AXIS));

		JPanel jpHaut = new JPanel();
		jpHaut.setLayout(new BoxLayout(jpHaut, BoxLayout.X_AXIS));

		jpHaut.add(new JLabel("IP:"));

		String a = "#IPError#";
		try {
			a = Util.getFirstNonLoopbackAddress(false).getHostAddress();
		} catch (SocketException | NullPointerException ignored) {}
		jpHaut.add(new JLabel(a));
		jpHaut.add(new JLabel(" : "));
		jpHaut.add(jtfPortS);

		JPanel jpBas = new JPanel();
		jbLaunchServ.addActionListener(al->clickLaunchServ());
		jbCancelServ.addActionListener(al->clickCancelServ());
		jbPingAllPlayers.addActionListener(al->clickPingAll());
		jbCancelServ.setVisible(false);
		jpBas.add(jbLaunchServ);
		jpBas.add(jbCancelServ);
		jpBas.add(jbPingAllPlayers);

		jpRight.add(jpHaut);
		jpRight.add(jpBas);

		return jpRight;
	}

	private void clickLaunchServ() {
		jlStatus.setText("Trying to create model...");
		try {
			int port = Integer.parseUnsignedInt(jtfPortS.getText());
			setEnabledAll(false);

			jbLaunchServ.setVisible(false);
			jbCancelServ.setVisible(true);

			//Start to accept client
			server = new ServerDem(port, serverFrame);
		} catch(NumberFormatException e) {
			jlStatus.setText("Error: Server's Port should be an unsigned integer.");
		} catch (Exception e) {
			jlStatus.setText("?!Error?! " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void clickCancelServ() {
		jlStatus.setText("Cancelling...");
		jbCancelServ.setEnabled(false);
		server.closeSocketServeur();
	}

	private void clickPingAll() {
		DemEmitter cmd = new CommandPing();
		server.sendBroadcast(cmd.buildCommand());
	}

	private void setEnabledAll(boolean tf) {
		jtfPortS.setEnabled(tf);
		jbLaunchServ.setEnabled(tf);
	}

	public void reset() {
		setEnabledAll(true);
		jbLaunchServ.setVisible(true);
		jbCancelServ.setVisible(false);
		jbCancelServ.setEnabled(true);
		jlStatus.setText("Waiting to push button");
	}

	public void showMessage(String message) {
		jlStatus.setText("Controller: " + message);
	}
}
