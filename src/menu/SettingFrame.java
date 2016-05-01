package menu;

import server.Player;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;

/**
 * Created by Michael on 29/04/2016.
 */
public class SettingFrame extends JFrame {

	private final JButton jbNotReady;
	private final JButton jbGo;
	private final JButton jbReady;
	private boolean isServ;

    private Server server = null;

    public SettingFrame(Server serv) {
        super("Salon");
        server = serv;

	    JPanel jpMain = new JPanel();
	    {
		    JScrollPane jspPlayers = new JScrollPane();
		    {
			    JPanel viewport = new JPanel();
				jspPlayers.setViewportView(viewport);
			    viewport.setBackground(Color.WHITE);
			    viewport.setLayout(new GridLayout(0, 1, 2, 2));

			    for(int i=0; i<21; i++)
				    viewport.add(new JPPlayer("PlayerName"+(2<<i),
						    new Color((int)(Math.random()*256),
								    (int)(Math.random()*256),
								    (int)(Math.random()*256))));
			    jspPlayers.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			    jspPlayers.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		    }

		    JComponent jspSettings = initSettingsPanel();

		    JPanel jpButtons = new JPanel();
		    {
			    jbReady = new JButton("Ready !");
			    {
				    jbReady.addActionListener(al -> clicGetReady(true));
			    }
			    jbNotReady = new JButton("Plus Ready");
			    {
				    jbNotReady.addActionListener(al -> clicGetReady(false));
				    jbNotReady.setVisible(false);
			    }
			    jbGo = new JButton("Everyone Ready, GO !");
			    {
				    jbGo.addActionListener(al -> clicGo());
			    }

			    jpButtons.add(jbReady);
			    jpButtons.add(jbNotReady);
			    jpButtons.add(jbGo);
		    }

			jpMain.setLayout(new BorderLayout());
		    jpMain.add(jspPlayers, BorderLayout.EAST);
		    jpMain.add(jspSettings, BorderLayout.CENTER);
		    jpMain.add(jpButtons, BorderLayout.SOUTH);

	    }// JPMain

	    setContentPane(jpMain);

	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    this.pack();

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                int n = JOptionPane.showConfirmDialog(
                        SettingFrame.this,
                        "Do you really want to close the server?",
                        "Close?",
                        JOptionPane.YES_NO_OPTION);
                if(n == JOptionPane.YES_OPTION){
                    Object[] options = {"OSEF",
                            "YOLO"};
                    n = JOptionPane.showOptionDialog(SettingFrame.this,
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

	private JComponent initSettingsPanel() {
		JScrollPane jspSettings = new JScrollPane();
		{
			JPanel viewport = new JPanel();
			jspSettings.setViewportView(viewport);
			viewport.setLayout(new BoxLayout(viewport, BoxLayout.Y_AXIS));

			JPanel jpMapDim = new JPanel();
			{
				JTextField jtfX = new JTextField("10", 3);
				{
					jtfX.setMaximumSize(new Dimension(30, jtfX.getPreferredSize().height));
				}
				JTextField jtfY = new JTextField("10", 3);
				{
					jtfY.setMaximumSize(new Dimension(30, jtfY.getPreferredSize().height));
				}

				jpMapDim.setAlignmentX( Component.LEFT_ALIGNMENT );
				jpMapDim.setLayout(new BoxLayout(jpMapDim, BoxLayout.X_AXIS));
				jpMapDim.add(new JLabel("Taille de la carte: x:"));

				jpMapDim.add(jtfX);
				jpMapDim.add(new JLabel(" y:"));

				jpMapDim.add(jtfY);
			}

			JPanel jpNbMines = new JPanel();
			{
				JTextField jtfMines = new JTextField("42", 3);
				{
					jtfMines.setMaximumSize(new Dimension(50, jtfMines.getPreferredSize().height));
				}

				jpNbMines.setAlignmentX( Component.LEFT_ALIGNMENT );
				jpNbMines.add(new JLabel("Nombre de mines: "));
				jpNbMines.add(jtfMines);
			}

			/////////////////////////////////////////////// HERE to add your own new parameter types

			viewport.add(jpMapDim);
			viewport.add(jpNbMines);
		}

		return jspSettings;
	}

	private void clicGo() {
		// TODO
	}

	private void clicGetReady(boolean isReady) {
		// TODO
	}

	public SettingFrame(){
        this(null);
    }

	public static void main(String[] args) {
		new SettingFrame();
	}

    public void reloadPlayer(HashMap<String, Player> players) {
    }
}
