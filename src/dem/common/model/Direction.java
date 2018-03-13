package dem.common.model;

public enum Direction {
	N(0,1), S(0,-1), E(1,0), W(-1,0);

	public final int[] decal;
	Direction(int x, int y) {
		this.decal = new int[]{x,y};
	}
}