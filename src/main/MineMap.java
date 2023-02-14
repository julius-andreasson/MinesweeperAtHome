package main;

import java.util.*;

import utils.Point;
import utils.Settings;

public class MineMap {
    private final Tile[][] tileMap;
	private final int xSize;
	private final int ySize;

    public MineMap(int xSize, int ySize, Point startingPoint) {
		this.xSize = xSize;
        this.ySize = ySize;

        //Initialize TileMap
		tileMap = new Tile[xSize][ySize];

		//Populate TileMap with Tiles
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				tileMap[x][y] = new Tile();
			}
		}

		//Generate a set of random point on the map.
		var mineMap = generateMineMap(startingPoint, xSize, ySize);

		for (var p : mineMap) {
			tileMap[p.x()][p.y()].hasMine = true;
		}

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				tileMap[x][y].surroundingMines = calcSurroundingMines(new Point(x, y));
			}
		}
	}

    private List<Point> generateMineMap(Point startPoint, int width, int height) {
		//Generate an ArrayList with points representing each point in the TileMap.
		ArrayList<Point> pointSelectionList = new ArrayList<>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pointSelectionList.add(new Point(x, y));
			}
		}

		// Remove the first point clicked by the player, so that no mine can be generated there.
		pointSelectionList.remove(startPoint);
		
		//Generate array of Points to return
		var minePositions = new ArrayList<Point>();
		
		/*
		 * For each mine to be added, select a random point within the 'pointSelectionList' and add it to the 'minePositions'.
		 */
		for (var n = 0; n < Settings.mineCount; n++) {
			//Generate a random number within the bounds of the 'pointSelectionList'. -1 to keep within bounds.
			int curr = randomNumber(pointSelectionList.size() - 1);
			//Pick the point at this number.
			minePositions.add(pointSelectionList.get(curr).copy());
			//Each time a point is selected, remove it from the 'pointSelectionList' to avoid doubles.
			pointSelectionList.remove(curr);
		}
		
		//Return the finished array of 'mineCount' amount of Points, at which mines will be placed.
		return minePositions;
	}

	/**
	 * @return a random integer 0 <= int <= max.
	 */
	private static int randomNumber(int max) {
		return (int)Math.round(Math.random() * max);
	}

	/**
	 * A method that checks the 8 surrounding tiles for mines.
	 */
	private int calcSurroundingMines(Point curr) {
		return (int) Point.getSurroundingSquare(curr)
				.stream()
				.filter(this::isTileWithinBounds)
				.filter(p -> tileMap[p.x()][p.y()].hasMine)
				.count();
	}

    /**
	 * A method that checks if the specified tile is within the bounds.
	 */
	public boolean isTileWithinBounds(Point p) {
		//If the point is within the bounds, then return 'true':
		return (p.x() >= 0 && p.x() < width() &&
				p.y() >= 0 && p.y() < height());
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

    public int width() {
        return xSize;
    }

    public int height() {
        return ySize;
    }

    public MineMap reset(Point selectedTile) {
        return new MineMap(xSize, ySize, selectedTile);
    }
}
