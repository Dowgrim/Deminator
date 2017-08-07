package dem.view.game;

import dem.util.JPImage;

import java.awt.GridLayout;

import javax.swing.*;

import dem.net.client.ClientDem;

/**
 * Created by Michael on 16/06/2016.
 */
public class Deminator extends JFrame{
	
    private static String BLUE = "images/Blue.png";
    private static String GREEN = "images/Green.png";
    private static String EXPLO = "images/Explosion.png";

    private ClientDem cli;
    
    private final JPanel contentPane;

    private JPImage[][] boxs;

    public Deminator(ClientDem c, int sizeX, int sizeY){
    	cli = c;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //TODO en fonction de la taille de l'ecran
        setSize(sizeX*30, sizeY*30);

        boxs = new JPImage[sizeX][sizeY];
        contentPane = new JPanel();
        setContentPane(contentPane);

        contentPane.setLayout(new GridLayout(sizeX, sizeY));
        JPImage p;
        for(int i = 0; i < sizeX; i++){
        	for(int j = 0; j < sizeY; j++){
	            p = new JPImage();
	            boxs[i][j] = p;
	            contentPane.add(p);
        	}
        }
        boxs[0][0].addImage(BLUE);
        
        
        try {
            Thread.sleep(333333333);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addKeyListener(new PlayerListener(cli));

        setVisible(true);
    }



}
