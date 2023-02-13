package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import utils.Point;
import utils.Settings;

public class View extends JPanel {
	private final Board board;

	public View (Board board) {
		this.board = board;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//Draw left-side split
		g.setColor(Color.black);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, Settings.tileSizeX));
		
		//Draw Tiles
		for (int x = 0; x < Settings.tileCountX; x++) {
			for (int y = 0; y < Settings.tileCountY; y++) {
				paintTile(g, new Point(x, y));
			}
		}
		
		//Draw text; game-over if (end_Loss)
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, Settings.tileSizeY - 3));
		g.setColor(Color.black);
		if (board.getState() == State.LOST) {
			g.drawString("Game over! Press 'R' to restart.", Settings.borderSizeX - 5, Settings.borderSizeY + 20);
			g.drawString("You cleared " + (board.getTilesCleared()) + " out of " + (Settings.tilesToWin) + " tiles.", Settings.borderSizeX - 5, Settings.borderSizeY + 25 + Settings.tileSizeY);
		} else if (board.getState() == State.WON){
			g.drawString("You've won! Press 'R' to restart.", Settings.borderSizeX, Settings.borderSizeY + 20);
			g.drawString("You cleared " + (board.getTilesCleared()) + " out of " + (Settings.tilesToWin) + " tiles.", Settings.borderSizeX - 5, Settings.borderSizeY + 25 + Settings.tileSizeY);
		} else {
			//TopUI
			g.drawString("Remaining flags: " + board.getRemainingFlags(), Settings.borderSizeX, Settings.borderSizeY + 20);
			g.drawString("Tiles cleared: " + board.getTilesCleared(), Settings.borderSizeX, Settings.borderSizeY + 50);
		}
	}
	
	public void paintTile(Graphics g, Point p) {
		Color tileColor, stringColor; 
		Point pos = pixelsFromTile(p);

		if (board.isChecked(p)) {
			tileColor = Color.lightGray; 
		}
		else
		{
			if(board.isFlagged(p)) {
				tileColor = Color.red; 
			}
			else {
				tileColor = Color.gray; 
			}
		}
		g.setColor(tileColor);
		g.fillRect(pos.x(), pos.y(), Settings.tileSizeX, Settings.tileSizeY);
		//If the player has lost the game, draw a mine on the current tile if it 'hasMine'. 
		if (board.getState() != State.ONGOING && board.hasMine(p)) {
			//Set the color of the mines. 
			g.setColor(Color.black);
			//Draw an oval that represents a mine. 
			g.fillOval(pos.x(), pos.y(), Settings.tileSizeX, Settings.tileSizeY);
		}
		// Draw the number of mines on each checked tile. 
		if (!board.hasMine(p)) {
			if(board.isChecked(p) && board.getSurroundingMines(p) > 0) {
				stringColor = Color.black; 
				g.setColor(stringColor); 
				g.drawString("" + board.getSurroundingMines(p), pos.x() + Settings.tileSpacingX, pos.y() + Settings.tileSizeY - Settings.tileSpacingY);
			}
		}

		if (board.isCurrentTile(p)) {
			g.setColor(Color.green);
			g.fillRect(pos.x(), pos.y(), Settings.tileSizeX, Settings.tileSizeY);
		}
	}

	private static Point pixelsFromTile(Point p) {
        return new Point(
            Settings.borderSizeX + p.x() * (Settings.tileSizeX + Settings.tileSpacingX)
            ,
            Settings.borderSizeY * 2 + Settings.topUISizeY + p.y() * (Settings.tileSizeY + Settings.tileSpacingY)
        );
    }
}