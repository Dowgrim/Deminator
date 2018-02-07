package dem.net.util;

import dem.net.util.actions.Emitter;
import dem.net.util.actions.Receiver;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Communicator extends SockCom implements Runnable{
	private ComStatus comStatus;
	//private int state = 0;
	private boolean isListening = true;

	private Thread t;

	public Communicator(String host, int port, ComStatus comStatus) throws IOException {
		super(host, port);
		init(comStatus);
}

	public Communicator(Socket s, ComStatus comStatus) throws IOException {
		super(s);
		init(comStatus);
	}

	private void init(ComStatus comStatus){
        System.out.println("init");
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
        System.out.println("In Send");
		this.send(emitter.send());
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
	    t.start();
    }

	@Override
	public void run() {
		List<String> params;
		String command;
		while(isListening) {
            System.out.println("Listen");
			if((params = super.receive()) != null){
                System.out.println("RECEIVE!!!");
				command = params.remove(0);
				this.receive(command, params);
			}
			else{
				System.err.println("Error : Receiving message");
			}
		}
	}
}
