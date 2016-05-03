package common.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Nathaël N on 02/05/16.
 */
public abstract class DeminatorFrame extends JFrame {
	private JPanel jpContent;
	private JSPPlayersList jpRight;

	protected DeminatorFrame(String title) {
		super(title);

		// CONTENT
		JPanel jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());

		JScrollPane jpCenter = new JScrollPane();
		jpCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jpCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jpMain.add(jpCenter, BorderLayout.CENTER);

		jpContent = new JPanel();
		jpContent.setBackground(Color.GRAY);
		jpCenter.setViewportView(jpContent);

		jpRight = new JSPPlayersList();
		jpMain.add(jpRight, BorderLayout.EAST);

		// This frame
		setContentPane(jpMain);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showConfirmDialog(
						DeminatorFrame.this,
						"Do you really want to close the server?",
						"Close?",
						JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.YES_OPTION){
					Object[] options = {"OSEF",
							"YOLO"};
					n = JOptionPane.showOptionDialog(DeminatorFrame.this,
							"Nooo!!Don't do that otherwise this is what will happen: The time and space will break.",
							"Don't do that!",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null, options, options[0]);
					//TODO
				}
			}

			@Override public void windowOpened(WindowEvent e) {}
			@Override public void windowClosed(WindowEvent e) {}
			@Override public void windowIconified(WindowEvent e) {}
			@Override public void windowDeiconified(WindowEvent e) {}
			@Override public void windowActivated(WindowEvent e) {}
			@Override public void windowDeactivated(WindowEvent e) {}
		});
	}

	public abstract void setDefaultView();

	public void setView(JComponent jc) {
		jpContent.removeAll();
		jpContent.add(jc);
		this.validate();
		this.repaint();
	}

	public void putPlayerToList(String playerName, Color playerColor) {
		jpRight.putPlayerToList(playerName, playerColor);
	}
	public void removePlayerFromList(String playerName) {
		jpRight.removePlayerFromList(playerName);
	}
}
