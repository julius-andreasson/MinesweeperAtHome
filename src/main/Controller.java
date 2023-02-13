package main;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import utils.Settings;

enum Action {
	NONE,
	DIG,
	FLAG,
	RESET
}

enum Direction {
	LEFT,
	RIGHT,
	UP,
	DOWN
}

enum State {
	ONGOING,
	LOST,
	WON
}

public class Controller {
	private boolean shift; // is the shift key down?
	private final Board board;
	private final JPanel view;

	public Controller() {
		board = new Board(Settings.startingPoint, Settings.tileCountX, Settings.tileCountY);
		view = new View(board);
		JFrame frame = new JFrame("MinesweeperAtHome");

		//Set the frame to terminate the program when closed.
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Applying settings and adding the custom Canvas-based 'viewer' to the JFrame 'frame'.
		//Dynamic calculation of the size of 'frame'.
		frame.getContentPane().setPreferredSize(new Dimension(
				Settings.borderSizeX * 2 + Settings.tileCountX * Settings.tileSizeX + (Settings.tileCountX - 1) * Settings.tileSpacingX, 
				Settings.borderSizeY * 3 + Settings.topUISizeY + Settings.tileCountY * Settings.tileSizeY + (Settings.tileCountY - 1) * Settings.tileSpacingY
				));
		frame.pack();
		frame.add(view);
		
		frame.addKeyListener(createKeyAdapter());
		
		frame.setVisible(true);
	}

	KeyAdapter createKeyAdapter() {
		return new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
					shift = false;
				}
			}
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_D -> action(Action.DIG);
					case KeyEvent.VK_F -> action(Action.FLAG);
					case KeyEvent.VK_R -> action(Action.RESET);
					case KeyEvent.VK_Q -> System.exit(0);
					case KeyEvent.VK_LEFT -> move(Direction.LEFT);
					case KeyEvent.VK_RIGHT -> move(Direction.RIGHT);
					case KeyEvent.VK_UP -> move(Direction.UP);
					case KeyEvent.VK_DOWN -> move(Direction.DOWN);
					case KeyEvent.VK_SHIFT -> shift = true;
					default -> action(Action.NONE);
				}
			}

			private void move(Direction dir) {
				board.move(dir, shift ? 3 : 1);
				view.repaint();
			}

			private void action(Action action) {
				board.action(action);
				view.repaint();
			}
		};
	}
}