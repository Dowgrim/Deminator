package client.view;

import common.view.DeminatorFrame;
import common.view.PanelSettings;
import server.Server;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Nathaël N on 30/04/2016.
 */
public class PanelStart extends JPanel {

	private final DeminatorFrame papa;
	private final JTextField jtfIpC, jtfPortC, jtfPortS;
	private final JComboBox jtfIpS;
	private final JButton jbConnect, jbCancel, jbLaunchServ;
	private final JLabel jlStatus;
	private final JTextField jtfPseudo;

	public PanelStart(DeminatorFrame papa){
		this.papa = papa;
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		jtfPseudo = new JTextField();
		jtfIpC =  new JTextField("128.0.0.1", 15);
		jtfIpS = new JComboBox();

		jtfPortC = new JTextField("4224", 5);
		jtfPortS = new JTextField("4224", 5);
		jbConnect = new JButton("Connect");
		jbCancel = new JButton("Cancel");
		jbLaunchServ = new JButton("Be a server");
		jlStatus = new JLabel("Waiting to push button");

		JPanel jpLeft = generateLeft();
		JPanel jpRight = generateRight();
		JPanel jpBas = new JPanel();
		{
			jpBas.setLayout(new BoxLayout(jpBas, BoxLayout.Y_AXIS));
			jpBas.add(new JSeparator());

			JPanel jpp = new JPanel();
			jpp.add(new JLabel("Status: "));
			jpp.add(jlStatus);
			jpBas.add(jpp);
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
				jbConnect.addActionListener(al -> clicTryConnect());
				jbCancel.addActionListener(al -> clicCancel());
				jbCancel.setVisible(false);


			jpMid.setLayout(new BoxLayout(jpMid, BoxLayout.X_AXIS));
			jpMid.add(jbConnect);
			jpMid.add(jbCancel);
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
						jtfIpS.addItem(e.getDisplayName()+" ("+addr+")");
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		jpHaut.add(jtfIpS);
		jpHaut.add(new JLabel(":"));
		jpHaut.add(jtfPortS);


		JPanel jpBas = new JPanel();
		jbLaunchServ.addActionListener(al -> clicLaunchServ());
		jpBas.add(jbLaunchServ);

		jpRight.add(jpHaut);
		jpRight.add(jpBas);

		return jpRight;
	}

	private void clicLaunchServ() {
		papa.setView(new PanelSettings(papa));
	}

	private void clicTryConnect() {
		jlStatus.setText("Trying to connect...");
		jtfIpC.setEnabled(false);
		jtfPortC.setEnabled(false);
		jtfPseudo.setEnabled(false);

		jbConnect.setVisible(false);
		jbCancel.setVisible(true);

		//TODO Some network stuff //////////////////////////////////////////////////////////////

		//TODO Following: Network Stub
		papa.setView(new PanelSettings(papa, new Server()));
		//TODO ^^^^^^^^^^^^^^^^^^^^^^^

		jlStatus.setText("Waiting to push button");
	}

	private void clicCancel() {
		jlStatus.setText("Cancelling connect try...");
		jtfIpC.setEnabled(true);
		jtfPortC.setEnabled(true);
		jtfPseudo.setEnabled(true);

		//TODO Some cancelling network stuff //////////////////////////////////////////////////

		jlStatus.setText("Waiting to push button");
		jbConnect.setVisible(true);
		jbCancel.setVisible(false);
	}
}
