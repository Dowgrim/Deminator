package dem.net.client.actions.sendAndRecieve;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;

import java.util.List;

public class CommandPing implements Emitter {
	private long pingDate = -1;

	@Override
	public String send() {
		// prépare listener Pong
		pingDate = System.currentTimeMillis();
        System.out.println("PING " + pingDate);
		return "PING " + pingDate;
	}

	public static class CommandPong extends Receiver {
		CommandPing p;

		public CommandPong(CommandPing p) {
			super(null);
			this.p = p;
		}

		@Override
		public void receive(List<String> params) {
			long ms = System.currentTimeMillis() - p.pingDate;

			System.out.println("PONG " + ms);
		}
	}
}
