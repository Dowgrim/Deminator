package dem.server.net.action.receive;

import dem.server.net.action.AServerCmdReceive;
import dem.server.model.ServerGrid;
import dem.server.net.action.ServerCmdSender;

import java.util.List;

public class CmdReceivePong extends AServerCmdReceive {
	public void receive(ServerCmdSender cmdSender, ServerGrid grid, String playerName, List<String> params) {
		// TODO compute lag and send LAG to client
		cmdSender.lag(playerName, 42);
	}
}
