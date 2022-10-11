package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import Utils.Point;

import javax.swing.JPanel;

public class StandardViewer extends JPanel {
	Game game;

	public StandardViewer (Game game) {
		this.game = game;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//Draw left-side split
		g.setColor(Color.black);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, Settings.tileSizeX));
		
		//Draw Tiles
		for (int x = 0; x < game.map.tileMap.length; x++) {
			for (int y = 0; y < game.map.tileMap[0].length; y++) {
				paintTile(g, x, y);
			}
		}
		
		//Draw text; game-over if (end_Loss)
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, Settings.tileSizeY - 3));
		g.setColor(Color.black);
		if (game.end_Loss) {
			g.drawString("Game over! Press 'R' to restart.", Settings.borderSizeX - 5, Settings.borderSizeY + 20);
			g.drawString("You cleared " + (game.map.tilesDugUp) + " out of " + (game.tilesToWin) + " tiles.", Settings.borderSizeX - 5, Settings.borderSizeY + 25 + Settings.tileSizeY);
		} else if (game.end_Win){
			g.drawString("You've won! Press 'R' to restart.", Settings.borderSizeX, Settings.borderSizeY + 20);
			g.drawString("You cleared " + (game.map.tilesDugUp) + " out of " + (game.tilesToWin) + " tiles.", Settings.borderSizeX - 5, Settings.borderSizeY + 25 + Settings.tileSizeY);
		} else {
			//TopUI
			g.drawString("Remaining flags: " + game.map.remainingFlags, Settings.borderSizeX, Settings.borderSizeY + 20);
			g.drawString("Tiles cleared: " + game.map.tilesDugUp, Settings.borderSizeX, Settings.borderSizeY + 50);
		}
	}
	
	public void paintTile(Graphics g, int tileX, int tileY) {
		Color tileColor, stringColor; 
		Point pos = Settings.pixelsFromTile(tileX, tileY);
		
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
		g.fillRect(pos.x, pos.y, Settings.tileSizeX, Settings.tileSizeY);
		//If the player has lost the game, draw a mine on the current tile if it 'hasMine'. 
		if ((game.end_Loss || game.end_Win) && game.map.tileMap[tileX][tileY].hasMine) {
			//Set the color of the mines. 
			g.setColor(Color.black);
			//Draw an oval that represents a mine. 
			g.fillOval(pos.x, pos.y, Settings.tileSizeX, Settings.tileSizeY);
		}
		// Draw the number of mines on each checked tile. 
		if (!game.map.tileMap[tileX][tileY].hasMine) {
			if(game.map.tileMap[tileX][tileY].isChecked && game.map.tileMap[tileX][tileY].surroundingMines > 0) {
				stringColor = Color.black; 
				g.setColor(stringColor); 
				g.drawString("" + game.map.tileMap[tileX][tileY].surroundingMines, pos.x + Settings.tileSpacingX, pos.y + Settings.tileSizeY - Settings.tileSpacingY);
			}
		}
	}
}