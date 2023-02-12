package main;

/**
 * Struct for Tile.
 */
class Tile {
	
	/**
	 * Indicates whether the tile has been checked for mines.
	 */
	boolean isChecked = false;
	
	/**
	 * Indicates whether the tile is a mine or not.
	 */
	boolean hasMine = false;
	
	/**
	 * Indicates whether the tile has been flagged.
	 */
	boolean isFlagged = false;
	
	/**
	 * Indicates the number of mines in the 8 adjacent tiles.
	 */
	int surroundingMines = 0;
	
}