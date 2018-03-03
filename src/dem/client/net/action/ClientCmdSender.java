package dem.client.net.action;

import dem.common.net.CmdSender;

public abstract class ClientCmdSender extends CmdSender {

	private static void sendToServer(String cmdId, String ... parameters) {
		// send(buildCommand(cmdId, parameters), TODO);
	}

	public static void error(String code, String details) {
		sendToServer("ERR", code, details);
	}

	public static void pong() {
		sendToServer("PONG");
	}

	public static void cmdNew(String playerName, int colorHue) {
		sendToServer("NEW", playerName, Integer.toString(colorHue));
	}

	public static void rdy(boolean isReady) {
		sendToServer("PONG", isReady?"Y":"N");
	}
}
