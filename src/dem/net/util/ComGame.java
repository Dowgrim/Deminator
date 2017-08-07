package dem.net.util;

import dem.net.client.actions.*;

public class ComGame extends Communicator {
	public ComGame() {
		super.newReceiveAction("DEP", new CommandDEP());
		super.newReceiveAction("EXP", new CommandExp());
		super.newReceiveAction("DIS", new CommandDIS());
		super.newReceiveAction("STUN", new CommandStun());
		super.newReceiveAction("GAN", new CommandGAN());
		super.newReceiveAction("END", new CommandEnd());
	}
}
