package Main;

import Utils.Point;
import Utils.Settings;

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

	/**
	 * There are two different end variables since they keep track of two things;
	 * if the round is still going or is over; and which of the two possible outcomes was reached.
	 * end_Loss describes if the current round is lost.
	 * end_Win describes if the current round is won. 
	 * firstDig describes if the next dig will be the first. If so, generate the map after the dig. 
	 */
	private boolean firstDig = true;
  
	public Game(){
    	//Initializing the 'map', 'viewer' and 'frame' variables.
		newMap();
		state = State.ONGOING;
  	}

  	public void action(Action action, Point tilePos) {
		//This prevents any action but reset to go through if the game has been lost. 
		if (state != State.ONGOING && action != Action.RESET) {
			action = Action.NONE;
		}

		switch (action) {
			case DIG:
				if (firstDig) {
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

	private void newMap() {
		map = new Map(new Point(0, 0), Settings.tileCountX, Settings.tileCountY, Settings.mineCount);
	}

    public void clickedMine() {
		state = State.LOST;
    }

	public boolean isTileWithinBounds(Point newPos) {
		return map.isTileWithinBounds(newPos);
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
