package menu;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Nathaël N on 01/05/16.
 */
public class JPPlayer extends JPanel {
	public JPPlayer(String name, Color c) {

		JLabel jln = new JLabel(name);

		double lum = 0.0722*c.getBlue()+0.7152*c.getGreen()+0.2126*c.getRed();

		if(lum > 128)
			jln.setForeground(c.darker());
		else
			jln.setForeground(c.brighter());


		add(jln);
		setBackground(c);
	}
}
