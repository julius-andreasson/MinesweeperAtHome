package v1;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Perspective{
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

	public static void main(String[] args) {
		
		Game game = new Game(
			new Settings(tileSizeX, tileSizeY, tileCountX, tileCountY, mineCount, tileSpacingX, tileSpacingY, borderSizeX, borderSizeY, topUISizeY)
		);

		JFrame frame = new JFrame("Perspective");
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
				game.action(eventKey);
			}
		});
		
		frame.setVisible(true);
	}
}