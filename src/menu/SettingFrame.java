package menu;

import server.Player;
import server.Server;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;

/**
 * Created by Michael on 29/04/2016.
 */
public class SettingFrame extends JFrame {

    private boolean isServ;

    private Server server = null;

    public SettingFrame(Server serv) {
        super();
        server = serv;
        final JFrame jF = this;
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                int n = JOptionPane.showConfirmDialog(
                        jF,
                        "Do you really want to close the server?",
                        "Close?",
                        JOptionPane.YES_NO_OPTION);
                if(n == JOptionPane.YES_OPTION){
                    Object[] options = {"OSEF",
                            "YOLO"};
                    n = JOptionPane.showOptionDialog(jF,
                            "Nooo!!Don't do that otherwise this is what will happen: The time and space wil break.",
                            "Don't do that!",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);
                    //TODO
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public SettingFrame(){
        super();
        final JFrame jF = this;
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                int n = JOptionPane.showConfirmDialog(
                        jF,
                        "Do you really want to close the server?",
                        "Close?",
                        JOptionPane.YES_NO_OPTION);
                if(n == JOptionPane.YES_OPTION){
                    Object[] options = {"OSEF",
                            "YOLO"};
                    n = JOptionPane.showOptionDialog(jF,
                            "Nooo!!Don't do that otherwise this is what will happen: The time and space wil break.",
                            "Don't do that!",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);
                    //TODO
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public void reloadPlayer(HashMap<String, Player> players) {
    }
}
