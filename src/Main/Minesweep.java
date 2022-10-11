package Main;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import Utils.Point;

public class Minesweep{
	/**
	 * Settings. All are 'final'.
	 * tileSizeX sets the size of tiles in the x-dimension.
	 * tileSizeY sets the size of tiles in the y-dimension.
	 * tileCountX sets the number of tiles in the x-dimension.
	 * tileCountY sets the number of tiles in the y-dimension.
	 * mineCount sets the number of mines that are to be randomly spread over the map.
	 * tileSpacingX sets the size of the spacing between tiles in the x-dimension.
	 * tileSpacingY sets the size of the spacing between tiles in the y-dimension.
	 * borderSizeX sets the size of the spacing between the different areas of the playing area in the x-dimension.
	 * borderSizeY sets the size of the spacing between the different areas of the playing area in the y-dimension.
	 * topUISizeY sets the size of the UI at the top of the window.
	 */
	private static final int
		tileSizeX = 30, 
		tileSizeY = 30,
		tileCountX = 16,
		tileCountY = 16,
		mineCount = 40,
		tileSpacingX = 3, 
		tileSpacingY = 3,
		borderSizeX = 20,
		borderSizeY = 20,
		topUISizeY = 50;
	
	private Game game = new Game();
	private JFrame frame = new JFrame("MinesweeperAtHome");

	public Minesweep() {
		//Set the frame to terminate the program when closed.
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Applying settings and adding the custom Canvas-based 'viewer' to the JFrame 'frame'.
		//Dynamic calculation of the size of 'frame'.
		frame.getContentPane().setPreferredSize(new Dimension(
				borderSizeX * 2 + tileCountX * tileSizeX + (tileCountX - 1) * tileSpacingX, 
				borderSizeY * 3 + topUISizeY + tileCountY * tileSizeY + (tileCountY - 1) * tileSpacingY
				));
		frame.pack();
		frame.add(game.viewer);
		
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

		Point newPos = Settings.tileFromPixels(mousePos.x, mousePos.y);
		
		if(game.map.isTileWithinBounds(newPos.x, newPos.y)) {
			game.action(action, newPos);
		}
	}

	public static void main(String[] args) {
		new Minesweep();
	}
}