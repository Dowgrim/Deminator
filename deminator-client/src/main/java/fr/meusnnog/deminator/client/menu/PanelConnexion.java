package fr.meusnnog.deminator.client.menu;

import fr.meusnnog.deminator.client.net.ClientDem;
import fr.meusnnog.deminator.client.net.actions.sendAndRecieve.CommandNew;
import fr.meusnnog.deminator.client.net.actions.sendAndRecieve.CommandPing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-30
 */
public class PanelConnexion extends JPanel {

	private ClientDem cli;
	private final JTextField jtfIpC, jtfPortC;
	private final JButton jbLaunchClient, jbCancelClient;
	private final JButton jbPingServeur;
	private final JButton jbNewPlayer;
	private final JLabel jlStatus;
	private final JTextField jtfNick;
	private final JTextField jtfColor;
	private final JPanel jpColor;

	private final JButton jbUpColor, jbDownColor;

	private final ClientFrame clientFrame;

	public PanelConnexion(ClientFrame cF) {
		this.clientFrame = cF;
		setLayout(new BorderLayout());

		jtfNick = new JTextField();
		jtfColor = new JTextField("0");

		jtfIpC = new JTextField("127.0.0.1", 15);

		jtfPortC = new JTextField("4224", 5);
		jbLaunchClient = new JButton("Connect");
		jbCancelClient = new JButton("Cancel");
		jbPingServeur = new JButton("Ping!");
		jbNewPlayer = new JButton("New Player!");
		jbUpColor = new JButton("^");
		jbDownColor = new JButton("v");
		jlStatus = new JLabel("Waiting to push button");
		jpColor = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				float tempColor;
				try {
					tempColor = Float.parseFloat(jtfColor.getText());
					if (tempColor > 360) {
						jtfColor.setText((int)(tempColor % 360) + "");
					}
					g.setColor(new Color(Color.HSBtoRGB(tempColor / 360, 1, 1)));
					g.fillRect(0, 0, 50, 50);
				} catch (Exception e) {
					g.setColor(Color.RED);
					g.drawLine(0, 0, this.getWidth(), this.getHeight());
					g.drawLine(this.getWidth(), 0, 0, this.getHeight());
					jtfColor.setText("0");
					return;
				}
			}
		};
		jlStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		jbNewPlayer.setVisible(false);
		jbPingServeur.setVisible(false);

		JPanel jpLeft = generateLeft();
		JPanel jpBas = new JPanel();
		{
			jpBas.setLayout(new BoxLayout(jpBas, BoxLayout.Y_AXIS));
			jpBas.add(new JSeparator());
			jpBas.add(jlStatus);
		}

		add(jpLeft, BorderLayout.CENTER);
		add(jpBas, BorderLayout.SOUTH);
		jpBas.setBorder(new EmptyBorder(10, 10, 10, 10));


		cli = new ClientDem(clientFrame);
		clientFrame.setClient(cli);
	}

	private JPanel generateLeft() {
		JPanel jpLeft = new JPanel();
		jpLeft.setLayout(new BoxLayout(jpLeft, BoxLayout.Y_AXIS));

		JPanel jpNick = new JPanel();
		{
			jtfNick.setMaximumSize(new Dimension(150, (int)jtfNick.getMinimumSize().getHeight()));
			jtfColor.setMaximumSize(new Dimension(50, (int)jtfColor.getMinimumSize().getHeight()));
			jtfColor.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					jpColor.repaint();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					jpColor.repaint();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					jpColor.repaint();
				}
			});

			jpColor.setMaximumSize(new Dimension(24, 24));
			jbUpColor.setMaximumSize(new Dimension(25, 12));
			jbDownColor.setMaximumSize(new Dimension(25, 12));
			jpNick.setLayout(new BoxLayout(jpNick, BoxLayout.X_AXIS));
			jpNick.add(new JLabel("Pseudo:"));
			jbNewPlayer.addActionListener(a4->clickNewPlayer());
			jbUpColor.addMouseListener(new MouseListener() {
				boolean isDown = false;

				@Override
				public void mouseClicked(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {
					isDown = true;
					Thread t = new Thread() {
						@Override
						public void run() {
							while (isDown) {
								jtfColor.setText("" + (Integer.parseInt(jtfColor.getText()) + 1));
								try {
									Thread.sleep(20);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
							}
						}
					};
					t.start();

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					isDown = false;
				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}
			});

			jbDownColor.addMouseListener(new MouseListener() {
				boolean isDown = false;

				@Override
				public void mouseClicked(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {
					isDown = true;
					Thread t = new Thread() {
						@Override
						public void run() {
							while (isDown) {
								jtfColor.setText("" + (Integer.parseInt(jtfColor.getText()) - 1));
								try {
									Thread.sleep(20);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
							}
						}
					};
					t.start();

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					isDown = false;
				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}
			});

			jpNick.add(jtfNick);
			jpNick.add(new JLabel("Color(0-360):"));
			jpNick.add(jtfColor);
			JPanel jpbuttonColor = new JPanel();
			jpbuttonColor.setMaximumSize(new Dimension(25, 24));
			jpbuttonColor.setLayout(new GridLayout(2, 1));
			jpbuttonColor.add(jbUpColor);
			jpbuttonColor.add(jbDownColor);
			jpNick.add(jpbuttonColor);
			JPanel jpSep = new JPanel();
			jpSep.setMaximumSize(new Dimension(5, 25));
			jpNick.add(jpSep);
			jpNick.add(jpColor);

			jpNick.add(jbNewPlayer);
		}

		JPanel jpHaut = new JPanel();
		{
			jpHaut.setLayout(new BoxLayout(jpHaut, BoxLayout.X_AXIS));

			jpHaut.add(new JLabel("Server:"));
			jpHaut.add(jtfIpC);
			jpHaut.add(new JLabel(":"));
			jpHaut.add(jtfPortC);
		}

		JPanel jpMid = new JPanel();
		{
			jbLaunchClient.addActionListener(al->clickTryConnect());
			jbPingServeur.addActionListener(a2->clickPingServer());
			jbCancelClient.addActionListener(a3->closeClient());
			jbCancelClient.setVisible(false);

			jpMid.setLayout(new BoxLayout(jpMid, BoxLayout.X_AXIS));
			jpMid.add(jbLaunchClient);
			jpMid.add(jbPingServeur);
			jpMid.add(jbCancelClient);
		}

		jpLeft.add(jpNick);
		jpLeft.add(jpHaut);
		jpLeft.add(jpMid);

		return jpLeft;
	}

	private void clickNewPlayer() {
		cli.sendCommand(new CommandNew(jtfNick.getText(), Integer.parseInt(jtfColor.getText())));
	}

	private void closeClient() {
		cli.closeClient();

		jbPingServeur.setVisible(false);
		jbNewPlayer.setVisible(false);
		jbCancelClient.setVisible(false);
		jbLaunchClient.setVisible(true);

		jlStatus.setText("Waiting to push button");
	}

	private void clickTryConnect() {
		jlStatus.setText("Trying to connect...");

		try {
			int port = Integer.parseUnsignedInt(jtfPortC.getText());
			cli.startClient(jtfIpC.getText(), port);
			jbPingServeur.setVisible(true);
			jbNewPlayer.setVisible(true);
			jbCancelClient.setVisible(true);
			jbLaunchClient.setVisible(false);
		} catch(NumberFormatException e) {
			jlStatus.setText("Error: Client's port should be an unsigned integer.");
		} catch(IOException iOE) {
			jlStatus.setText("Error: Wrong IP or server is not running");
		}


	}

	private void clickPingServer() {
		jlStatus.setText("Ping server!");
		cli.sendCommand(new CommandPing());
	}

	public void showMessage(String message) {
		jlStatus.setText(message);
	}

}
