package dem.server;

import dem.common.net.CmdSender;
import dem.server.model.Grid;
import dem.server.net.ClientCmdReceiver;
import dem.server.net.action.ServerCmdSender;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerDem {
	private ServerSocket serverSocket = null;
	private final ServerCmdSender serverCmdSender = new ServerCmdSender(this);
	private final List<ClientCmdReceiver> clients = new ArrayList<>();
	private int maxClients = 0;
	private boolean acceptingClients = false;
	private final Thread connectLoopThread = new Thread(this::connectLoop);
	private Grid grid = null;

	protected void openServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	protected void setMaxClients(int maxClients) {
		this.maxClients = maxClients;
	}

	protected void acceptClients(boolean isAccepting) throws IOException {
		if(isAccepting == acceptingClients) {
			return;
		}

		if(isAccepting) {
			connectLoopThread.start();
		} else {
			// make currently running "accept" methods
			// (actively waiting for new connexions)
			// throwing IO exception
			serverSocket.close();
		}
	}

	private void connectLoop() {
		if(acceptingClients) {
			return; // another thread is already running
		}

		acceptingClients = true;
		while(acceptingNewClients()) {
			try {
				ClientCmdReceiver newClient = new ClientCmdReceiver(this, serverSocket.accept());
				if(!acceptingNewClients()) {
					break;
				}
				clients.add(newClient);
			} catch(IOException e) {
				System.out.println("Server socket does not accept new connexions anymore");
				break;
			}
		}
		acceptingClients = false;
	}

	private boolean acceptingNewClients() {
		return acceptingClients && clients.size() < maxClients;
	}

	public void disconnect(ClientCmdReceiver client) {
		client.disconnect();
		clients.remove(client);
	}

	public ServerCmdSender getCmdSender() {
		return serverCmdSender;
	}
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	public Grid getGrid() {
		return grid;
	}
}
