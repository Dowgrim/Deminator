package dem.net.server.actions;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandPing extends Receiver {
	private long pingDate = -1;
	CommandPong p;

	public CommandPing(Deminator d, CommandPong p) {
		super(d);
		this.p = p;
	}


	@Override
	public void receive(List<String> params) {
		// réseau.envoie('pong')
	}

	public static class CommandPong implements Emitter {


		@Override
		public void send() {

		}
	}
}
