package dem.net.client.actions;

import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandNew extends Receiver {
	public CommandNew(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {

	}
}
