package v1;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

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
	static final int
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
	
	private static Game game;
	private static JFrame frame;

	public static void main(String[] args) {
		
		game = new Game(
			new Settings(tileSizeX, tileSizeY, tileCountX, tileCountY, mineCount, tileSpacingX, tileSpacingY, borderSizeX, borderSizeY, topUISizeY)	
		);

		frame = new JFrame("MinesweeperAtHome");
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
				int eventKey = 0;
				
				//Dig
				if(e.getKeyChar() == 'd')
					eventKey = 1;
				
				//Flag
				if(e.getKeyChar() == 'f')
					eventKey = 2;
				
				//Reset
				if (e.getKeyChar() == 'r')
					eventKey = 3;

				//Finish
				action(eventKey);
			}
		});
		frame.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int eventKey = 0;
				
				//Dig
				if(SwingUtilities.isLeftMouseButton(e))
					eventKey = 1;	
				
				//Flag
				if(SwingUtilities.isRightMouseButton(e))
					eventKey = 2;
				
				//Reset
				if (SwingUtilities.isMiddleMouseButton(e))
					eventKey = 3;
				
				//Finish
				action(eventKey);
			}
		});
		
		frame.setVisible(true);
	}
	
	private static void action(int eventKey) {
		//This prevents any action but reset to go through if the game has been lost. 
		
		if ((game.end_Loss || game.end_Win) && eventKey != 3) {
			eventKey = 0; 
		}
		//Event == 0: nothing 
		//Event == 1: dig
		//Event == 2: flag
		//Event == 3: reset
		
		//MousePos
		Point mousePos = new Point(
			MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().getLocation().x, 
			MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().getLocation().y
		);
		
		//x_pos
		int tileX = (int)(
				(float)(mousePos.x - (borderSizeX)) 
				/ 
				(float)(tileSizeX + tileSpacingX)); 
		
		//y_pos
		int tileY = (int)(
				(float)(mousePos.y - (borderSizeY * 3 + topUISizeY)) 
				/ 
				(float)(tileSizeY + tileSpacingY));
		
		if(game.map.isTileWithinBounds(tileX, tileY)) {
			game.action(eventKey, new Point(tileX, tileY));
		}
	}
}