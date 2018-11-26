package fr.meusnnog.deminator.gameengine.model;

import java.util.List;
import java.util.Random;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public class Grid {

	private Box[][] boxs;

	private int size;

	private int remainingBomb;

	public Grid(int s, int b) {
		size = s;
		remainingBomb = b;
		boxs = new Box[size][size];
		randomGrid();
	}


	private void randomGrid() {
		Random r = new Random();
		int bput = 0;
		int powSize = size * size;
		doubleLoop1:

		for (int x = size - 1; x >= 0; x--) {
			for (int y = size - 1; y >= 0; y--) {
				boxs[x][y] = new Box(r.nextInt(powSize) < remainingBomb);
				if (boxs[x][y].isBomb()) {
					bput++;
					if (bput >= remainingBomb) {
						break doubleLoop1;
					}
				}
			}
		}

		int maxTries = 10000; // Avoid infinite loop
		while (bput < remainingBomb && (maxTries--) > 0) {
			Box randomCell = boxs[r.nextInt(size)][r.nextInt(size)];
			if (!randomCell.isBomb()) {
				randomCell.setBomb();
				bput++;
			}
		}

		for (int x = size - 1; x >= 0; x--) {
			for (int y = size - 1; y >= 0; y--) {
				boxs[x][y].setValue(getValue(x, y));
			}
		}
	}

	private int getValue(int x, int y) {
		int tempValue = 0;
		for (int dX = -1; dX <= 1; dX++) {
			for (int dY = -1; dY <= 1; dY++) {
				if (boxs[(x + dX) % size][(y + dY) % size].isBomb()) {
					tempValue++;
				}
			}
		}
		return tempValue;
	}

	public boolean isBomb(int x, int y) {
		return boxs[x][y].isBomb();
	}

	public boolean isDiscov(int x, int y) {
		return boxs[x][y].isVisible();
	}

	/*public List<String> discover(int x, int y) {
		if (boxs[x][y].isFree()) {
			for (int dX = -1; dX <= 1; dX++) {
				for (int dY = -1; dY <= 1; dY++) {
					discover((x + dX) % size, (y + dY) % size);
					server.discover(x, y);
				}
			}
		}
		return null;
	}*/

	public int get(int x, int y) {
		return boxs[x][y].getValue();
	}

	public int size() {
		return size;
	}
}
