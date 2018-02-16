package dem.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public class Buttonator extends JPImage {

    private JButton btn;

    public Buttonator(final String ... adrs){
        super(adrs);
        setLayout(new GridLayout(1, 1));
        setBounds(100, 100, 100, 100);
        btn = new JButton();
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        add(btn);
        btn.addActionListener(e->System.out.println("yolo"));
    }
}
