package dem.net.client.actions.receiveOnly;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandExplose extends Receiver {
	public CommandExplose(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// EXP x y
		int x = Integer.parseInt(params.remove(0));
		int y = Integer.parseInt(params.remove(0));

		// TODO
	}
}