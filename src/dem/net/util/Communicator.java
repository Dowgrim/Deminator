package dem.net.util;

import dem.net.client.ComPing;
import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;

import java.io.IOException;
import java.util.*;

public class Communicator extends SockCom implements Runnable{
	private ComStatus comStatus;
	//private int state = 0;
	private boolean isListening = true;

	private Thread t;

	public Communicator(String host, int port, ComStatus comStatus) throws IOException {
		super(host, port);

		this.comStatus = comStatus;

		t = new Thread(this);
		t.start();
	}

	public void setComStatus(ComStatus newComStatus){
	    comStatus = newComStatus;
    }

	public void send(Emitter emitter) {
		if(!comStatus.emits.contains(emitter.command)) {
			throw new RuntimeException("Command not found: " + emitter.command);
		}

		emitter.send();
	}

	public void receive(String command, List<String> params) {
		if(!comStatus.receivers.containsKey(command)) {
			throw new RuntimeException("Command not found: "+command);
		}
		comStatus.receivers.get(command).receive(params);
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
