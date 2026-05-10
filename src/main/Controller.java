package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

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

public class Controller {
	private boolean shift; // Whether or not the shift key is down.
	private final Game game;
	private final JFrame view;

	public Controller() {
		// Classic Model-View-Controller (MVC) split.
		// Game is 'model' in the MVC split. It holds and updates the game state.
		// The only interaction View has with Game is reading game state to draw it.
		// This class, Controller, is the interface between them; it takes input, sends it to Game to update game state.
		game = new Game(Settings.startingPoint, Settings.tileCountX, Settings.tileCountY);
		view = new View(game);
	
		view.addKeyListener(createKeyAdapter());
		
		view.setVisible(true);
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
				game.move(dir, shift ? 3 : 1);
				view.repaint();
			}

			private void action(Action action) {
				game.action(action);
				view.repaint();
			}
		};
	}
}