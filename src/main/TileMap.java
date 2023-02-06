package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import utils.Point;
import utils.Settings;
import utils.Util;

public class TileMap implements Map {

	private Tile[][] tileMap;
	private int remainingFlags;
	private int tilesCleared;
	
	/**
	 * @param startingPoint
	 * @param xSize
	 * @param ySize
	 * @param mineCount
	 */
	public TileMap(Point startingPoint, int xSize, int ySize, int mineCount) {
		//Initialize TileMap
		tileMap = new Tile[xSize][ySize];
		
		//Generate a set of random point on the map.
		Point[] mineMap = new Point[mineCount];
		mineMap = generateMineMap(startingPoint, xSize, ySize, mineCount);
		
		//Populate TileMap with Tiles
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				tileMap[x][y] = new Tile();
			}
		}
		
		for (int n = 0; n < mineMap.length; n++) {
			tileMap[mineMap[n].x()][mineMap[n].y()].hasMine = true;
			
			// tilesDugUp = 0;
			//DEBUG actions
			if (Settings.debug) { 
				tileMap[mineMap[n].x()][mineMap[n].y()].isFlagged = true;
				remainingFlags = 0;
			}
		}
		
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				tileMap[x][y].surroundingMines = calcSurroundingMines(x, y);
			}
		}
	}

	/**
	 * A method that generates a random point, and then checks that isn't already in rndMap.
	 * @param mineCount
	 */
	private Point[] generateMineMap(Point startPoint, int width, int height, int mineCount) {
		//Generate an ArrayList with points representing each point in the TileMap.
		ArrayList<Point> pointSelectionList = new ArrayList<Point>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pointSelectionList.add(new Point(x, y));
			}
		}

		// Remove the first point clicked by the player - so that no mine can be generated there.
		pointSelectionList.remove(startPoint);
		
		//Generate array of Points to return
		Point[] returnArray = new Point[mineCount];
		
		/*
		 * For each mine to be added, select a random point within the 'pointSelectionList' and add it to the 'returnArray'. 
		 */
		int curr;
		for (int n = 0; n < mineCount; n++) {
			//Generate a random number within the bounds of the 'pointSelectionList'. -1 to keep within bounds.
			curr = Util.randomNumber(0, pointSelectionList.size() - 1);
			//Pick the point at this number.
			returnArray[n] = pointSelectionList.get(curr).copy();
			//Each time a point is selected, remove it from the 'pointSelectionList' to avoid doubles.
			pointSelectionList.remove(curr);
		}
		
		//Return the finished array of 'mineCount' amount of Points, at which mines will be placed.
		return returnArray;
	}
	
	/**
	 * A method that checks the 8 surrounding tiles for mines.
	 * @param x
	 * @param y
	 * @return
	 */
	private int calcSurroundingMines(int x, int y) {
		int sum = 0;
		Point[] surroundingTiles = getSurroundingTiles(x, y);
		for (Point p : surroundingTiles) {
			if(tileMap[p.x()][p.y()].hasMine) {
				sum++;
			}
		}	
		return sum;
	}
	
	private Point[] getSurroundingTiles(int x, int y) {
		//Generate an ArrayList with points representing points in the TileMap.
		ArrayList<Point> surroundingTilesList = new ArrayList<Point>();
		/*
		 * For each tile in a 9-tile square centered on the tile in question;
		 * If it exists, and is not the 'tile in question';
		 * Add it to the list of 'surroundingTilesList'
		 */
		for (int dx = -1; dx < 2; dx++) {
			for (int dy = -1; dy < 2; dy++) {
				if (isTileWithinBounds(new Point(x + dx, y + dy)) && !(dx == 0 && dy == 0)) {
					surroundingTilesList.add(new Point(x + dx, y + dy));
				}
			}
		}
		
		//Convert the ArrayList to an array and return the finished product.
		return surroundingTilesList.toArray(new Point[0]);
	}

	private Point[] getNext(int x, int y) {
		//Generate an ArrayList with points representing each point in the TileMap.
		ArrayList<Point> surroundingTilesList = new ArrayList<Point>();
		/*
		* For each tile next to the tile in question;
		* If it exists, add it to the list of 'surroundingTilesList'
		*/
		Point[] points = {
			new Point(x - 1, y),
			new Point(x + 1, y),
			new Point(x, y - 1),
			new Point(x, y + 1)
		};
		for (Point p : points) {
			if (isTileWithinBounds(p)) {
				surroundingTilesList.add(p);
			}
		}

		//Convert the ArrayList to an array and return the finished product.
		return surroundingTilesList.toArray(new Point[0]);
	}

	// A flood-fill function that find an interconnected area
	private Point[] getAdjecentZeroes(Point p) {
    // Initialize a queue. Add the starting point to it.
		Queue<Point> frontier = new LinkedList<Point>();
		frontier.add(p);

		// Create an array and fill it with false. 
		Boolean[][] reached = new Boolean[tileMap.length][tileMap[0].length];
		for (int i = 0; i < tileMap.length; i++){
			Arrays.fill(reached[i], false);
		}
		reached[p.x()][p.y()] = true;
    
		while (!frontier.isEmpty()) {
			Point current = frontier.remove();

			for (Point next : getNext(current.x(), current.y())) {
				if (!reached[next.x()][next.y()]) {
					reached[next.x()][next.y()] = true;
					if (getSurroundingMines(next) == 0) {
						frontier.add(next);
						if (isFlagged(next)) {
							setFlagged(next, false);
						}
					}
				}
			}
		}

		// Generate an empty ArrayList.
		ArrayList<Point> res = new ArrayList<Point>();
		// Iterate through "reached" and add all the reached points to res.
		for (int i = 0; i < reached.length; i++){
			for (int j = 0; j < reached[0].length; j++) {
				if (reached[i][j]) {
					res.add(new Point(i, j));
				}
			}
		}
		//Convert the ArrayList to an array and return it.
		return res.toArray(new Point[0]);
	}
	
	/**
	 * A method that checks if the specified tile is within the bounds.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isTileWithinBounds(Point p) {
		//If the point is within the bounds, then return 'true':
		return (p.x() >= 0 && p.x() < tileMap.length &&
				p.y() >= 0 && p.y() < tileMap[0].length);
	}

	// Checks a tile. 
	private void check(Point p) {
		//Check if it's already checked - if it is, don't bother redoing it - this to avoid counting the point twice.
		if (!isChecked(p)) {
			tilesCleared++;
			setChecked(p, true);
		}
	}

	/**
	 * When a tile is turned _Checked_, it's either a mine (in which case you lose) or not (in which case a chain reaction might be appropriate).
	 * @param x
	 * @param y
	 */
	public boolean checkTile(Point p1) {
		if(!isFlagged(p1)) {
			if(tileMap[p1.x()][p1.y()].hasMine) {
				return true;
			}
			else
			{
				check(p1);
				
				//If the tile has no nearby mines; clear all adjecent tiles with no surrounding mines.
				if(tileMap[p1.x()][p1.y()].surroundingMines == 0) {

					//Get surrounding tiles and check each of them. 
					for (Point p : getAdjecentZeroes(p1)) {
						check(p);
						
						if (isFlagged(p)) {
							setFlagged(p, false);
						}
					}
				}
			}
		}
		return false;
	}

    public boolean isChecked(Point tilePos) {
        return tileMap[tilePos.x()][tilePos.y()].isChecked;
    }

    public boolean isFlagged(Point tilePos) {
        return tileMap[tilePos.x()][tilePos.y()].isFlagged;
    }

	public void setChecked(Point tilePos, boolean b) {
		tileMap[tilePos.x()][tilePos.y()].isChecked = b;
	}

    public void setFlagged(Point tilePos, boolean b) {
		tileMap[tilePos.x()][tilePos.y()].isFlagged = b;
		remainingFlags += b ? 1 : -1;
    }

	/**
	 * This is a separate method since it has to be checked both when a tile is cleared and when a flag is placed.
	 */
	public boolean checkWin() {
		//If all the flags are placed and all the empty tiles are dug up, the player wins.
		if (tilesCleared == Settings.tilesToWin && remainingFlags == 0) {
			//The player beat the game!
			return true;
		} else {
			return false;
		}
	}

	public void toggleFlagged(Point tilePos) {
		boolean b = tileMap[tilePos.x()][tilePos.y()].isFlagged;
		tileMap[tilePos.x()][tilePos.y()].isFlagged = !b;
		remainingFlags += b ? -1 : 1;
	}

	public String getTilesCleared() {
		return Integer.toString(tilesCleared);
	}

    public boolean hasMine(Point p) {
        return tileMap[p.x()][p.y()].hasMine;
    }

    public int getSurroundingMines(Point p) {
        return tileMap[p.x()][p.y()].surroundingMines;
    }

    public String getRemainingFlags() {
        return Integer.toString(remainingFlags);
    }
}
