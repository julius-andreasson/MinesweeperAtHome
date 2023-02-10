package main;

import java.util.ArrayList;
import java.util.Arrays;

import utils.Point;
import utils.Settings;

public class MineMap {
    private Tile[][] tileMap;
	private int xSize;
	private int ySize;
    private int mineCount;
    private Point startingPoint;

    public MineMap(int xSize, int ySize, int mineCount, Point startingPoint) {
		this.xSize = xSize;
        this.ySize = ySize;
        this.mineCount = mineCount;
        this.startingPoint = startingPoint;

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
			
			//DEBUG actions
			if (Settings.debug) { 
				tileMap[mineMap[n].x()][mineMap[n].y()].isFlagged = true;
				//remainingFlags = 0;
			}
		}
		
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				tileMap[x][y].surroundingMines = calcSurroundingMines(x, y);
			}
		}
	}
    
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
		for (int n = 0; n < mineCount; n++) {
			//Generate a random number within the bounds of the 'pointSelectionList'. -1 to keep within bounds.
			int curr = randomNumber(0, pointSelectionList.size() - 1);
			//Pick the point at this number.
			returnArray[n] = pointSelectionList.get(curr).copy();
			//Each time a point is selected, remove it from the 'pointSelectionList' to avoid doubles.
			pointSelectionList.remove(curr);
		}
		
		//Return the finished array of 'mineCount' amount of Points, at which mines will be placed.
		return returnArray;
	}

	/**
	 * Returns a random integer min <= int <= max.
	 * @param min
	 * @param max
	 * @return
	 */
	private static int randomNumber(int min, int max) {
		return (int)Math.round((Math.random() * (max - min) + min));
	}

    	/**
	 * A method that checks the 8 surrounding tiles for mines.
	 * @param x
	 * @param y
	 * @return
	 */
	private int calcSurroundingMines(int x, int y) {
		return (int) Arrays
			.stream(getSurroundingTiles(x, y))
			.filter(p -> tileMap[p.x()][p.y()].hasMine)
			.count();
	}

    private Point[] getSurroundingTiles(int x, int y) {
		//Generate an ArrayList with points representing points in the TileMap.
		ArrayList<Point> surroundingTilesList = new ArrayList<Point>();
		/*
		 * For each tile in a 9-tile square centered on the current tile:
		 * If it exists, and is not the 'current tile',
		 * add it to the list of 'surroundingTilesList'
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

    /**
	 * A method that checks if the specified tile is within the bounds.
	 */
	public boolean isTileWithinBounds(Point p) {
		//If the point is within the bounds, then return 'true':
		return (p.x() >= 0 && p.x() < x() &&
				p.y() >= 0 && p.y() < y());
	}

    public void setFlagged(Point p, boolean b) {
        tileMap[p.x()][p.y()].isFlagged = b;
    }

    public boolean hasMine(Point p) {
        return tileMap[p.x()][p.y()].hasMine;
    }

    public int surroundingMines(Point p) {
        return tileMap[p.x()][p.y()].surroundingMines;
    }

    public boolean isFlagged(Point p) {
        return tileMap[p.x()][p.y()].isFlagged;
    }

    public boolean isChecked(Point p) {
        return tileMap[p.x()][p.y()].isChecked;
    }

    public void setChecked(Point p, boolean b) {
        tileMap[p.x()][p.y()].isChecked = b;
    }

    public int x() {
        return xSize;
    }

    public int y() {
        return ySize;
    }

    public MineMap reset(Point selectedTile) {
        return new MineMap(xSize, ySize, mineCount, startingPoint);
    }
}
