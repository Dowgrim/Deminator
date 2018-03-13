package dem.client;

import dem.client.model.ClientGrid;
import dem.client.net.action.AClientCmdReceive;
import dem.client.net.action.ClientCmdSender;
import dem.common.net.CmdSender;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ClientDem {
    private final Map<String, AClientCmdReceive> strToCmdClass = new HashMap<>();
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private boolean isConnected;
    private ClientGrid grid;
    private ClientCmdSender cmdSender;

    protected ClientDem() {
        initReceiveCmds();
    }

    private void initReceiveCmds() {
        // Registering commands to receive
        strToCmdClass.put("PING", new dem.client.net.action.receive.CmdReceivePing());
        strToCmdClass.put("ERR", new dem.client.net.action.receive.CmdReceiveErr());
        strToCmdClass.put("END", new dem.client.net.action.receive.CmdReceiveEnd());
        strToCmdClass.put("LAG", new dem.client.net.action.receive.CmdReceiveLag());
        strToCmdClass.put("QUIT", new dem.client.net.action.receive.CmdReceiveQuit());

        strToCmdClass.put("NEW", new dem.client.net.action.receive.settings.CmdReceiveNew());
        strToCmdClass.put("RDY", new dem.client.net.action.receive.settings.CmdReceiveRdy());
        strToCmdClass.put("LDD", new dem.client.net.action.receive.settings.CmdReceiveLdd());
        strToCmdClass.put("OPT", new dem.client.net.action.receive.settings.CmdReceiveOpt());

        strToCmdClass.put("DIS", new dem.client.net.action.receive.game.CmdReceiveDis());
        strToCmdClass.put("EXP", new dem.client.net.action.receive.game.CmdReceiveExp());
        strToCmdClass.put("MOV", new dem.client.net.action.receive.game.CmdReceiveMov());
        strToCmdClass.put("PTS", new dem.client.net.action.receive.game.CmdReceivePts());
        strToCmdClass.put("STN", new dem.client.net.action.receive.game.CmdReceiveStn());

        for(AClientCmdReceive cmd : strToCmdClass.values()) {
            cmd.setClientDem(this);
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

        disconnect();
    }

    public void receive(String ... params) {
        List<String> command = Arrays.asList(params);
        AClientCmdReceive cmdClass = strToCmdClass.get(command.remove(0));
        cmdClass.receive(command);
    }

    protected void connect(String ip, int port) throws IOException {
        disconnect();
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(this::listen).start();
    }

    public void disconnect() {
        isConnected = false;
        try{in.close();} catch(IOException ignored) {}
        out.close();
        try{socket.close();} catch(IOException ignored) {}
        socket = null;
    }

    public Writer getOutput() {
        return out;
    }

    public abstract void log(String s);

    public ClientGrid getGrid() {
        return grid;
    }

    public ClientCmdSender getCmdSender() {
        return cmdSender;
    }
}
