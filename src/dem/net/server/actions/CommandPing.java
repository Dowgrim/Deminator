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
        long ms = System.currentTimeMillis() - p.pongDate;

        dem.displayPing(ms);
	}

	public static class CommandPong implements Emitter {
        long pongDate;

		@Override
		public String send() {
			long pongDate = System.currentTimeMillis();

			return "PONG " + pongDate;
		}
	}
}
