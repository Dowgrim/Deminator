package dem.client.net.action.receive.settings;

import dem.client.net.action.ClientCmdSender;
import dem.common.net.CmdReceiver;
import dem.server.model.Grid;
import dem.server.model.Player;
import dem.server.net.action.ServerCmdSender;

import java.util.List;

public class CmdReceiveLdd extends CmdReceiver {
	@Override
	public void receive(ServerCmdSender cmdSender, Grid grid, Player emitter, List<String> params) {
		ClientCmdSender.pong();
	}
}
