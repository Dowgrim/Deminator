package dem.common.net;

import java.io.IOException;
import java.io.Writer;

public abstract class CmdSender {
	public static final String SEPARATOR = " ";

	protected static void send(String cmd, Writer receiver) {
        try {
            receiver.write(cmd);
            receiver.flush();
        } catch (IOException e) {
            throw new RuntimeException("FAIL in the code !!");
        }
    }

	protected static String buildCommand(String cmdId, String ... parameters) {
		/* TODO error if cmdId or any parameters contains SEPARATOR */
		return cmdId + SEPARATOR + String.join(SEPARATOR, parameters);
	}
}
