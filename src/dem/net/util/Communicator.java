package dem.net.util;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Communicator {
	private Map<String, Emitter> emits = new HashMap<>();
	private Map<String, Receiver> receives = new HashMap<>();

	protected void newSendAction(String command, Emitter emitter) {
		if(emits.containsKey(command))
			throw new RuntimeException("Already contains emit command: "+command);

		emits.put(command, emitter);
	}

	protected void newReceiveAction(String command, Receiver receiver) {
		if(receives.containsKey(command))
			throw new RuntimeException("Already contains receive command: "+command);

		receives.put(command, receiver);
	}

	public void send(String command) {
		if(!emits.containsKey(command)) {
			throw new RuntimeException("Command not found: "+command);
		}

		emits.get(command).send();
	}

	public void receive(String command, List<String> params) {
		if(!receives.containsKey(command)) {
			throw new RuntimeException("Command not found: "+command);
		}

		receives.get(command).receive(params);
	}
}
