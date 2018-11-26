package fr.meusnnog.deminator.graphics.menu;

import javax.swing.*;
import java.awt.*;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public class PanelSettings extends JPanel {
	public enum Rank {CLIENT, SERVER}

	private JButton jbNotReady;
	private JButton jbGo;
	private JButton jbReady;

	private Rank rank;


	public PanelSettings(Rank r) {
		rank = r;

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

			jpMapDim.setAlignmentX(Component.LEFT_ALIGNMENT);
			jpMapDim.setLayout(new BoxLayout(jpMapDim, BoxLayout.X_AXIS));
			jpMapDim.add(new JLabel("Map size: x:"));

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

			jpNbMines.setAlignmentX(Component.LEFT_ALIGNMENT);
			jpNbMines.add(new JLabel("mines numbers: "));
			jpNbMines.add(jtfMines);
		}

		/////////////////////////////////////////////// HERE to add your own new parameter types

		jspSettings.add(jpMapDim);
		jspSettings.add(jpNbMines);

		JPanel jpButtons = new JPanel();
		{
			if(rank == Rank.CLIENT) {
				jbReady = new JButton("Ready !");
				{
					jbReady.addActionListener(al -> clicGetReady(true));
				}
				jbNotReady = new JButton("Not Ready");
				{
					jbNotReady.addActionListener(al -> clicGetReady(false));
					jbNotReady.setVisible(false);
				}
				jpButtons.add(jbReady);
				jpButtons.add(jbNotReady);
			}
			if(rank == Rank.SERVER) {
				jbGo = new JButton("Everyone Ready, GO !");
				{
					jbGo.addActionListener(al -> clicGo());
				}
				jpButtons.add(jbGo);
			}
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

}
