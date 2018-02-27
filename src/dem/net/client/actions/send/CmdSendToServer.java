package dem.net.client.actions.send;

public abstract class CmdSendToServer {
	private static final String SEPARATOR = " ";

	protected static void sendToServer(String cmdId, String ... parameters) {
		String command = buildCommand(cmdId, parameters);
		/* TODO do send message trough network here */
	}

	private static String buildCommand(String cmdId, String ... parameters) {
		return cmdId + SEPARATOR + String.join(SEPARATOR, parameters);
	}
}
