package dem.net.util.actions;

import dem.view.game.Deminator;

import java.util.List;

public abstract class Receiver {

	private Deminator dem;

	public Receiver(Deminator d){
		dem = d;
	}

	public abstract void receive(List<String> params);
}
