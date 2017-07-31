package server;

import java.net.Socket;
import java.util.List;
import java.util.Random;

/**
 * Created by Michael on 29/04/2016.
 */
public class Grid {

    private Box[][] boxs;

    private int size;

    private int remainingBomb;
    
    private Socket sock;

    public Grid(int s, int b, Socket sock){
        size = s;
        remainingBomb = b;
        boxs = new Box[size][size];
        this.sock = sock;
        randomGrid();
    }



    public void randomGrid(){
        Random r = new Random();
        int bput = 0;
        int powSize = size*size;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(bput < remainingBomb) {
                    boxs[i][j] = new Box(r.nextInt(powSize) < remainingBomb);
                    if (boxs[i][j].isBomb()) {
                        bput++;
                    }
                }
            }
        }
        while(bput < remainingBomb){
            int x, y;
            x = r.nextInt(size); y = r.nextInt(size);
            boxs[x][y].setBomb();
            if (boxs[x][y].isBomb()) {
                bput++;
            }
        }

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                boxs[i][j].setValue(getValue(i, j));
            }
        }


    }

    private int getValue(int i, int j) {
        int tempValue = 0;
        for(int k = -1; k <= 1; k++){
            for(int l = -1; l <= 1; l++){
                if(boxs[(i+k)%size][(j+l)%size].isBomb()){
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

    public List<String> discover(int x, int y) {
        if(boxs[x][y].isNull()){
            for(int i = -1; i <= 1; i++){
                for(int j = -1; j <= 1; j++){
                    discover((x+i)%size, (y+j)%size, serv);
                }
            }
        }
        sock.discover(x, y);
    }

    public int get(int x, int y) {
        return boxs[x][y].getValue();
    }

    public int getSize() {
        return size;
    }
}
