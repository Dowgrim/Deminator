package dem.net.client.actions.receiveOnly;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandOption extends Receiver {
	public CommandOption(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// OPT option value
		String opt = params.remove(0);
		String val = params.remove(0);

		// TODO
	}
}