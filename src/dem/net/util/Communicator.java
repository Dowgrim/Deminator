package dem.net.util;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;

import java.io.IOException;
import java.util.*;

public class Communicator extends SockCom implements Runnable{
	private Set<String> emits = new HashSet<>();
	private Map<String, Receiver> receives = new HashMap<>();
	private boolean isListening = true;

	private Thread t;

	public Communicator(String host, int port) throws IOException {
		super(host, port);

		t = new Thread(this);
		t.start();
	}

	public void setCommunicationStatus(Emitter emitter) {
		if(emits.contains(emitter.command))
			throw new RuntimeException("Already contains emit command: "+emitter.command);

		emits.add(emitter.command);
	}

	public void newReceiveAction(String command, Receiver receiver) {
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

	public void stop() {
		isListening = false;
	}

	public void restar() {
	    isListening = true;

    }

	@Override
	public void run() {
		List<String> params;
		String command;
		while(isListening) {
			if((params = super.receive()) != null){
				command = params.remove(0);
				this.receive(command, params);
			}
			else{
				System.err.println("Error : Receiving message");
			}
		}
	}
}
