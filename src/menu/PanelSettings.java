package menu;

import client.view.DeminatorGame;
import common.DeminatorFrame;
import common.JSPPlayersList;
import server.Player;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by Michael on 29/04/2016.
 */
public class PanelSettings extends JPanel {

	private final DeminatorFrame papa;
	private final JButton jbNotReady;
	private final JButton jbGo;
	private final JButton jbReady;
	private final JButton jbDisconnect;

	private Server server = null;

	public PanelSettings(DeminatorFrame papa){
		this(papa, null);
	}
	public PanelSettings(DeminatorFrame papa, Server serv) {
		super();
		server = serv;
		this.papa = papa;
		JScrollPane jspPlayers = new JSPPlayersList();

		JComponent jspSettings = initSettingsPanel();

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
		add(jspPlayers, BorderLayout.EAST);
		add(jspSettings, BorderLayout.CENTER);
		add(jpButtons, BorderLayout.SOUTH);
	}

	private JComponent initSettingsPanel() {
		JScrollPane jspSettings = new JScrollPane();
		{
			JPanel viewport = new JPanel();
			jspSettings.setViewportView(viewport);
			viewport.setLayout(new BoxLayout(viewport, BoxLayout.Y_AXIS));

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

			viewport.add(jpMapDim);
			viewport.add(jpNbMines);
		}

		return jspSettings;
	}

	private void clicGo() {
		// TODO
	}

	private void clicGetReady(boolean isReady) {
		// TODO
	}

	private void clicDisconnect() {
		papa.setDefaultView();
		this.setVisible(false);
	}


	public void reloadPlayer(HashMap<String, Player> players) {
	}
}
