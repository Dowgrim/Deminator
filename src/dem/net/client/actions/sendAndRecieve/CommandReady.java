package dem.net.client.actions.sendAndRecieve;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandReady extends Receiver implements Emitter {
	public CommandReady(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// RDY playerName true/false
		String playerName = params.remove(0);
		boolean isReady = Boolean.parseBoolean(params.remove(0));

		// TODO
	}

	@Override
	public String send() {
		return null;
	}
}
