package fr.meusnnog.deminator.net.communication;

import fr.meusnnog.deminator.net.DemEmitter;

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

	public Communicator(String host, int port, ComStatus comStatus) throws IOException {
		super(host, port);
		init(comStatus);
	}

	public Communicator(Socket s, ComStatus comStatus) throws IOException {
		super(s);
		init(comStatus);
	}

	private void init(ComStatus comStatus) {
		this.comStatus = comStatus;

		t = new Thread(this);
		t.start();
	}

	public void setComStatus(ComStatus newComStatus) {
		comStatus = newComStatus;
	}

	public void send(DemEmitter demEmitter) {
		this.send(demEmitter.buildCommand());
	}

	public void receive(String command, List<String> params) {
		if (!comStatus.receivers.containsKey(command)) {
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
		System.out.println("Communicator running.");
		List<String> params;
		String command;
		while (isListening) {
			System.out.println("Communicator listening...");
			if ((params = super.receive()) != null) {
				System.out.println("Communicator received: '" + String.join(" ", params) + "'");
				command = params.remove(0);
				this.receive(command, params);
			} else {
				System.err.println("Error : Receiving message null");
				break;
			}
		}
		System.out.println("Communicator no more running.");
	}
}
