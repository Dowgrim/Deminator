package common.menu;

import util.JPImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Michael on 29/04/2016.
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
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("yolo");
            }
        });
    }
}
