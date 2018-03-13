package dem.client.graphic;

import dem.client.ClientDem;
import dem.client.graphic.menu.ClientFrame;

public class ClientGraphic extends ClientDem {

    private ClientGraphic() {
        // TODO launch graphics
    }

	public static void main(String[] args) {
		new ClientFrame();
	}

	@Override
	public void log(String s) {

	}
}
