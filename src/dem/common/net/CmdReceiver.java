package dem.common.net;

import dem.server.ServerDem;
import dem.server.model.Grid;
import dem.server.model.Player;
import dem.server.net.action.ServerCmdSender;

import java.util.List;

public abstract class CmdReceiver {
	private ServerDem server;

	public abstract void receive(ServerCmdSender cmdSender, Grid grid, Player emitter, List<String> params);

	public void receive(int receiverId, List<String> commandParams) {
		Grid grid = server.getGrid();
		receive(server.getCmdSender(), grid, grid.getPlayerById(receiverId), commandParams);
	}

	public void setServer(ServerDem server) {
		this.server = server;
	}

}
