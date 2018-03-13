package dem.client.model;

import dem.common.model.Grid;

import java.util.HashMap;
import java.util.Map;

public class ClientGrid extends Grid {
    private final Map<Integer, Integer> discoveredValues = new HashMap<>();

    public ClientGrid(int width, int height) {
        super(width, height);
    }

    private int getProximityNumberOf(int x, int y) {
        Integer value = discoveredValues.get(toIntLocation(x, y));
        return value==null?-1:value;
    }
    private void setProximityNumberOf(int x, int y, int value) {
        discoveredValues.put(toIntLocation(x, y), value);
    }
}
