package dem.net.client.actions.sendAndRecieve;

import dem.net.util.actions.Receiver;
import dem.net.util.actions.Emitter;
import dem.view.game.Deminator;

import java.util.List;

public class CommandMove extends Receiver implements Emitter {
	public CommandMove(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// MOV name x y
		String playerName = params.remove(0);
		int x = Integer.parseInt(params.remove(0));
		int y = Integer.parseInt(params.remove(0));

		// TODO
	}

	@Override
	public void send() {
		// MOV direction

		// TODO
	}
}
