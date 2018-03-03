package dem.server.graphic.menu;

import dem.common.graphic.JSPPlayersList;
import dem.server.model.Player;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Created by Nathaël N on 30/04/2016.
 */
public class PanelServer extends JPanel {

	//private OldServerDem server;
	private HashMap<String, Player> players;
	private final JTextField jtfPortS;
	private final JButton jbCancelServ, jbLaunchServ;
	private final JLabel jlStatus;

	private final JSPPlayersList sppl;

	public PanelServer(JSPPlayersList sppl) {
		setLayout(new BorderLayout());

		this.sppl = sppl;
		jtfPortS = new JTextField("4224", 5);
		jtfPortS.setMaximumSize(new Dimension(50, 200));
		jbCancelServ = new JButton("Cancel");
		jbLaunchServ = new JButton("Launch server");
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

		InetAddress a = null;
		try {
			a = InetAddress.getLocalHost();
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
		jpHaut.add(new JLabel(a.getHostAddress()));
		jpHaut.add(new JLabel(":"));
		jpHaut.add(jtfPortS);

		JPanel jpBas = new JPanel();
		jbLaunchServ.addActionListener(al->clicLaunchServ());
		jbCancelServ.addActionListener(al->clicCancelServ());
		jbCancelServ.setVisible(false);
		jpBas.add(jbLaunchServ);
		jpBas.add(jbCancelServ);

		jpRight.add(jpHaut);
		jpRight.add(jpBas);

		return jpRight;
	}

	private void clicLaunchServ() {
		jlStatus.setText("Trying to create model...");
		int port = 0;
		try {
			port = Integer.parseUnsignedInt(jtfPortS.getText());
			setEnabledAll(false);

			jbLaunchServ.setVisible(false);
			jbCancelServ.setVisible(true);

			//Start to accept client
			//server = new OldServerDem(port, sppl);
		} catch(NumberFormatException e) {
			jlStatus.setText("Error: Server's Port should be an unsigned integer.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void clicCancelServ() {
		jlStatus.setText("Cancelling...");
		jbCancelServ.setEnabled(false);
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
