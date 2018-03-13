package dem.common.model;

import dem.common.util.DoubleMap;

import java.util.HashMap;
import java.util.Map;

public class Grid {
    protected final int width;
    protected final int height;

    private static final ElementOnGrid BOMB = new ElementOnGrid(){};

    private final Map<Integer, Player> players = new HashMap<>();
    private final DoubleMap<Integer, ElementOnGrid> grid = new DoubleMap<>();

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected int toIntLocation(int x, int y) {
        return x* height +y;
    }
    protected int[] fromIntLocation(int intLocation) {
        return new int[]{intLocation/ height, intLocation% height};
    }


    public ElementOnGrid getElementOn(int x, int y) {
        return grid.get(toIntLocation(x,y));
    }
    public boolean isBombOn(int x, int y) {
        return getElementOn(x,y) == BOMB;
    }

    protected void putBombOn(int x, int y) {
        int loc = toIntLocation(x,y);
        if(grid.get(loc) != null) {
            throw new RuntimeException("Putting bomb on a cell with something already on it");
        }
        grid.put(loc, BOMB);
    }

    public boolean isCorrectLocation(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public Player getPlayer(int playerId) {
        return players.get(playerId);
    }

    public int[] getLocationOf(ElementOnGrid element) {
        return fromIntLocation(grid.getR(element));
    }

    public boolean isPlayerOn(int newX, int newY) {
        return grid.get(toIntLocation(newX, newY)) instanceof Player;
    }

    public void moveElementTo(Player p, int newX, int newY) {
        // TODO
    }
}
