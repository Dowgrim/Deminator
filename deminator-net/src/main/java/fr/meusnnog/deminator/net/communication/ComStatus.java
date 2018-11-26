package fr.meusnnog.deminator.net.communication;

import fr.meusnnog.deminator.net.DemReceiver;

import java.util.HashMap;
import java.util.Map;

public abstract class ComStatus {
	public final Map<String, DemReceiver> receivers = new HashMap<>();

	protected void newReceiveAction(DemReceiver demReceiver) {
		receivers.put(demReceiver.getCommandName(), demReceiver);
	}
}
