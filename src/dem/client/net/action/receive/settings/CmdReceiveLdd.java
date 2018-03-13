package dem.client.net.action.receive.settings;

import dem.client.net.action.AClientCmdReceive;
import dem.client.net.action.ClientCmdSender;
import dem.server.model.ServerGrid;

import java.util.List;

public class CmdReceiveLdd extends AClientCmdReceive {
	@Override
	public void receive(ClientCmdSender cmdSender, ServerGrid grid, List<String> params) {
		ClientCmdSender.pong();
	}
}
