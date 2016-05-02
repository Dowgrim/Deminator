package client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Nathaël N on 01/05/16.
 */
public class DeminatorGame extends JFrame {

	private JPanel jpContent;
	private JSPPlayersList jpRight;

	private DeminatorGame() {
		super("JPClientConnect !!");

		// CONTENT
		JPanel jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());

		JScrollPane jpCenter = new JScrollPane();
		jpCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jpCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jpMain.add(jpCenter, BorderLayout.CENTER);

		jpContent = new JPanel();
		jpCenter.setViewportView(jpContent);
		jpContent.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		jpRight = new JSPPlayersList();
		jpMain.add(jpRight, BorderLayout.EAST);

		setView(new JPClientConnect());

		// This frame
		setContentPane(jpMain);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}

	public void setView(JComponent jc) {
		jpContent.removeAll();
		jpContent.add(jc);
	}


	// CLIENT MAIN
	public static void main(String[] args) {
		new DeminatorGame();
	}
}
