package menu;

import javax.swing.*;

/**
 * Created by Michael on 29/04/2016.
 */
public class Welcome extends JFrame{

    public Welcome(){
        super("Deminator!!");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        add(new Buttonator("images/Demineurs.jpg"));
        setVisible(true);
    }

    public static void main(String[] args) {
        new Welcome();
    }

}
