package dem.server.net.action.receive.settings;

import dem.server.net.action.AServerCmdReceive;
import dem.server.model.ServerGrid;
import dem.server.net.action.ServerCmdSender;

import java.util.List;

/**
 * RDY isPlayerReady
 * received by SERVER
 */
public class CmdReceiveRdy extends AServerCmdReceive {
	@Override
	public void receive(ServerCmdSender cmdSender, ServerGrid grid, String playerName, List<String> params) {
		boolean isPlayerReady = Boolean.valueOf(params.remove(0).trim());
		String playerId = "TODO"; // TODO

		// TODO
	}
}
