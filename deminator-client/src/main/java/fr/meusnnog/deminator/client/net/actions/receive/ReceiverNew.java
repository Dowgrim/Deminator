package fr.meusnnog.deminator.client.net.actions.receive;

import fr.meusnnog.deminator.client.menu.ClientFrame;
import fr.meusnnog.deminator.client.net.util.ClientDemReceiver;

import java.awt.*;
import java.util.List;

public class ReceiverNew extends ClientDemReceiver {
	public ReceiverNew(ClientFrame f) {
		super(f);
	}

	@Override
	public String getCommandName() {
		return "NEW";
	}

	/**
	 * param 1  Pseudo
	 * param 2  Color (int 0>360)
	 * (param 3 Ancient pseudo)
	 */
	@Override
	public void receive(List<String> params) {
		if(params.size() == 3) {
			updateExisting(params.get(2), params.get(0), Integer.parseInt(params.get(1)));
		} else {
			createNew(params.get(0), Integer.parseInt(params.get(1)));
		}
	}

	private void createNew(String newName, int newColor) {
		frame.addPlayer(newName, new Color(Color.HSBtoRGB(((float)(newColor) / 360), 1, 1)));
	}

	private void updateExisting(String oldName, String newName, int newColor) {
		frame.removePlayer(oldName);
		createNew(newName, newColor);
	}
}
