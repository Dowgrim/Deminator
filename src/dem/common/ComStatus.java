package dem.common;

import dem.common.net.actions.Emitter;
import dem.common.net.actions.Receiver;

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
