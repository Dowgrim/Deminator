package dem.client.net.action;

import dem.client.ClientDem;
import dem.client.model.ClientGrid;

import java.util.List;

public abstract class AClientCmdReceive {
	private ClientDem client;

	public abstract void receive(ClientCmdSender cmdSender, ClientGrid grid, List<String> params);

	public void receive(List<String> commandParams) {
		receive(client.getCmdSender(), client.getGrid(), commandParams);
	}

	public void setClientDem(ClientDem client) {
		this.client = client;
	}

}
