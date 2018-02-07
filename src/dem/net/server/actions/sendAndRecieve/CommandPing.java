package dem.net.server.actions.sendAndRecieve;

import dem.model.Player;
import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;
import dem.view.game.Deminator;

import java.util.List;

public class CommandPing extends Receiver {
	private long pingDate = -1;
	Player p;

	public CommandPing(Player p) {
		super(null);
		this.p = p;
	}

	@Override
	public void receive(List<String> params) {
        p.send(new CommandPong());
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
