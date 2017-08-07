package dem.model;

import dem.net.server.ServerDem;

/**
 * Created by Michael on 01/05/2016.
 */
public class Stun extends Thread{

    private ServerDem server;

    private Player player;

    public Stun(ServerDem serv, Player p){
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
