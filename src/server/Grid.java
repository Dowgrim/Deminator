package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Michael on 29/04/2016.
 */
public class Grid {

    private Box[][] boxs;
    
    private int size;

    private int remainingBomb;

    public Grid(int s, int b){
        size = s;
        remainingBomb = b;
        boxs = new Box[size][size];
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

    public void init(){

    }
}
