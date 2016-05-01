package server;

/**
 * Created by Michael on 29/04/2016.
 */
public class Box {

    /**
     * value -1 == Bombe
     */
    private int value;

    private boolean visible;

    public Box(boolean b){
        visible = false;
        value = b?-1:0;
    }

    public boolean isBomb() {
        return value == -1;
    }

    public void setBomb() {
        value = -1;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
