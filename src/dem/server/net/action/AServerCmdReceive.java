package dem.server.net.action;

import dem.server.ServerDem;
import dem.server.model.ServerGrid;

import java.util.List;

public abstract class AServerCmdReceive {
	private ServerDem server;

	public abstract void receive(ServerCmdSender cmdSender, ServerGrid grid, int playerId, List<String> params);

	public void receive(int receiverId, List<String> commandParams) {
		ServerGrid grid = server.getGrid();
		receive(server.getCmdSender(), grid, receiverId, commandParams);
	}

	public void setServer(ServerDem server) {
		this.server = server;
	}
}
