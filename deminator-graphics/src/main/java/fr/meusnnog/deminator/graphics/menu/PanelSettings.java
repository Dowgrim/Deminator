package fr.meusnnog.deminator.graphics.menu;

import javax.swing.*;
import java.awt.*;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public abstract class PanelSettings extends JPanel {

	protected JButton jbReady = new JButton("Ready ?!");


	public PanelSettings() {
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
		jpButtons.add(jbReady);
		jbReady.addActionListener(al -> clickReady());


		setLayout(new BorderLayout());
		add(jspSettings, BorderLayout.CENTER);
		add(jpButtons, BorderLayout.SOUTH);
	}

	public abstract void clickReady();
}
