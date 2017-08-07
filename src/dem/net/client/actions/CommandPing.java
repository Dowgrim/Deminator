package dem.net.client.actions;

import java.util.List;

public class CommandPing implements Emitter, Receiver {
	private long pingDate = -1;

	@Override
	public void send() {
		// prépare listener Pong
		pingDate = System.currentTimeMillis();

		// réseau.envoie('ping')
	}

	@Override
	public void receive(List<String> params) {
		// réseau.envoie('pong')
	}

	public static class Pong implements Receiver {
		CommandPing p;

		public Pong(CommandPing p) {
			this.p = p;
		}

		@Override
		public void receive(List<String> params) {
			long ms = System.currentTimeMillis() - p.pingDate;

			// faire la magie avec ms
		}
	}
}
