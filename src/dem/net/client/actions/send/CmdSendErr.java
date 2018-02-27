package dem.net.client.actions.send;

public class CmdSendErr extends CmdSendToServer {
	private static final String IDENTIFIER = "ERR";

	public static void send(String code, String details) {
		sendToServer(IDENTIFIER, code, details);
	}
}
