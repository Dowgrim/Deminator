package dem.server.net.action.receive.settings;

import dem.server.net.action.AServerCmdReceive;
import dem.server.model.ServerGrid;
import dem.server.net.action.ServerCmdSender;

import java.util.List;

/**
 * NEW playerName
 * received by SERVER
 */
public class CmdReceiveNew extends AServerCmdReceive {
	@Override
	public void receive(ServerCmdSender cmdSender, ServerGrid grid, String playerName, List<String> params) {
		if(params.size() != 2) {
			// TODO error
			throw new RuntimeException();
		}

		String playerName = params.remove(0);
		int colorHue = Integer.parseInt(params.remove(0));

		if(playerName.isEmpty()) {
			// TODO error
			throw new RuntimeException();
		}
		if(colorHue < 0 || colorHue >= 365) {
			// TODO error
			throw new RuntimeException();
		}

		// TODO check that no other players has the same name or same color
		// TODO in that case ERROR

		// TODO instantiate the new player
	}
}
