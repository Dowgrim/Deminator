package dem.net.client.actions;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandPing implements Emitter {
	private long pingDate = -1;


	@Override
	public String send() {
		// prépare listener Pong
		pingDate = System.currentTimeMillis();

		return "PING " + pingDate;
	}

	public static class CommandPong extends Receiver {
		CommandPing p;

		public CommandPong(Deminator d, CommandPing p) {
			super(d);
			this.p = p;
		}

		@Override
		public void receive(List<String> params) {
			long ms = System.currentTimeMillis() - p.pingDate;

			dem.displayPing(ms);
		}
	}
}
