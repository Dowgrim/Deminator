package fr.meusnnog.deminator.client.net.util;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.net.DemReceiver;

public abstract class ClientDemReceiver extends DemReceiver<ClientFrame> {
	public ClientDemReceiver(ClientFrame f) {
		super(f);
	}

	public final boolean isBroadcast() {
		return false;
	}
}
