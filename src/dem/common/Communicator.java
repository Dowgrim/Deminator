package dem.common;

import dem.common.net.Emitter;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public class Communicator extends SockCom implements Runnable {
	private ComStatus comStatus;
	//private int state = 0;
	private boolean isListening = true;

	private Thread t;

	public Communicator(String host, int port) throws IOException {
		super(host, port);
		init();
	}

	public Communicator(Socket s) throws IOException {
		super(s);
		init();
	}

	private void init() {
		System.out.println("init");

		t = new Thread(this);
		t.start();
	}

	public void setComStatus(ComStatus newComStatus) {
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
			throw new RuntimeException("Command not found: " + command);
		}
		comStatus.receivers.get(command).receive(params);
	}

	public void stop() {
		isListening = false;
	}

	public void restart() {
		isListening = true;
		t.start();
	}

	@Override
	public void run() {
		List<String> params;
		String command;
		while(isListening) {
			System.out.println("Listen");
			if((params = super.receive()) != null) {
				System.out.println("RECEIVE!!!");
				command = params.remove(0);
				this.receive(command, params);
			} else {
				System.err.println("Error : Receiving message");
			}
		}
	}
}
