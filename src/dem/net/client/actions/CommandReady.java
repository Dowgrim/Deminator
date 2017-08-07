package dem.net.client.actions;

import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandReady extends Receiver {
	public CommandReady(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {

	}
}
