package main;

import utils.Point;
import utils.Settings;

enum Action {
	NONE,
	DIG,
	FLAG,
	RESET;
}

enum State {
	ONGOING,
	LOST,
	WON
}

public class Game {
	public State state;
	private Map map;

	private boolean firstDig = true;
  
	public Game(){
    	//Initializing the 'map', 'viewer' and 'frame' variables.
		newMap();
		state = State.ONGOING;
  	}

  	public void action(Action action, Point tilePos) {
		if (isTileWithinBounds(tilePos)) {
			//This prevents any action but reset to go through if the game has been lost. 
			if (state != State.ONGOING && action != Action.RESET) {
				action = Action.NONE;
			}

			switch (action) {
				case DIG:
					if (firstDig) {
						/* In order to avoid the risk of losing on your first dig,
						 * this first dig should be handled differently.
						 * This is not implemented yet.
						 */
						newMap();
						firstDig = false;
					}
					if (map.checkTile(tilePos)) {
						clickedMine();
					}
					if (map.checkWin()) {
						state = State.WON;
					}
					break;
				case FLAG:
					if(!map.isChecked(tilePos)) {
						map.toggleFlagged(tilePos);
					}
					break;
				case RESET:
					//On the next line, the Map constructor resets 'minesRemaning'.  
					newMap();
					state = State.ONGOING;
					firstDig = true;
					// The value 'minesRemaning' is not reset at this point in this method, since it's already reset in the Map constructor that is called.
					break;
				default:
					break;
			}
		}
	}

	private void newMap() {
		map = new Map(new Point(0, 0), Settings.tileCountX, Settings.tileCountY, Settings.mineCount);
	}

    public void clickedMine() {
		state = State.LOST;
    }

	private boolean isTileWithinBounds(Point p) {
		return map.isTileWithinBounds(p);
	}

	public String getTilesCleared() {
		return map.getTilesCleared();
	}

	public boolean isChecked(Point p) {
		return map.isChecked(p);
	}

	public boolean isFlagged(Point p) {
		return map.isFlagged(p);
	}

    public boolean hasMine(Point p) {
        return map.hasMine(p);
    }

    public int getSurroundingMines(Point p) {
        return map.getSurroundingMines(p);
    }

    public String getRemainingFlags() {
        return map.getRemainingFlags();
    }
}
