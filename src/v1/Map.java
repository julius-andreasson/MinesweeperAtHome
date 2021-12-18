/**
 * 
 */
package v1;

import java.awt.Point;
import java.util.ArrayList;

public class Map{

	public static Tile[][] TileMap;
	
	public static int remainingFlags, tilesDugUp;
	
	/**
	 * @param xSize
	 * @param ySize
	 * @param mineCount
	 */
	public Map(int xSize, int ySize, int mineCount) {
		//Initialize TileMap
		TileMap = new Tile[xSize][ySize];
		
		//Generate a set of random point on the map.
		Point[] mineMap = new Point[mineCount];
		mineMap = generateMineMap(xSize, ySize, mineCount);
		
		//Populate TileMap with Tiles
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				TileMap[x][y] = new Tile();
			}
		}
		
		for (int n = 0; n < mineMap.length; n++) {
			TileMap[mineMap[n].x][mineMap[n].y].hasMine = true;
			//DEBUG options
			if (MainFrame.DEBUG) { 
				TileMap[mineMap[n].x][mineMap[n].y].isFlagged = true;
				remainingFlags = 0;
			} else {
				remainingFlags = mineCount;
			}
			Map.tilesDugUp = 0;
		}
		
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				TileMap[x][y].surroundingMines = calcSurroundingMines(x, y);
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
			if(TileMap[p.x][p.y].hasMine) {
				sum++;
			}
		}	
		return sum;
	}
	
	private static Point[] getSurroundingTiles(int posX, int posY) {
		//Generate an ArrayList with points representing each point in the TileMap.
				ArrayList<Point> surroundingTilesList = new ArrayList<Point>();
				/*
				 * For each tile in a 9-tile square centered on the tile in question;
				 * If it exists, and is not the 'tile in question';
				 * Add it to the list of 'surroundingTilesList'
				 */
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 3; y++) {
						if (isTileWithinBounds(posX - 1 + x, posY - 1 + y) && !(x==1 && y == 1)) {
							surroundingTilesList.add(new Point(posX - 1 + x, posY - 1 + y));
						}
					}
				}
				
				//Convert the ArrayList to an array and return the finished product.
				return surroundingTilesList.toArray(new Point[0]);
	}
	
	/**
	 * A method that checks if the specified tile is within the bounds.
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isTileWithinBounds(int x, int y) {
		//If the point is within the bounds, then return 'true':
		return (x >= 0 && x < TileMap.length &&
				y >= 0 && y < TileMap[0].length);
	}

	/**
	 * When a tile is turned _Checked_, it's either a mine (in which case you lose) or not (in which case a chain reaction might be appropriate).
	 * @param tileX
	 * @param tileY
	 */
	public static void checkTile(int tileX, int tileY) {
		if(!TileMap[tileX][tileY].isFlagged) {
			if(TileMap[tileX][tileY].hasMine) {
				MainFrame.end_Loss = true;
			}
			else
			{
				TileMap[tileX][tileY].Check();
				
				//If the tile has no nearby mines; as a quality of life feature, automatically check the 8 tiles directly adjacent to it.
				if(TileMap[tileX][tileY].surroundingMines == 0) {
					//Get surrounding tiles and check each of them. 
					for (Point p : getSurroundingTiles(tileX, tileY)) {
						TileMap[p.x][p.y].Check();
						
						if (TileMap[p.x][p.y].isFlagged) {
							TileMap[p.x][p.y].isFlagged = false;
							Map.remainingFlags++;
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
	public Point[] generateMineMap(int xSize, int ySize, int mineCount) {
		//Generate an ArrayList with points representing each point in the TileMap.
		ArrayList<Point> pointSelectionList = new ArrayList<Point>();
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				pointSelectionList.add(new Point(x, y));
			}
		}
		
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
	public static void checkWin() {
		//If all the flags are placed and all the empty tiles are dug up, the player wins.
		if (Map.tilesDugUp == MainFrame.tilesToWin && Map.remainingFlags == 0) {
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
