package fr.meusnnog.deminator.gameengine.model;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public class Box {

	private static int FREE = 0;
	private static int BOMB = -1;

	private int value;
	private boolean visible;

	public Box(boolean bomb) {
		visible = false;
		value = bomb ? BOMB : 0;
	}

	public void setBomb() {
		value = BOMB;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isFree() {
		return value == FREE;
	}

	public boolean isBomb() {
		return value == BOMB;
	}
}
