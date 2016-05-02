package server.view;

import common.DeminatorFrame;
import common.menu.PanelSettings;
import server.Server;

/**
 * Created by Nathaël N on 02/05/16.
 */
public class DeminatorServer extends DeminatorFrame {

	private Server serv;
	private DeminatorServer() {
		super("Deminator Server");
		this.serv = new Server();
	}

	@Override
	public void setDefaultView() { setView(new PanelSettings(this, serv));	}
}
