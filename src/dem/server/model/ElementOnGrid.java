package dem.server.model;

public class ElementOnGrid {
	private int x;
	private int y;

	protected ElementOnGrid(int x, int y) {
		moveTo(x,y);
	}

	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean isOn(int x, int y) {
		return x == this.x && y == this.y;
	}

	public int[] getLocation() {
		return new int[]{x,y};
	}
	public int[] getLocation(int[] decal) {
		return new int[]{x+decal[0],y+decal[1]};
	}
}
