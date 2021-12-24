package v1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JPanel;

public class PerspectiveViewer extends JPanel {
	Game game;
	Point mid;

	/**
	 * Constructor
	 */
	public PerspectiveViewer (Game _game) {
		game = _game;
		mid = new Point(
			Settings.tileCountX * (Settings.tileSizeX + Settings.tileSpacingX) / 2 + Settings.borderSizeX, 
			Settings.tileCountY * (Settings.tileSizeY + Settings.tileSpacingY) / 2 + Settings.borderSizeY + Settings.topUISizeY
		);
	}
	
	/**
	 * 
	 */
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

		// Draw player
		g.setColor(Color.GREEN);
		g.fillOval(mid.x - 10, mid.y - 10 - Settings.borderSizeY, 20, 20);
		
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
		// float angle = Settings.renderingAngle;

		Point p = new Point((int)game.player.x, (int)game.player.y);
		Point pos = Settings.pixelsFromTile(tileX, tileY);
		pos.translate(mid.x - p.x, mid.y - p.y);
		Point[] points = new Point[4];
		points[0] = pos;
		points[1] = new Point(pos.x + Settings.tileSizeX, pos.y);
		points[2] = new Point(pos.x + Settings.tileSizeX, pos.y + Settings.tileSizeY);
		points[3] = new Point(pos.x, pos.y + Settings.tileSizeY);
		// float xmod = (float)tileY / game.player.y;
		
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
		Polygon pol = new Polygon();
		pol.addPoint(points[0].x, points[0].y);
		pol.addPoint(points[1].x, points[1].y);
		pol.addPoint(points[2].x, points[2].y);
		pol.addPoint(points[3].x, points[3].y);
		g.fillPolygon(pol);
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