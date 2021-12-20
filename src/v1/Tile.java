package v1;

public class Tile {
	
	/**
	 * A boolean value indicating whether or not the tile has been checked for mines.
	 */
	boolean isChecked = false;
	
	/**
	 * A boolean value indicating whether or not the tile is a mine or not.
	 */
	boolean hasMine;
	
	/**
	 * A boolean value indicating whether or not the tile has been flagged.
	 */
	boolean isFlagged = false;
	
	/**
	 * An integer value indicating the number of mines in the 8 adjacent tiles.
	 */
	int surroundingMines;
	
	/**
	 * Constructor method
	 */
	public Tile() {
	}
}