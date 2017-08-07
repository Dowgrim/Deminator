package dem.net.client.actions.receiveOnly;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandStun extends Receiver {
	public CommandStun(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// STUN playerName time
		String playerName = params.remove(0);
		int time = Integer.parseInt(params.remove(0));

		// TODO
	}
}