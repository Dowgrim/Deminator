package menu;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Michael on 29/04/2016.
 */
public class Accueil extends JFrame{

    public Accueil(){
        super("Deminator!!");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        add(new Buttonator("images\\Demineurs.jpg"));
        setVisible(true);
    }

    public static void main(String[] args) {
        new Accueil();
    }

}
