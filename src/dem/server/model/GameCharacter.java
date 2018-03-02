package dem.server.model;

public class GameCharacter extends ElementOnGrid {
	private boolean living = false;
	private int points = 0;
	private int shield = 0;

	private int amo = 0;

	public GameCharacter(int x, int y) {
		super(x,y);
	}

	public int getPts() {
		return points;
	}
}
