package dem.view.game;

import dem.util.JPImage;

import java.awt.GridLayout;

import javax.swing.*;

import dem.net.client.ClientDem;

/**
 * Created by Michael on 16/06/2016.
 */
public class Deminator extends JFrame {

	private static final String BLUE = "images/Blue.png";
	private static final String GREEN = "images/Green.png";
	private static final String EXPLO = "images/Explosion.png";

	private ClientDem cli;

	private final JPanel contentPane;

	private final JPanel gridPane;
	private final PanelInfos infosPane;

	private JPImage[][] boxs;

	public Deminator(ClientDem c, int sizeX, int sizeY) {
		cli = c;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//TODO en fonction de la taille de l'ecran
		setSize((sizeX * 50) + 150, sizeY * 50);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		boxs = new JPImage[sizeX][sizeY];
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		gridPane = new JPanel();
		infosPane = new PanelInfos(cli);

		contentPane.add(gridPane);
		contentPane.add(infosPane);

		gridPane.setLayout(new GridLayout(sizeX, sizeY));
		JPImage p;
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j++) {
				p = new JPImage();
				boxs[i][j] = p;
				gridPane.add(p);
			}
		}
		boxs[0][0].addImage(BLUE);


		addKeyListener(new PlayerListener(cli));

		setVisible(true);
	}

	public void displayPing(long ms) {
		infosPane.setPing(ms);
	}
}
