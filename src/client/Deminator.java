package client;

import javax.swing.*;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by Michael on 29/04/2016.
 */
public class Deminator extends JFrame {

	private final JTextField jtfIp;
	private final JTextField jtfPort;
	private final JButton jbConnect;
	private final JButton jbCancel;
	private final JLabel jlStatus;
	private final JTextField jtfPseudo;
	private Socket socket;

    public Deminator(){
        super("Deminator!!");

	    JPanel jpMain = new JPanel();
	    {
		    jpMain.setLayout(new BoxLayout(jpMain, BoxLayout.Y_AXIS));

		    JPanel jpInfos = new JPanel();
		    {
			    jtfPseudo = new JTextField();
			    jpInfos.setLayout(new BoxLayout(jpInfos, BoxLayout.X_AXIS));
			    jpInfos.add(new JLabel("Pseudo:"));
			    jpInfos.add(jtfPseudo);
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

		    jpMain.add(jpInfos);
		    jpMain.add(jpHaut);
		    jpMain.add(jpMid);
		    jpMain.add(jpBas);
	    }// JPMain
	    setContentPane(jpMain);

	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    this.pack();
    }

	private void clicTryConnect() {
		jlStatus.setText("Trying to connect...");
		jtfIp.setEnabled(false);
		jtfPort.setEnabled(false);
		jbConnect.setVisible(false);
		jbCancel.setVisible(true);

		// Some network stuff

		jlStatus.setText("Waiting to push button");
	}

	private void clicCancel() {
		jlStatus.setText("Cancelling connect try...");
		jtfIp.setEnabled(true);
		jtfPort.setEnabled(true);

		// Some cancelling network stuff

		jlStatus.setText("Waiting to push button");
		jbConnect.setVisible(true);
		jbCancel.setVisible(false);
	}


	public static void main(String[] args) {
		new Deminator();
	}
}
