package dem.common.model;


/**
 * @author Nathaël Noguès
 * @since 2018-02-27
 */
public class Player extends ElementOnGrid {
	private String name;
	private int color; // 0 - 359 (included)
	private boolean ready = false;
	private GameCharacter character;

	public Player() {
		color = (int)(Math.random() * 360);
	}

	/**
	 * @return Player color is corresponding to the Hue (HSV color mode)
	 * Saturation(Grey-Color) and value(Black-Color) will depend on graphics and type of the element
	 */
	public int getPlayerColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public GameCharacter getCharacter() {
		return character;
	}

	public boolean isReady() {
		return ready;
	}
}
