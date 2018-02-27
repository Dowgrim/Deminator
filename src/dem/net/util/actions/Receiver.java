package dem.net.util.actions;

import dem.view.game.Deminator;

import java.util.List;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public abstract class Receiver {

	public String command;

	protected Deminator dem;

	protected Receiver(Deminator dem, String command) {
		this.dem = dem;
		this.command = command;
	}

	public abstract void receive(List<String> params);
}
