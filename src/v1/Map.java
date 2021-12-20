package v1;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Map{

	Tile[][] tileMap;
	
	int 
		remainingFlags, 
		tilesDugUp;
	
	/**
	 * @param xSize
	 * @param ySize
	 * @param mineCount
	 */
	public Map(Point startingPoint, int xSize, int ySize, int mineCount) {
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
			tileMap[mineMap[n].x][mineMap[n].y].hasMine = true;
			remainingFlags = mineCount;
			tilesDugUp = 0;
			//DEBUG options
			if (MainFrame.DEBUG) { 
				tileMap[mineMap[n].x][mineMap[n].y].isFlagged = true;
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
	 * A method that checks the 8 surrounding tiles for mines.
	 * @param x
	 * @param y
	 * @return
	 */
	private int calcSurroundingMines(int x, int y) {
		int sum = 0;
		Point[] surroundingTiles = getSurroundingTiles(x, y);
		for (Point p : surroundingTiles) {
			if(tileMap[p.x][p.y].hasMine) {
				sum++;
			}
		}	
		return sum;
	}
	
	private Point[] getSurroundingTiles(int x, int y) {
				//Generate an ArrayList with points representing each point in the TileMap.
		ArrayList<Point> surroundingTilesList = new ArrayList<Point>();
		/*
		 * For each tile in a 9-tile square centered on the tile in question;
		 * If it exists, and is not the 'tile in question';
		 * Add it to the list of 'surroundingTilesList'
		 */
		for (int dx = -1; dx < 2; dx++) {
			for (int dy = -1; dy < 2; dy++) {
				if (isTileWithinBounds(x + dx, y + dy) && !(dx == 0 && dy == 0)) {
					surroundingTilesList.add(new Point(x + dx, y + dy));
				}
			}
		}
		
		//Convert the ArrayList to an array and return the finished product.
		return surroundingTilesList.toArray(new Point[0]);
	}

	// A flood-fill function that find an interconnected area
	private Point[] getAdjecentZeroes(int x, int y) {
    // Initialize a queue. Add the starting point to it.
		Queue<Point> frontier = new LinkedList<Point>();
		frontier.add(new Point(x, y));

		// Create an array and fill it with false. 
		Boolean[][] reached = new Boolean[tileMap.length][tileMap[0].length];
		for (int i = 0; i < tileMap.length; i++){
			Arrays.fill(reached[i], false);
		}
		reached[x][y] = true;
    
    while (!frontier.isEmpty()) {
			Point current = frontier.remove();

			for (Point next : getSurroundingTiles(current.x, current.y)) {
				if (!reached[next.x][next.y]) {
					reached[next.x][next.y] = true;
					if (tileMap[next.x][next.y].surroundingMines == 0) {
						frontier.add(next);
						if (tileMap[next.x][next.y].isFlagged) {
							tileMap[next.x][next.y].isFlagged = false;
							remainingFlags++;
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
	Boolean isTileWithinBounds(int x, int y) {
		//If the point is within the bounds, then return 'true':
		return (x >= 0 && x < tileMap.length &&
				y >= 0 && y < tileMap[0].length);
	}

	// Checks a tile. 
	private void check(int x, int y) {
		//Check if it's already checked - if it is, don't bother redoing it - this to avoid counting the point twice.
		if (!tileMap[x][y].isChecked) {
			tilesDugUp++;
			tileMap[x][y].isChecked = true;
			checkWin();
		}
	}

	/**
	 * When a tile is turned _Checked_, it's either a mine (in which case you lose) or not (in which case a chain reaction might be appropriate).
	 * @param x
	 * @param y
	 */
	void checkTile(int x, int y) {
		if(!tileMap[x][y].isFlagged) {
			if(tileMap[x][y].hasMine) {
				MainFrame.end_Loss = true;
			}
			else
			{
				check(x, y);
				
				//If the tile has no nearby mines; clear all adjecent tiles with no surrounding mines.
				if(tileMap[x][y].surroundingMines == 0) {

					//Get surrounding tiles and check each of them. 
					for (Point p : getAdjecentZeroes(x, y)) {
						check(p.x, p.y);
						
						if (tileMap[p.x][p.y].isFlagged) {
							tileMap[p.x][p.y].isFlagged = false;
							remainingFlags++;
						}
					}
				}
			}
		}
	}

	/**
	 * A method that generates a random point, and then checks that isn't already in rndMap.
	 * @param mineCount
	 */
	Point[] generateMineMap(Point startPoint, int width, int height, int mineCount) {
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
			curr = randomNumber(0, pointSelectionList.size() - 1);
			//Pick the point at this number.
			returnArray[n] = new Point(pointSelectionList.get(curr));
			//Each time a point is selected, remove it from the 'pointSelectionList' to avoid doubles.
			pointSelectionList.remove(curr);
		}
		
		//Return the finished array of 'mineCount' amount of Points, at which mines will be placed.
		return returnArray;
	}

	/**
	 * This is a separate method since it has to be checked both when a tile is cleared and when a flag is placed.
	 */
	void checkWin() {
		//If all the flags are placed and all the empty tiles are dug up, the player wins.
		if (tilesDugUp == MainFrame.tilesToWin && remainingFlags == 0) {
			//The player beat the game!
			MainFrame.end_Win = true;
		}
	}
	
	/**
	 * Returns a random integer min <= int <= max.
	 * @param min
	 * @param max
	 * @return
	 */
	int randomNumber(int min, int max) {
		return (int)Math.round((Math.random() * (max - min) + min));
	}
}
