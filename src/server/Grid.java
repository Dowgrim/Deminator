package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by Michael on 29/04/2016.
 */
public class Grid {

    private ServerSocket server;

    private Box[][] boxs;

    private int size;

    private int bombesRestante;

    private ArrayList<Player> players;

    public Grid(int s, int b){
        try {
            server = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        size = s;
        bombesRestante = b;
        boxs = new Box[size][size];
        randomGrid();
        waitingPlayer();
    }

    private void waitingPlayer() {


    }

    public void randomGrid(){
        int bplace = 0;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                //TODO
            }
        }
    }

    public void init(){

    }
}
