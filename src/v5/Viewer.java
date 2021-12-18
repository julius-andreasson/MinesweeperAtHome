package v5;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Viewer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4468946197788215667L;
	
	/**
	 * Constructor
	 */
	public Viewer () {
	}
	
	/**
	 * 
	 */
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//Draw left-side split
		g.setColor(Color.black);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, MainFrame.tileSizeX));
		
		//Draw Tiles
		for (int x = 0; x < Map.TileMap.length; x++) {
			for (int y = 0; y < Map.TileMap[0].length; y++) {
				//x_pos
				int x_pos = MainFrame.borderSizeX + x * (MainFrame.tileSizeX + MainFrame.tileSpacingX);
				//y_pos
				int y_pos = MainFrame.borderSizeY * 2 + MainFrame.topUISizeY + y * (MainFrame.tileSizeY + MainFrame.tileSpacingY);
				
				
				paintTile(g, x, y, x_pos, y_pos);
			}
		}
		
		//Draw text; game-over if (end_Loss)
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, MainFrame.tileSizeY - 3));
		g.setColor(Color.black);
		if (MainFrame.end_Loss) {
			g.drawString("Game over! Press 'R' to restart.", MainFrame.borderSizeX - 5, MainFrame.borderSizeY + 20);
			g.drawString("You cleared " + (Map.tilesDugUp) + " out of " + (MainFrame.tilesToWin) + " tiles.", MainFrame.borderSizeX - 5, MainFrame.borderSizeY + 25 + MainFrame.tileSizeY);
		} else if (MainFrame.end_Win){
			g.drawString("You've won! Press 'R' to restart.", MainFrame.borderSizeX, MainFrame.borderSizeY + 20);
			g.drawString("You cleared " + (Map.tilesDugUp) + " out of " + (MainFrame.tilesToWin) + " tiles.", MainFrame.borderSizeX - 5, MainFrame.borderSizeY + 25 + MainFrame.tileSizeY);
		} else {
			//TopUI
			g.drawString("Remaining flags: " + Map.remainingFlags, MainFrame.borderSizeX, MainFrame.borderSizeY + 20);
			g.drawString("Tiles cleared: " + Map.tilesDugUp, MainFrame.borderSizeX, MainFrame.borderSizeY + 50);
		}
	}
	
	public void paintTile(Graphics g, int tileX, int tileY, int posX, int posY) {
		Color tileColor, stringColor; 
		//int edgeSize = 5; 
		
		if (Map.TileMap[tileX][tileY].isChecked) {
			tileColor = Color.lightGray; 
		}
		else
		{
			if(Map.TileMap[tileX][tileY].isFlagged) {
				tileColor = Color.red; 
			}
			else {
				tileColor = Color.gray; 
			}
		}
		g.setColor(tileColor);
		g.fillRect(posX, posY, MainFrame.tileSizeX, MainFrame.tileSizeY);
		//If the player has lost the game, draw a mine on the current tile if it 'hasMine'. 
		if ((MainFrame.end_Loss || MainFrame.end_Win) && Map.TileMap[tileX][tileY].hasMine) {
			//Set the color of the mines. 
			g.setColor(Color.black);
			//Draw an oval that represents a mine. 
			g.fillOval(posX, posY, MainFrame.tileSizeX, MainFrame.tileSizeY);
		}
		if (!Map.TileMap[tileX][tileY].hasMine) {
			if(Map.TileMap[tileX][tileY].isChecked && Map.TileMap[tileX][tileY].surroundingMines > 0) {
				stringColor = Color.black; 
				g.setColor(stringColor); 
				g.drawString("" + Map.TileMap[tileX][tileY].surroundingMines, posX + MainFrame.tileSpacingX, posY + MainFrame.tileSizeY - MainFrame.tileSpacingY); 	
			}
		}
	}
}