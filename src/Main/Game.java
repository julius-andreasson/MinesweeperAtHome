package Main;

import Utils.Point;
import javax.swing.JPanel;

enum Action {
	NONE,
	DIG,
	FLAG,
	RESET;
}

public class Game {
	Map map;
	JPanel viewer;
	InputHandler inputHandler = new InputHandler();
	int tilesToWin;

	/**
	 * There are two different end variables since they keep track of two things;
	 * if the round is still going or is over; and which of the two possible outcomes was reached.
	 * end_Loss describes if the current round is lost.
	 * end_Win describes if the current round is won. 
	 * firstDig describes if the next dig will be the first. If so, generate the map after the dig. 
	 * DEBUG describes if the game is in debug mode. 
	 */
	boolean 
		end_Loss 	= false,
		end_Win 	= false, 
		firstDig 	= true;
  
	public Game(){
    	tilesToWin = Settings.tileCountX * Settings.tileCountY - Settings.mineCount;

    	//Initializing the 'map', 'viewer' and 'frame' variables.
		map = new Map(this, Settings.startingPoint, Settings.tileCountX, Settings.tileCountY, Settings.mineCount);

		viewer = new StandardViewer(this);
  	}

	void run() {
		while (true) {
			try {
				Thread.sleep(16L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Loop final step
			viewer.repaint();
		}
	}

  	void action(Action action, Point tilePos) {
		//This prevents any action but reset to go through if the game has been lost. 
		if ((end_Loss || end_Win) && action != Action.RESET) {
			action = Action.NONE;
		}

		switch (action) {
			case DIG:
				if (firstDig) {
					map = new Map(this, new Point(tilePos.x, tilePos.y), Settings.tileCountX, Settings.tileCountY, Settings.mineCount);
					firstDig = false;
				}
				map.checkTile(tilePos.x, tilePos.y);
				break;
			case FLAG:
				if(!map.tileMap[tilePos.x][tilePos.y].isChecked) {
					if(map.tileMap[tilePos.x][tilePos.y].isFlagged) {
						map.tileMap[tilePos.x][tilePos.y].isFlagged = false; 
						map.remainingFlags++; 
					}
					else
					{
						map.tileMap[tilePos.x][tilePos.y].isFlagged = true; 
						map.remainingFlags--; 
						map.checkWin();	
					}
				}
				break;
			case RESET:
				//On the next line, the Map constructor resets 'minesRemaning'.  
				map = new Map(this, new Point(0, 0), Settings.tileCountX, Settings.tileCountY, Settings.mineCount);
				end_Loss = false;
				end_Win = false;
				firstDig = true;
				// The value 'minesRemaning' is not reset at this point in this method, since it's already reset in the Map constructor that is called.
				break;
			default:
				break;
		}
		viewer.repaint();
	}
}
