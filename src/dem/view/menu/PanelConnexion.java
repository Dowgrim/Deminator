package dem.view.menu;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dem.net.client.ClientDem;

import java.awt.*;

/**
 * Created by Nathaël N on 30/04/2016.
 */
public class PanelConnexion extends JPanel {

	private ClientDem cli;
	private final JTextField jtfIpC, jtfPortC;
	private final JComboBox jcbIpS;
	private final JButton jbLaunchClient, jbCancelClient;
	private final JLabel jlStatus;
	private final JTextField jtfPseudo;

	public PanelConnexion(ClientDem cli){
		this.cli = cli;
		setLayout(new BorderLayout());

		jtfPseudo = new JTextField();
		jtfIpC =  new JTextField("128.0.0.1", 15);
		jcbIpS = new JComboBox();

		jtfPortC = new JTextField("4224", 5);
		jbLaunchClient = new JButton("Connect");
		jbCancelClient = new JButton("Cancel");
		jlStatus = new JLabel("Waiting to push button");
		jlStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		JPanel jpLeft = generateLeft();
		JPanel jpBas = new JPanel();
		{
			jpBas.setLayout(new BoxLayout(jpBas, BoxLayout.Y_AXIS));
			jpBas.add(new JSeparator());
			jpBas.add(jlStatus);
		}

		add(jpLeft, BorderLayout.WEST);
		add(jpBas, BorderLayout.SOUTH);
		jpBas.setBorder(new EmptyBorder(10, 10, 10, 10));
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

	private void clicTryConnect() {
		jlStatus.setText("Trying to connect...");

		int port;
		try {
			port = Integer.parseUnsignedInt(jtfPortC.getText());
			setEnabledAll(false);

			jbLaunchClient.setVisible(false);
			jbCancelClient.setVisible(true);
			cli.start(jtfIpC.getText(), Integer.parseInt(jtfPortC.getText()), jtfPseudo.getText());
		}catch(NumberFormatException e) {
			jlStatus.setText("Error: Client's port should be an unsigned integer.");
		}
	}


	private void setEnabledAll(boolean tf) {
		jtfIpC.setEnabled(tf);
		jtfPortC.setEnabled(tf);
		jtfPseudo.setEnabled(tf);
		jcbIpS.setEnabled(tf);
		jbLaunchClient.setEnabled(tf);
	}

	public void reset() {
		setEnabledAll(true);
		jbLaunchClient.setVisible(true);
		jbCancelClient.setVisible(false);
		jbCancelClient.setEnabled(true);
		jlStatus.setText("Waiting to push button");
	}

	public void showMessage(String message) {
		jlStatus.setText("Controller: " + message);
	}
}
