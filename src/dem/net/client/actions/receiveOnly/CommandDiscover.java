package dem.net.client.actions.receiveOnly;

import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandDiscover extends Receiver {
	public CommandDiscover(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// DIS x y value
		int x = Integer.parseInt(params.remove(0));
		int y = Integer.parseInt(params.remove(0));
		int value = Integer.parseInt(params.remove(0));

		// TODO
	}
}