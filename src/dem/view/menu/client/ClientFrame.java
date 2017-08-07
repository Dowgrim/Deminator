package dem.view.menu.client;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dem.net.client.ClientDem;
import dem.view.menu.JSPPlayersList;
import dem.view.menu.PanelSettings;

public class ClientFrame extends JFrame {
	
	private ClientDem cli;
	
	private final JPanel mainPanel;
	
	private final JPanel pCenter;
	
	private final PanelConnexion pc;
	
	private final PanelSettings ps;
	
	private final JSPPlayersList sppl;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5930605691302577354L;
	
	
	public ClientFrame() {
		super("Deminator !!(Client)"); 
		cli = new ClientDem();
		mainPanel = new JPanel(); 
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		setContentPane(mainPanel);

		pCenter = new JPanel();	
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));

		pc = new PanelConnexion(cli);
		
		ps = new PanelSettings();
		
		sppl = new JSPPlayersList();
			
		pCenter.add(pc);
		pCenter.add(ps);
		
		mainPanel.add(pCenter);
		mainPanel.add(sppl);

		pcInitialisation();
		psInitialisation();
		spplInitialisation();
		
		setSize(600, 600);
		setVisible(true);
	}
	
	private void pcInitialisation() {
		pc.setBorder(new EmptyBorder(10, 10, 10, 10));
		pc.setBounds(0, 0, 0, 150);
		pc.setMaximumSize(new Dimension(2000,250));
	}
	
	private void psInitialisation() {
		
	}
	
	private void spplInitialisation() {
		sppl.setBounds(mainPanel.getWidth() - 300, 0, 300, mainPanel.getHeight());
	}
	
	
	public static void main(String[] args) {
		new ClientFrame();
	}

}
