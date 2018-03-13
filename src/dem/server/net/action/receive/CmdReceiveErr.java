package dem.server.net.action.receive;

import dem.server.net.action.AServerCmdReceive;
import dem.server.model.ServerGrid;
import dem.server.net.action.ServerCmdSender;

import java.util.Arrays;
import java.util.List;

public class CmdReceiveErr extends AServerCmdReceive {
	@Override
	public void receive(ServerCmdSender cmdSender, ServerGrid grid, String playerName, List<String> params) {
		System.err.println("ERROR received from client: "+Arrays.toString(params.toArray()));
	}
}
