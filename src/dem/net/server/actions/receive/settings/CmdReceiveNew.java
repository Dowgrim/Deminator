package dem.net.server.actions.receive.settings;

import dem.net.common.CmdReceiver;

import java.util.List;

/**
 * NEW playerName
 * received by SERVER
 */
public class CmdReceiveNew extends CmdReceiver {
	@Override
	public void receive(List<String> params) {
		String playerName = params.remove(0).trim();

		if(playerName.isEmpty()) {
			// TODO error
		}

		// TODO check that no other players has the same
		// TODO in that case ERROR
		// TODO else instantiate the new player
	}
}
