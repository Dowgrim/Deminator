import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 */
public class Yolo extends JFrame {

    private static String BLUE = "images\\Blue.png";

    private static String GREEN = "images\\Green.png";

    private JPanel contentPane;

    private ArrayList<JPImage> cases;

    private int pos;

    public Yolo(){
        super("Yolo");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        contentPane = new JPanel();
        setContentPane(contentPane);

        contentPane.setLayout(new GridLayout(4, 4));
        cases = new ArrayList<JPImage>();
        JPImage p;
        for(int i = 0; i < 16; i++){
            p = new JPImage();
            cases.add(p);
            contentPane.add(p);
        }
        pos = 0;
        cases.get(pos).addImage(BLUE);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP:{
                        cases.get(pos).rmImage(BLUE);
                        pos = (pos - 4)%16;
                        cases.get(pos).addImage(BLUE);
                        break;
                    }
                    case KeyEvent.VK_DOWN:{
                        cases.get(pos).rmImage(BLUE);
                        pos = (pos + 4)%16;
                        cases.get(pos).addImage(BLUE);

                        break;
                    }
                    case KeyEvent.VK_LEFT:{
                        cases.get(pos).rmImage(BLUE);
                        pos = (pos - 1)%16;
                        cases.get(pos).addImage(BLUE);
                        break;
                    }
                    case KeyEvent.VK_RIGHT:{
                        cases.get(pos).rmImage(BLUE);
                        pos = (pos + 1)%16;
                        cases.get(pos).addImage(BLUE);
                        break;
                    }
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new Yolo();
    }
}
