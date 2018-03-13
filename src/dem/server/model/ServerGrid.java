package dem.server.model;

import dem.common.model.Grid;

import java.util.Random;

public class ServerGrid extends Grid {
    private static final int INIT_MAX_TRIES = 10000;

    public ServerGrid(int width, int height) {
        super(width, height);
    }

    private void addRandomBombs(int howManyBombs, int seed) {
        Random r = new Random(seed);

        for (int maxTries = INIT_MAX_TRIES; howManyBombs > 0 && maxTries > 0; maxTries--) {
            int rndX = r.nextInt(width);
            int rndY = r.nextInt(height);

            if (isBombOn(rndX, rndY)) {
                putBombOn(rndX, rndY);
                howManyBombs--;
            }
        }
        throw new RuntimeException("Cannot find empty location after " + INIT_MAX_TRIES + " tries");
    }

    private int getProximityNumberOf(int x, int y) {
        int value = 0;
        for (int x2 = -1; x2 <= 1; x2++) {
            for (int y2 = -1; y2 <= 1; y2++) {
                int checkX = x + x2;
                int checkY = y + y2;
                // OSEF to check if (x,y) is a bomb
                if (isCorrectLocation(x, y)
                        && isBombOn(checkX, checkY)) {
                    value++;
                }
            }
        }
        return value;
    }
}
