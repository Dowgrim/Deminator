package util;

import net.Client;
import net.Server;
import view.menu.DeminatorFrame;
import view.menu.PanelSettings;
import view.menu.PanelConnexion;

import java.util.Calendar;

/**
 * Created by Nathaël N on 04/05/16.
 */
public class Controller {
	private final PanelConnexion pc;
	//private final PanelGrid pg;
	private final PanelSettings ps;
	private final DeminatorFrame dg;
	private final Client c;
	private final Server s;

	public Controller() {
		pc = new PanelConnexion(this);
		//pg = new PanelGrid(this);
		ps = new PanelSettings(this);
		dg = new DeminatorFrame(this);
		dg.setView(pc);

		c = new Client(this);
		s = new Server(this);
	}

	// // // // //↓ PanelConnexion ↓// // // // //
	public void beAServer(int port) {
		Runnable r = () -> {
			try {
				long t = Calendar.getInstance().getTimeInMillis() + 1000 * 60; // try connecting during 60s
				do {
					if (s.start(port)) {
						dg.setView(ps);
						pc.reset();
						return;
					}
				} while (Calendar.getInstance().getTimeInMillis() < t);
				pc.reset();
				pc.showMessage("Cannot create a server since last 60s.");
			}catch(Exception e) {
				pc.reset();
				pc.showMessage("Cannot create a server on this port: " + e.getMessage());
			}
		};

		new Thread(r).start();
	}
	public void tryToBeAClient(String pseudo, String ip, int port) {
		Runnable r = () -> {
			long t = Calendar.getInstance().getTimeInMillis() + 1000*60; // try connecting during 60s
			c.setPseudo(pseudo);
			do {
				if (c.connectTo(ip, port)) {
					dg.setView(ps);
					pc.reset();
					return;
				}
			} while(Calendar.getInstance().getTimeInMillis() < t);
			pc.reset();
			pc.showMessage("Cannot connect to a server since last 60s.");
		};

		new Thread(r).start();
	}
	public void cancelBeAClient() {
		c.stopConnecting();
		pc.reset();
	}
	public void cancelBeAServ() {
		// TODO some network stuff
		pc.reset();
	}
	// // // // //↑ PanelConnexion ↑// // // // //



	//  //  //  //  //  MAIN    //  //  //  //  //
	public static void main(String[] args) {
		Controller c = new Controller();
	}
}
