package dem.net.server.actions.receive.settings;

import dem.net.common.CmdReceiver;
import dem.net.server.actions.send.settings.CmdSendRdy;

import java.util.List;

/**
 * RDY isPlayerReady
 * received by SERVER
 */
public class CmdReceiveRdy extends CmdReceiver {
	@Override
	public void receive(List<String> params) {
		boolean isPlayerReady = Boolean.valueOf(params.remove(0).trim());
		String playerId = "TODO"; // TODO

		// TODO: if it has changed
		CmdSendRdy.send(playerId, isPlayerReady);
	}
}
