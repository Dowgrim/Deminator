package dem.net.client;

import dem.net.client.actions.*;
import dem.net.util.Communicator;
import dem.view.game.Deminator;

public class ComGame extends Communicator {
	public ComGame(Deminator d) {
		super.newReceiveAction("DEP", new CommandDEP(d));
		super.newReceiveAction("EXP", new CommandExp(d));
		super.newReceiveAction("DIS", new CommandDIS(d));
		super.newReceiveAction("STUN", new CommandStun(d));
		super.newReceiveAction("GAN", new CommandGAN(d));
		super.newReceiveAction("END", new CommandEnd(d));
	}
}
