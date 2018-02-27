package dem.model;

public class GameCharacter extends ElementOnGrid {
	private boolean living = false;
	private int points = 0;
	private int shield = 0;

	private int amo = 0;
	private String name;
	private boolean ready;

	public GameCharacter(String name, int x, int y) {
		super(x,y);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getPts() {
		return points;
	}

	public boolean isReady() {
		return ready;
	}
}
