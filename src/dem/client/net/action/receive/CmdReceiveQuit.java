package dem.client.net.action.receive;

import dem.client.net.action.AClientCmdReceive;
import dem.client.net.action.ClientCmdSender;
import dem.server.model.ServerGrid;

import java.util.List;

public class CmdReceiveQuit extends AClientCmdReceive {
	@Override
	public void receive(ClientCmdSender cmdSender, ServerGrid grid, List<String> params) {
		ClientCmdSender.pong();
	}
}
