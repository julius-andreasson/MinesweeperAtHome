package Main;

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

	public static void main(String[] args) {
		InputHandler inputHandler = new InputHandler();

		Game game = new Game(inputHandler, true);

		JFrame frame = new JFrame("Perspective");
		//Set the frame to terminate the program when closed.
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Applying settings and adding the custom Canvas-based 'viewer' to the JFrame 'frame'.
		//Dynamic calculation of the size of 'frame'.
		frame.getContentPane().setPreferredSize(new Dimension(
				Settings.borderSizeX * 2 + Settings.tileCountX * Settings.tileSizeX + (Settings.tileCountX - 1) * Settings.tileSpacingX, 
				Settings.borderSizeY * 3 + Settings.topUISizeY + Settings.tileCountY * Settings.tileSizeY + (Settings.tileCountY - 1) * Settings.tileSpacingY
				));
		frame.pack();
		frame.add(game.viewer);
		
		//Add keyListeners
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e){
				inputHandler.keyPressed(e.getKeyCode());
			}
			public void keyReleased(KeyEvent e) {
				inputHandler.keyReleased(e.getKeyCode());
			}
		});
		frame.setVisible(true);
		game.run();
	}
}