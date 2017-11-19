package dem.net.util;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;

import java.util.*;

public abstract class Communicator {
	private Set<String> emits = new HashSet<>();
	private Map<String, Receiver> receives = new HashMap<>();

	protected void newSendAction(Emitter emitter) {
		if(emits.contains(emitter.command))
			throw new RuntimeException("Already contains emit command: "+emitter.command);

		emits.add(emitter.command);
	}

	protected void newReceiveAction(String command, Receiver receiver) {
		if(receives.containsKey(command))
			throw new RuntimeException("Already contains receive command: "+command);

		receives.put(command, receiver);
	}

	public void send(Emitter emitter) {
		if(!emits.contains(emitter.command)) {
			throw new RuntimeException("Command not found: " + emitter.command);
		}

		emitter.send();
	}

	public void receive(String command, List<String> params) {
		if(!receives.containsKey(command)) {
			throw new RuntimeException("Command not found: "+command);
		}

		receives.get(command).receive(params);
	}
}
