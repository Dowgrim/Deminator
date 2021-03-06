package fr.meusnnog.deminator.server.actions.receive;

import fr.meusnnog.deminator.server.actions.send.EmitterNew;
import fr.meusnnog.deminator.server.menu.ServerFrame;
import fr.meusnnog.deminator.net.DemEmitter;
import fr.meusnnog.deminator.server.net.ServerDemReceiver;
import fr.meusnnog.deminator.server.controllers.PlayerController;

import java.awt.*;
import java.util.List;

public class ReceiveNew extends ServerDemReceiver {
	public ReceiveNew(ServerFrame sF, PlayerController p) {
		super(sF, p);
	}

	@Override
	public void receive(List<String> params) {
		try {
			// NEW name hue
			String name = params.get(0);
			int h = Integer.parseInt(params.get(1));

			String oldName = playerController.getPlayer().getNick();
			playerController.getPlayer().setNick(name);
			playerController.getPlayer().setColor(h);

			playerController.send(new EmitterNew(oldName, name, h));
			frame.removePlayer(oldName);
            frame.addPlayer(name, new Color(Color.HSBtoRGB((float)h / 360, 1, 1)));
		} catch (IndexOutOfBoundsException|NumberFormatException e) {
			System.err.println("Called Receive new with parameters: " + params);
		}
	}

	@Override
	public String getCommandName() {
		return "NEW";
	}
}
