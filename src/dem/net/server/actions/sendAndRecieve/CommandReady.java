package dem.net.server.actions.sendAndRecieve;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
		// RDY true/false
		boolean isReady = Boolean.parseBoolean(params.remove(0));

		// TODO
	}

	@Override
	public void send() {
		// RDY playerName true/false

		// TODO
	}
}
