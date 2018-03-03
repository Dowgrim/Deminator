package dem.server.net.action.receive;

import dem.common.net.CmdReceiver;
import dem.server.model.Grid;
import dem.server.model.Player;
import dem.server.net.action.ServerCmdSender;

import java.util.List;

public class CmdReceivePong extends CmdReceiver {
	public void receive(ServerCmdSender cmdSender, Grid grid, Player emitter, List<String> params) {
		// TODO compute lag and send LAG to client
		cmdSender.lag(emitter, 42);
	}
}
