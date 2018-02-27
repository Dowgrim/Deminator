package dem.model;

import java.util.*;

public class GridNew {
	private static final int INIT_MAX_TRIES = 10000;

	// change to HashMap<int,? extends ElementOnGrid> if need a Bomb object
	private final Set<Integer> bombs = new HashSet<>();
	private final List<GameCharacter> gameCharacters = new ArrayList<>();
	private final int width;
	private final int height;

	public GridNew(int width, int height) {
		this.width = width;
		this.height = height;
	}

	private void addRandomBombs(int howManyBombs) {
		Random r = new Random();

		int maxTries = INIT_MAX_TRIES;

		while(howManyBombs > 0 && (maxTries--)>0) {
			int locationHash = toIntLocation(r.nextInt(width),r.nextInt(height));
			if(bombs.contains(locationHash)) {
				bombs.add(locationHash);
				howManyBombs--;
			}
		}
	}

	private int getProximityNumberOf(int x, int y) {
		int value = 0;
		for(int x2=-1; x2<=1; x2++) {
			for(int y2 = -1; y2 <= 1; y2++) {
				int checkX = x+x2;
				int checkY = y+y2;
				// OSEF to check if (x,y) is a bomb
				if(isCorrectLocation(x,y)
					&& bombs.contains(toIntLocation(checkX,checkY))) {
					value++;
				}
			}
		}
		return value;
	}

	private int toIntLocation(int x, int y) {
		return x* height +y;
	}

	private int[] fromIntLocation(int intLocation) {
		return new int[]{intLocation/ height, intLocation% height};
	}

	public boolean isBombOn(int x, int y) {
		return bombs.contains(toIntLocation(x,y));
	}

	public boolean isCorrectLocation(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}

	public boolean isPlayerOn(int x, int y) {
		for(GameCharacter c : gameCharacters) {
			if(c.isOn(x,y)) {
				return true;
			}
		}
		return false;
	}
}
