package view.game;

import util.Controller;
import util.JPImage;

import javax.swing.*;
import java.awt.event.KeyListener;

/**
 * Created by Michael on 16/06/2016.
 */
public class Deminator extends JFrame{

    private Controller controller;

    private JPImage[][] boxs;

    public Deminator(Controller c, int sizeX, int sizeY){
        controller = c;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //TODO en fonction de la taille de l'ecran
        setSize(sizeX*30, sizeY*30);

        boxs = new JPImage[sizeX][sizeY];

        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                boxs[i][j] = new JPImage("Yolo, trouver des images cool");
            }
        }

        addKeyListener(new PlayerListerner());

        setVisible(true);
    }



}
