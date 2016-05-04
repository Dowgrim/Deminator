package client;

import util.Controler;
import view.DeminatorGame;
import view.PanelSettings;

/**
 * Created by Nathaël N on 04/05/16.
 */
public class Client implements Controler  {
	private final DeminatorGame frame;
	private PanelSettings ps;
	//private PanelGrid pg;

	public Client(DeminatorGame papa) {
		this.frame = papa;

		ps = new PanelSettings(PanelSettings.Rank.CLIENT, this);
		//pg = null;
		papa.setView(ps);
	}

	@Override
	public void disconnect() {
		frame.setViewToConnection();
		if(ps != null)
			ps.setVisible(false);
		//if(pg != null)
		//  pg.setVisible(false);
	}
}
