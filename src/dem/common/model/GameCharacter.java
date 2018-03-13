package dem.common.model;

public class GameCharacter extends ElementOnGrid {
	private int points = 0;
	private int shield = 0;

	public int getPts() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getShield() {
		return shield;
	}
	public void setShield(int shield) {
		this.shield = shield;
	}
}
