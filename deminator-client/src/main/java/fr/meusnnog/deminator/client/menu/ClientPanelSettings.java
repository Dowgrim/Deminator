package fr.meusnnog.deminator.client.menu;

import fr.meusnnog.deminator.client.net.ClientDem;
import fr.meusnnog.deminator.client.net.actions.send.EmitterReady;
import fr.meusnnog.deminator.graphics.menu.PanelSettings;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2018-12-24
 */
public class ClientPanelSettings extends PanelSettings {
	private ClientDem cli;

	public ClientPanelSettings(){}

	@Override
	public void clickReady() {
		cli.sendCommand(new EmitterReady());
	}

	public void updateButtonReady(boolean b) {
		jbReady.setText(b ? "Not Ready" : "Ready !!!");
	}

	public void setCli(ClientDem cli) {
		this.cli = cli;
	}
}
