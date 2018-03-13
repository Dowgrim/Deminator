package dem.server.net;

import dem.server.net.action.AServerCmdReceive;
import dem.common.net.CmdSender;
import dem.server.ServerDem;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientCmdReceiver {
	private static int lastReceiverId = 0;

	private final int receiverId = lastReceiverId++;
	private final Map<String, AServerCmdReceive> strToCmdClass = new HashMap<>();
	private final PrintWriter out;
	private final BufferedReader in;
	private final Socket socket;
	private final ServerDem server;
	private boolean isConnected;

	public ClientCmdReceiver(ServerDem server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		new Thread(this::listen).start();
	}

	private void initReceiveCmds() {
		// Registering commands to receive
		strToCmdClass.put("PONG", new dem.server.net.action.receive.CmdReceivePong());
		strToCmdClass.put("ERR", new dem.server.net.action.receive.CmdReceiveErr());

		strToCmdClass.put("NEW", new dem.server.net.action.receive.settings.CmdReceiveNew());
		strToCmdClass.put("RDY", new dem.server.net.action.receive.settings.CmdReceiveRdy());

		strToCmdClass.put("MOV", new dem.server.net.action.receive.game.CmdReceiveMov());

		for(AServerCmdReceive cmd : strToCmdClass.values()) {
			cmd.setServer(server);
		}
	}

	private void listen() {
		isConnected = true;
		try {
			while(isConnected) {
				String received = in.readLine();
				this.receive(received.split(CmdSender.SEPARATOR));
			}
		} catch(IOException ignored) {}

		server.disconnect(this);
	}

	public void receive(String ... params) {
		List<String> command = Arrays.asList(params);
		AServerCmdReceive cmdClass = strToCmdClass.get(command.remove(0));
		cmdClass.receive(receiverId, command);
	}

	public void disconnect() {
		isConnected = false;
		try{in.close();} catch(IOException ignored) {}
		out.close();
		try{socket.close();} catch(IOException ignored) {}
	}

	public int getId() {
		return receiverId;
	}

    public Writer getOutput() {
        return out;
    }
}
