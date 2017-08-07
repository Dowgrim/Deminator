package dem.net.client.actions.receiveOnly;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandPoints extends Receiver {
	public CommandPoints(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// PTS playerName nbPts
		String playerName = params.remove(0);
		int pts = Integer.parseInt(params.remove(0));

		// TODO
	}
}