package dem.net.util;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ComStatus {
	public final Set<String> emits = new HashSet<>();
	public final Map<String, Receiver> receivers = new HashMap<>();

	protected void newSendAction(Emitter emitter) {
		emits.add(emitter.command);
	}

	protected void newReceiveAction(Receiver receiver) {
		receivers.put(receiver.command, receiver);
	}
}
