package dem.net.server.actions.sendOnly;

import dem.net.util.actions.Emitter;

public class CommandQuit implements Emitter {
	@Override
	public String send() {
		// QUIT playerName
		return "";
	}
}