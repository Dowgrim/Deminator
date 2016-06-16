package view.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Michael on 16/06/2016.
 */
public class PlayerListerner implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:{
                break;
            }
            case KeyEvent.VK_RIGHT:{
                break;
            }
            case KeyEvent.VK_UP:{
                break;
            }
            case KeyEvent.VK_DOWN:{
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
