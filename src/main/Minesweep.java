package main;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import utils.Point;
import utils.Settings;

public class Minesweep {

	private Game game = new Game();
	private StandardViewer viewer = new StandardViewer(game);
	private JFrame frame = new JFrame("MinesweeperAtHome");

	private Minesweep() {
		//Set the frame to terminate the program when closed.
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Applying settings and adding the custom Canvas-based 'viewer' to the JFrame 'frame'.
		//Dynamic calculation of the size of 'frame'.
		frame.getContentPane().setPreferredSize(new Dimension(
				Settings.borderSizeX * 2 + Settings.tileCountX * Settings.tileSizeX + (Settings.tileCountX - 1) * Settings.tileSpacingX, 
				Settings.borderSizeY * 3 + Settings.topUISizeY + Settings.tileCountY * Settings.tileSizeY + (Settings.tileCountY - 1) * Settings.tileSpacingY
				));
		frame.pack();
		frame.add(viewer);
		
		//Add keyListeners
		frame.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyChar()) {
					case 'd':
						action(Action.DIG);
						break;
					case 'f':
						action(Action.FLAG);
						break;
					case 'r':
						action(Action.RESET);
						break;
					case 'q':
						System.exit(0);
						break;
					default:
						action(Action.NONE);
						break;
				}
			}
		});
		frame.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				switch (e.getButton()) {
					case MouseEvent.BUTTON1:
						action(Action.DIG);
						break;
					case MouseEvent.BUTTON2:
						action(Action.FLAG);
						break;
					case MouseEvent.BUTTON3:
						action(Action.RESET);
						break;
					default:
						action(Action.NONE);
						break;
				}
			}
		});
		
		frame.setVisible(true);
	}
	
	private void action(Action action) {
		Point mousePos = new Point(
			MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().getLocation().x, 
			MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().getLocation().y
		);

		Point newPos = Settings.tileFromPixels(mousePos);
		
		game.action(action, newPos);
		viewer.repaint();
	}

	public static void main(String[] args) {
		new Minesweep();
	}
}