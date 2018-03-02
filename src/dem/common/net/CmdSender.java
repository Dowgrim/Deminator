package dem.common.net;

public abstract class CmdSender {
	public static final String SEPARATOR = " ";

	protected static void send(String cmd, Object receiver) {
		/* TODO do send message trough network here */
	}

	protected static String buildCommand(String cmdId, String ... parameters) {
		/* TODO error if cmdId or any parameters comtains SEPARATOR */
		return cmdId + SEPARATOR + String.join(SEPARATOR, parameters);
	}
}
