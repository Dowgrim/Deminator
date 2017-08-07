package dem.view.game;

import dem.net.client.ClientDem;

import javax.swing.*;
import java.awt.*;

public class PanelInfos extends JPanel {

    private ClientDem cli;

    public PanelInfos(ClientDem c){
        cli = c;
         setBorder(BorderFactory.createEtchedBorder());

        //TODO en fonction de la taille de l'écran
        setMinimumSize(new Dimension(150, (int)getMinimumSize().getHeight()));
        setPreferredSize(new Dimension(150, (int)getMinimumSize().getHeight()));
        setMaximumSize(new Dimension(150, 1000));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new Button("Ping"));
        JPanel size = new JPanel();
        size.setLayout(new FlowLayout());
        size.add(new JLabel("x:10"));
        size.add(new JLabel("y:10"));
        add(size);

    }
}
