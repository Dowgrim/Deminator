package dem.common.util;

import java.util.HashMap;

public class DoubleMap<X,Y> {
    private final HashMap<X, Y> a = new HashMap<>();
    private final HashMap<Y, X> b = new HashMap<>();

    public void put(X x, Y y) {
        a.put(x, y);
        b.put(y, x);
    }

    public Y get(X x) {
        return a.get(x);
    }
    public X getR(Y y) {
        return b.get(y);
    }

    public void clear() {
        a.clear();
        b.clear();
    }
}
