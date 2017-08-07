package view.menu;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Nathaël N on 02/05/16.
 */
public class DeminatorFrame extends JFrame {
	private JPanel jpContent;
	private JSPPlayersList jpRight;

	public DeminatorFrame() {
		super("Deminator !!");

		// CONTENT
		JPanel jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());

		JScrollPane jpCenter = new JScrollPane();
		jpCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jpCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jpMain.add(jpCenter, BorderLayout.CENTER);

		JPanel jpp = new JPanel();
		jpp.setBackground(Color.GRAY);
		jpContent = new JPanel();
		jpContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		jpp.add(jpContent);
		jpCenter.setViewportView(jpp);

		jpRight = new JSPPlayersList();
		jpMain.add(jpRight, BorderLayout.EAST);

		// This frame
		setContentPane(jpMain);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
	}

	public void setView(JComponent jc) {
		jpContent.removeAll();
		jpContent.add(jc);
		jc.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.validate();
		this.repaint();
	}

	public void putPlayerToList(String playerName, Color playerColor) {
		jpRight.putPlayerToList(playerName, playerColor);
	}
	public void removePlayerFromList(String playerName) {
		jpRight.removePlayerFromList(playerName);
	}

	public static void main(String[] args) {
		new DeminatorFrame();
	}
}
