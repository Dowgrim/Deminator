package fr.meusnnog.deminator.net;

import javax.swing.*;
import java.util.List;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public abstract class DemReceiver<T extends JFrame> {
	protected final T frame;

	public DemReceiver(T f) {
		frame = f;
	}

	public abstract void receive(List<String> params);

	public abstract String getCommandName();
}
