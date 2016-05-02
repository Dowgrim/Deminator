package client.view;

import common.DeminatorFrame;
import common.menu.PanelSettings;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

/**
 * Created by Nathaël N on 30/04/2016.
 */
public class PanelClientConnect extends JPanel {

	private final DeminatorFrame papa;
	private final JTextField jtfIp;
	private final JTextField jtfPort;
	private final JButton jbConnect;
	private final JButton jbCancel;
	private final JLabel jlStatus;
	private final JTextField jtfPseudo;

	public PanelClientConnect(DeminatorFrame papa){
		this.papa = papa;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel jpPseudo = new JPanel();
		{
			jtfPseudo = new JTextField();
			jtfPseudo.setMaximumSize(new Dimension(150, (int)jtfPseudo.getMinimumSize().getHeight()));
			jpPseudo.setLayout(new BoxLayout(jpPseudo, BoxLayout.X_AXIS));
			jpPseudo.add(new JLabel("Pseudo:"));
			jpPseudo.add(jtfPseudo);
		}

		JPanel jpHaut = new JPanel();
		{
			jpHaut.setLayout(new BoxLayout(jpHaut, BoxLayout.X_AXIS));

			jtfIp = new JTextField("128.0.0.1", 15);
			jtfPort = new JTextField("8042", 5);

			jpHaut.add(new JLabel("Server IP:"));
			jpHaut.add(jtfIp);
			jpHaut.add(new JLabel(":"));
			jpHaut.add(jtfPort);
		}

		JPanel jpMid = new JPanel();
		{
			jbConnect = new JButton("Try Connect");
			{
				jbConnect.addActionListener(al -> clicTryConnect());
			}
			jbCancel = new JButton("Cancel");
			{
				jbCancel.addActionListener(al -> clicCancel());
				jbCancel.setVisible(false);
			}

			jpMid.setLayout(new BoxLayout(jpMid, BoxLayout.X_AXIS));
			jpMid.add(jbConnect);
			jpMid.add(jbCancel);
		}

		JPanel jpBas = new JPanel();
		{
			jpBas.setLayout(new BoxLayout(jpBas, BoxLayout.X_AXIS));

			jpBas.add(new JLabel("Status: "));
			jlStatus = new JLabel("Waiting to push button");
			jpBas.add(jlStatus);
		}

		add(jpPseudo);
		add(jpHaut);
		add(jpMid);
		add(jpBas);
	}

	private void clicTryConnect() {
		jlStatus.setText("Trying to connect...");
		jtfIp.setEnabled(false);
		jtfPort.setEnabled(false);
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
		jtfIp.setEnabled(true);
		jtfPort.setEnabled(true);
		jtfPseudo.setEnabled(true);

		//TODO Some cancelling network stuff //////////////////////////////////////////////////

		jlStatus.setText("Waiting to push button");
		jbConnect.setVisible(true);
		jbCancel.setVisible(false);
	}
}
