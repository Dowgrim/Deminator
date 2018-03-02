package dem.server.net.action.receive.settings;

import dem.common.net.CmdReceiver;
import dem.server.model.Grid;
import dem.server.model.Player;
import dem.server.net.action.ServerCmdSender;

import java.util.List;

/**
 * RDY isPlayerReady
 * received by SERVER
 */
public class CmdReceiveRdy extends CmdReceiver {
	@Override
	public void receive(ServerCmdSender cmdSender, Grid grid, Player emitter, List<String> params) {
		boolean isPlayerReady = Boolean.valueOf(params.remove(0).trim());
		String playerId = "TODO"; // TODO

		// TODO
	}
}
