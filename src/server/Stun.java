package server;

import net.Server;

/**
 * Created by Michael on 01/05/2016.
 */
public class Stun extends Thread{

    private Server server;

    private Player player;

    public Stun(Server serv, Player p){
        server = serv;
        player = p;
        player.setReady(false);
        start();
    }

    @Override
    public void run(){
        for(int i = 5; i >= 0; i--){
            server.stunBroadcast(player.getNick(), i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        player.setReady(true);
    }
}
