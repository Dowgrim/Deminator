package view.menu;

import server.Player;
import net.Server;
import util.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by Michael on 29/04/2016.
 */
public class PanelSettings extends JPanel {
	public enum Rank { CLIENT, SERVER }

	private final Controller control;
	private final JButton jbNotReady;
	private final JButton jbGo;
	private final JButton jbReady;
	private final JButton jbDisconnect;

	private Server server = null;

	public PanelSettings(Controller control)
	{
		this.control = control;
		JPanel jspSettings = new JPanel();
		jspSettings.setLayout(new BoxLayout(jspSettings, BoxLayout.Y_AXIS));

		JPanel jpMapDim = new JPanel();
		{
			JTextField jtfX = new JTextField("10", 3);
			{
				jtfX.setMaximumSize(new Dimension(30, jtfX.getPreferredSize().height));
			}
			JTextField jtfY = new JTextField("10", 3);
			{
				jtfY.setMaximumSize(new Dimension(30, jtfY.getPreferredSize().height));
			}

			jpMapDim.setAlignmentX( Component.LEFT_ALIGNMENT );
			jpMapDim.setLayout(new BoxLayout(jpMapDim, BoxLayout.X_AXIS));
			jpMapDim.add(new JLabel("Taille de la carte: x:"));

			jpMapDim.add(jtfX);
			jpMapDim.add(new JLabel(" y:"));

			jpMapDim.add(jtfY);
		}

		JPanel jpNbMines = new JPanel();
		{
			JTextField jtfMines = new JTextField("42", 3);
			{
				jtfMines.setMaximumSize(new Dimension(50, jtfMines.getPreferredSize().height));
			}

			jpNbMines.setAlignmentX( Component.LEFT_ALIGNMENT );
			jpNbMines.add(new JLabel("Nombre de mines: "));
			jpNbMines.add(jtfMines);
		}

		/////////////////////////////////////////////// HERE to add your own new parameter types

		jspSettings.add(jpMapDim);
		jspSettings.add(jpNbMines);

		JPanel jpButtons = new JPanel();
		{
			jbReady = new JButton("Ready !");
			{
				jbReady.addActionListener(al -> clicGetReady(true));
			}
			jbNotReady = new JButton("Plus Ready");
			{
				jbNotReady.addActionListener(al -> clicGetReady(false));
				jbNotReady.setVisible(false);
			}
			jbGo = new JButton("Everyone Ready, GO !");
			{
				jbGo.addActionListener(al -> clicGo());
			}
			jbDisconnect = new JButton("Disconnect");
			{
				jbDisconnect.addActionListener(al -> clicDisconnect());
			}

			jpButtons.add(jbReady);
			jpButtons.add(jbNotReady);
			jpButtons.add(jbGo);
			jpButtons.add(jbDisconnect);
		}

		setLayout(new BorderLayout());
		add(jspSettings, BorderLayout.CENTER);
		add(jpButtons, BorderLayout.SOUTH);
	}

	private void clicGo() {
		// TODO
	}

	private void clicGetReady(boolean isReady) {
		// TODO
	}

	private void clicDisconnect() {
		//control.disconnect();
	}


	public void reloadPlayer(HashMap<String, Player> players) {
	}
}
