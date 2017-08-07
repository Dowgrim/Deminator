package dem.net.client.actions.receiveOnly;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandLaunch extends Receiver {
	public CommandLaunch(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// LDD name x y shields
		String playerName = params.remove(0);
		int x = Integer.parseInt(params.remove(0));
		int y = Integer.parseInt(params.remove(0));
		int shields = Integer.parseInt(params.remove(0));

		// TODO
	}
}