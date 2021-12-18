/**
 * 
 */
package v5;

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
	
	public void Check() {
		//Check if it's already checked - if it is, don't bother redoing it - this to avoid counting the point twice.
		if (!isChecked) {
			Map.tilesDugUp++;
			isChecked = true;
			Map.checkWin();
		}
	}
}