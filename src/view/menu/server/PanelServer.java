package view.menu.server;

import net.server.ServerDem;

import javax.swing.*;

import java.awt.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by Nathaël N on 30/04/2016.
 */
public class PanelServer extends JPanel {

    private ServerDem server;
	private final JTextField jtfPortS;
	private final JButton jbCancelServ, jbLaunchServ;
	private final JLabel jlStatus;

	public PanelServer(ServerDem ser){
	    server = ser;
		setLayout(new BorderLayout());

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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        jpHaut.add(new JLabel(a.getHostAddress()));
		jpHaut.add(new JLabel(":"));
		jpHaut.add(jtfPortS);

		JPanel jpBas = new JPanel();
		jbLaunchServ.addActionListener(al -> clicLaunchServ());
		jbCancelServ.addActionListener(al -> clicCancelServ());
		jbCancelServ.setVisible(false);
		jpBas.add(jbLaunchServ);
		jpBas.add(jbCancelServ);

		jpRight.add(jpHaut);
		jpRight.add(jpBas);

		return jpRight;
	}

	private void clicLaunchServ() {
		jlStatus.setText("Trying to create server...");
		int port;
		try {
			port = Integer.parseUnsignedInt(jtfPortS.getText());
			setEnabledAll(false);

			jbLaunchServ.setVisible(false);
			jbCancelServ.setVisible(true);
		}catch(NumberFormatException e) {
			jlStatus.setText("Error: Server's Port should be an unsigned integer.");
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
