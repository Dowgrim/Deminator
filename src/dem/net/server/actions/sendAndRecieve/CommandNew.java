package dem.net.server.actions.sendAndRecieve;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandNew extends Receiver implements Emitter {
	public CommandNew(Deminator d) {
		super(d);
	}

	@Override
	public void receive(List<String> params) {
		// NEW name rrr ggg bbb
		String name = params.remove(0);
		int r = Integer.parseInt(params.remove(0));
		int g = Integer.parseInt(params.remove(0));
		int b = Integer.parseInt(params.remove(0));

		// TODO
	}

	@Override
	public String send() {
		// NEW name rrr ggg bbb
		// TODO
        return null;
    }
}
