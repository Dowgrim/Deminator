package dem.server.net.action.receive;

import dem.common.net.CmdReceiver;
import dem.server.model.Grid;
import dem.server.model.Player;
import dem.server.net.action.ServerCmdSender;

import java.util.Arrays;
import java.util.List;

public class CmdReceiveErr extends CmdReceiver {
	@Override
	public void receive(ServerCmdSender cmdSender, Grid grid, Player emitter, List<String> params) {
		System.err.println("ERROR received from client: "+Arrays.toString(params.toArray()));
	}
}
