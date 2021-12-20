package v1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StandardViewer extends JPanel {
	Game game;

	/**
	 * Constructor
	 */
	public StandardViewer (Game _game) {
		game = _game;
	}
	
	/**
	 * 
	 */
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//Draw left-side split
		g.setColor(Color.black);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, game.tileSizeX));
		
		//Draw Tiles
		for (int x = 0; x < game.map.tileMap.length; x++) {
			for (int y = 0; y < game.map.tileMap[0].length; y++) {
				//x_pos
				int x_pos = game.borderSizeX + x * (game.tileSizeX + game.tileSpacingX);
				//y_pos
				int y_pos = game.borderSizeY * 2 + game.topUISizeY + y * (game.tileSizeY + game.tileSpacingY);
				
				
				paintTile(g, x, y, x_pos, y_pos);
			}
		}
		
		//Draw text; game-over if (end_Loss)
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, game.tileSizeY - 3));
		g.setColor(Color.black);
		if (game.end_Loss) {
			g.drawString("Game over! Press 'R' to restart.", game.borderSizeX - 5, game.borderSizeY + 20);
			g.drawString("You cleared " + (game.map.tilesDugUp) + " out of " + (game.tilesToWin) + " tiles.", game.borderSizeX - 5, game.borderSizeY + 25 + game.tileSizeY);
		} else if (game.end_Win){
			g.drawString("You've won! Press 'R' to restart.", game.borderSizeX, game.borderSizeY + 20);
			g.drawString("You cleared " + (game.map.tilesDugUp) + " out of " + (game.tilesToWin) + " tiles.", game.borderSizeX - 5, game.borderSizeY + 25 + game.tileSizeY);
		} else {
			//TopUI
			g.drawString("Remaining flags: " + game.map.remainingFlags, game.borderSizeX, game.borderSizeY + 20);
			g.drawString("Tiles cleared: " + game.map.tilesDugUp, game.borderSizeX, game.borderSizeY + 50);
		}
	}
	
	public void paintTile(Graphics g, int tileX, int tileY, int posX, int posY) {
		Color tileColor, stringColor; 
		//int edgeSize = 5; 
		
		if (game.map.tileMap[tileX][tileY].isChecked) {
			tileColor = Color.lightGray; 
		}
		else
		{
			if(game.map.tileMap[tileX][tileY].isFlagged) {
				tileColor = Color.red; 
			}
			else {
				tileColor = Color.gray; 
			}
		}
		g.setColor(tileColor);
		g.fillRect(posX, posY, game.tileSizeX, game.tileSizeY);
		//If the player has lost the game, draw a mine on the current tile if it 'hasMine'. 
		if ((game.end_Loss || game.end_Win) && game.map.tileMap[tileX][tileY].hasMine) {
			//Set the color of the mines. 
			g.setColor(Color.black);
			//Draw an oval that represents a mine. 
			g.fillOval(posX, posY, game.tileSizeX, game.tileSizeY);
		}
		if (!game.map.tileMap[tileX][tileY].hasMine) {
			if(game.map.tileMap[tileX][tileY].isChecked && game.map.tileMap[tileX][tileY].surroundingMines > 0) {
				stringColor = Color.black; 
				g.setColor(stringColor); 
				g.drawString("" + game.map.tileMap[tileX][tileY].surroundingMines, posX + game.tileSpacingX, posY + game.tileSizeY - game.tileSpacingY); 	
			}
		}
	}
}