package v1;

import java.awt.Point;

import javax.swing.JPanel;

public class Game {
  Map map;
  JPanel viewer;
  Player player;
	InputHandler inputHandler;

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
    firstDig 	= true,
    DEBUG 		= false;
  
  public Game(InputHandler _inputHandler, boolean perspective){
		inputHandler = _inputHandler;
    tilesToWin = Settings.tileCountX * Settings.tileCountY - Settings.mineCount;

    //Initializing the 'map', 'viewer' and 'frame' variables.
		map = new Map(this, Settings.startingPoint, Settings.tileCountX, Settings.tileCountY, Settings.mineCount);

		viewer = perspective ? new PerspectiveViewer(this) : new StandardViewer(this);
		//Enable DoubleBuffering to reduce flickering.
		viewer.setDoubleBuffered(true);

		System.out.println(Settings.startingPoint.x);
    player = new Player(Settings.startingPoint.x, Settings.startingPoint.y);
  }

	void run() {
		long lastTime = 0L;
		int elapsedTime;
		boolean flag_enabled = true;
		boolean reset_enabled = true;
		while (true) {
			elapsedTime = (int)(System.currentTimeMillis() - lastTime);
			lastTime = System.currentTimeMillis();
			int leftright = 0;
			int updown = 0;
			if (inputHandler.up) {
				updown -= 1;
			}
			if (inputHandler.left) {
				leftright -= 1;
			}
			if (inputHandler.down) {
				updown += 1;
			}
			if (inputHandler.right) {
				leftright += 1;
			}
			player.update(elapsedTime, leftright, updown);
			if (inputHandler.dig) {
				action(1);
			}
			if (inputHandler.flag) {
				if (flag_enabled) {
					action(2);
					flag_enabled = false;
				}
			} else {
				flag_enabled = true;
			}
			if (inputHandler.reset) {
				if (reset_enabled) {
					action(3);
					reset_enabled = false;
				}
			} else {
				reset_enabled = true;
			}
			try {
				Thread.sleep(16L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Loop final step
			viewer.repaint();
		}
	}
  // Overloading to simplify calls when using the "player" system. 
  void action(int eventKey) {
    action(eventKey, new Point(player.tileX, player.tileY));
  }

  void action(int eventKey, Point tilePos) {
		//This prevents any action but reset to go through if the game has been lost. 
		if ((end_Loss || end_Win) && eventKey != 3) {
			eventKey = 0;
		}
		
		//Event == 0: nothing 
		//Event == 1: dig
		//Event == 2: flag
		//Event == 3: reset
		
		if(map.isTileWithinBounds(player.tileX, player.tileY)) {
			//Dig
			if(eventKey == 1) {
				if (firstDig) {
					map = new Map(this, new Point(tilePos.x, tilePos.y), Settings.tileCountX, Settings.tileCountY, Settings.mineCount);
					firstDig = false;
				}
				map.checkTile(tilePos.x, tilePos.y);
			}
			//Flag
			if(eventKey == 2 && !map.tileMap[tilePos.x][tilePos.y].isChecked) {
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
			//Restart
			if(eventKey == 3) {
				//On the next line, the Map constructor resets 'minesRemaning'.  
				map = new Map(this, new Point(0, 0), Settings.tileCountX, Settings.tileCountY, Settings.mineCount);
				end_Loss = false;
				end_Win = false;
				firstDig = true;
				// The value 'minesRemaning' is not reset at this point in this method, since it's already reset in the Map constructor that is called.
			}
			viewer.repaint();
		}
	}
}
