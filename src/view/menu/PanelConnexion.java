package view.menu;

import util.Controller;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Nathaël N on 30/04/2016.
 */
public class PanelConnexion extends JPanel {

	private final Controller control;
	private final JTextField jtfIpC, jtfPortC, jtfPortS;
	private final JComboBox jcbIpS;
	private final JButton jbLaunchClient, jbCancelClient, jbCancelServ, jbLaunchServ;
	private final JLabel jlStatus;
	private final JTextField jtfPseudo;

	public PanelConnexion(Controller control){
		this.control = control;
		setLayout(new BorderLayout());

		jtfPseudo = new JTextField();
		jtfIpC =  new JTextField("128.0.0.1", 15);
		jcbIpS = new JComboBox();

		jtfPortC = new JTextField("4224", 5);
		jtfPortS = new JTextField("4224", 5);
		jbLaunchClient = new JButton("Connect");
		jbCancelClient = new JButton("Cancel");
		jbCancelServ = new JButton("Cancel");
		jbLaunchServ = new JButton("Be a server");
		jlStatus = new JLabel("Waiting to push button");
		jlStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		JPanel jpLeft = generateLeft();
		JPanel jpRight = generateRight();
		JPanel jpBas = new JPanel();
		{
			jpBas.setLayout(new BoxLayout(jpBas, BoxLayout.Y_AXIS));
			jpBas.add(new JSeparator());
			jpBas.add(jlStatus);
		}

		add(jpLeft, BorderLayout.WEST);
		add(new JSeparator(JSeparator.VERTICAL), BorderLayout.CENTER);
		add(jpRight, BorderLayout.EAST);
		add(jpBas, BorderLayout.SOUTH);
	}

	private JPanel generateLeft() {
		JPanel jpLeft = new JPanel();
		jpLeft.setLayout(new BoxLayout(jpLeft, BoxLayout.Y_AXIS));

		JPanel jpPseudo = new JPanel();
		{
			jtfPseudo.setMaximumSize(new Dimension(150, (int) jtfPseudo.getMinimumSize().getHeight()));
			jpPseudo.setLayout(new BoxLayout(jpPseudo, BoxLayout.X_AXIS));
			jpPseudo.add(new JLabel("Pseudo:"));
			jpPseudo.add(jtfPseudo);
		}

		JPanel jpHaut = new JPanel();
		{
			jpHaut.setLayout(new BoxLayout(jpHaut, BoxLayout.X_AXIS));

			jpHaut.add(new JLabel("Server:"));
			jpHaut.add(jtfIpC);
			jpHaut.add(new JLabel(":"));
			jpHaut.add(jtfPortC);
		}

		JPanel jpMid = new JPanel();
		{
				jbLaunchClient.addActionListener(al -> clicTryConnect());
				jbCancelClient.addActionListener(al -> clicCancel());
				jbCancelClient.setVisible(false);


			jpMid.setLayout(new BoxLayout(jpMid, BoxLayout.X_AXIS));
			jpMid.add(jbLaunchClient);
			jpMid.add(jbCancelClient);
		}

		jpLeft.add(jpPseudo);
		jpLeft.add(jpHaut);
		jpLeft.add(jpMid);

		return jpLeft;
	}
	private JPanel generateRight() {
		JPanel jpRight = new JPanel();
		jpRight.setLayout(new BoxLayout(jpRight, BoxLayout.Y_AXIS));

		JPanel jpHaut = new JPanel();
		jpHaut.setLayout(new BoxLayout(jpHaut, BoxLayout.X_AXIS));

		jpHaut.add(new JLabel("IP:"));

		try{
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			while(n.hasMoreElements()) {
				NetworkInterface e = n.nextElement();
				Enumeration<InetAddress> a = e.getInetAddresses();
				while (a.hasMoreElements()) {
					String addr = a.nextElement().getHostAddress();
					if(addr.contains("."))
						jcbIpS.addItem(e.getDisplayName()+" ("+addr+")");
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		jpHaut.add(jcbIpS);
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
			control.beAServer(port);
		}catch(NumberFormatException e) {
			jlStatus.setText("Error: Server's Port should be an unsigned integer.");
		}

	}
	private void clicTryConnect() {
		jlStatus.setText("Trying to connect...");

		int port;
		try {
			port = Integer.parseUnsignedInt(jtfPortC.getText());
			setEnabledAll(false);

			jbLaunchClient.setVisible(false);
			jbCancelClient.setVisible(true);
			control.tryToBeAClient(jtfPseudo.getText(), jtfIpC.getText(), port);
		}catch(NumberFormatException e) {
			jlStatus.setText("Error: Client's port should be an unsigned integer.");
		}
	}
	private void clicCancel() {
		jlStatus.setText("Cancelling...");
		jbCancelClient.setEnabled(false);
		control.cancelBeAClient();
	}
	private void clicCancelServ() {
		jlStatus.setText("Cancelling...");
		jbCancelServ.setEnabled(false);
		control.cancelBeAServ();
	}
	private void setEnabledAll(boolean tf) {
		jtfIpC.setEnabled(tf);
		jtfPortC.setEnabled(tf);
		jtfPseudo.setEnabled(tf);
		jcbIpS.setEnabled(tf);
		jtfPortS.setEnabled(tf);

		jbLaunchClient.setEnabled(tf);
		jbLaunchServ.setEnabled(tf);
	}

	public void reset() {
		setEnabledAll(true);
		jbLaunchClient.setVisible(true);
		jbLaunchServ.setVisible(true);
		jbCancelClient.setVisible(false);
		jbCancelClient.setEnabled(true);
		jbCancelServ.setVisible(false);
		jbCancelServ.setEnabled(true);
		jlStatus.setText("Waiting to push button");
	}

	public void showMessage(String message) {
		jlStatus.setText("Controller: " + message);
	}
}
