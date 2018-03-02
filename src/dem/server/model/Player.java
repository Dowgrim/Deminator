package dem.server.model;


/**
 * @author Nathaël Noguès
 * @since 2018-02-27
 */
public class Player {
	private static final int R = 0;
	private static final int G = 0;
	private static final int B = 0;

	private String name;
	private final int color; // 0 to 359 (H; S and V fixed depending on object)

	private boolean ready = false;

	private GameCharacter character;

	public Player() {
		color = (int)(Math.random() * 360);
	}

	public int getColorHue() {
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
