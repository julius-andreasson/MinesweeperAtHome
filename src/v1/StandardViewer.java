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
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, game.settings.tileSizeX));
		
		//Draw Tiles
		for (int x = 0; x < game.map.tileMap.length; x++) {
			for (int y = 0; y < game.map.tileMap[0].length; y++) {
				//x_pos
				int x_pos = game.settings.borderSizeX + x * (game.settings.tileSizeX + game.settings.tileSpacingX);
				//y_pos
				int y_pos = game.settings.borderSizeY * 2 + game.settings.topUISizeY + y * (game.settings.tileSizeY + game.settings.tileSpacingY);
				
				
				paintTile(g, x, y, x_pos, y_pos);
			}
		}
		
		//Draw text; game-over if (end_Loss)
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, game.settings.tileSizeY - 3));
		g.setColor(Color.black);
		if (game.end_Loss) {
			g.drawString("Game over! Press 'R' to restart.", game.settings.borderSizeX - 5, game.settings.borderSizeY + 20);
			g.drawString("You cleared " + (game.map.tilesDugUp) + " out of " + (game.tilesToWin) + " tiles.", game.settings.borderSizeX - 5, game.settings.borderSizeY + 25 + game.settings.tileSizeY);
		} else if (game.end_Win){
			g.drawString("You've won! Press 'R' to restart.", game.settings.borderSizeX, game.settings.borderSizeY + 20);
			g.drawString("You cleared " + (game.map.tilesDugUp) + " out of " + (game.tilesToWin) + " tiles.", game.settings.borderSizeX - 5, game.settings.borderSizeY + 25 + game.settings.tileSizeY);
		} else {
			//TopUI
			g.drawString("Remaining flags: " + game.map.remainingFlags, game.settings.borderSizeX, game.settings.borderSizeY + 20);
			g.drawString("Tiles cleared: " + game.map.tilesDugUp, game.settings.borderSizeX, game.settings.borderSizeY + 50);
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
		g.fillRect(posX, posY, game.settings.tileSizeX, game.settings.tileSizeY);
		//If the player has lost the game, draw a mine on the current tile if it 'hasMine'. 
		if ((game.end_Loss || game.end_Win) && game.map.tileMap[tileX][tileY].hasMine) {
			//Set the color of the mines. 
			g.setColor(Color.black);
			//Draw an oval that represents a mine. 
			g.fillOval(posX, posY, game.settings.tileSizeX, game.settings.tileSizeY);
		}
		if (!game.map.tileMap[tileX][tileY].hasMine) {
			if(game.map.tileMap[tileX][tileY].isChecked && game.map.tileMap[tileX][tileY].surroundingMines > 0) {
				stringColor = Color.black; 
				g.setColor(stringColor); 
				g.drawString("" + game.map.tileMap[tileX][tileY].surroundingMines, posX + game.settings.tileSpacingX, posY + game.settings.tileSizeY - game.settings.tileSpacingY); 	
			}
		}
	}
}