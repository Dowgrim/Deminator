package dem.net.server.actions.sendAndRecieve;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandMove extends Receiver implements Emitter {
	public CommandMove(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// MOV direction
		char direction = params.remove(0).trim().charAt(0); // 'N' 'S' 'E' 'W'

		// TODO
	}

	@Override
	public String send() {
		return null;
	}
}
