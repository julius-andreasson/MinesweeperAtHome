package v1;

import java.awt.Point;

public class Game {
  int 
    tilesToWin,
    tileCountX,
    tileCountY,
    mineCount,
    tileSizeX, 
    tileSizeY,
    tileSpacingX, 
    tileSpacingY,
    borderSizeX,
    borderSizeY,
    topUISizeY;

  Map map;
  StandardViewer viewer;
  Player player;

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
    DEBUG 		= true;
  
  public Game( 
    int _tileSizeX, 
    int _tileSizeY,
    int _tileCountX, 
    int _tileCountY, 
    int _mineCount,
    int _tileSpacingX, 
    int _tileSpacingY,
    int _borderSizeX,
    int _borderSizeY,
    int _topUISizeY
    ){
    tileSizeX = _tileSizeX;
    tileSizeY = _tileSizeY;
    tileCountX = _tileCountX;
    tileCountY = _tileCountY;
    mineCount = _mineCount;
    tilesToWin = _tileCountX * _tileCountY - _mineCount;
    tileSpacingX = _tileSpacingX;
    tileSpacingY = _tileSpacingY;
    borderSizeX = _borderSizeX;
    borderSizeY = _borderSizeY;
    topUISizeY = _topUISizeY;

    //Initializing the 'map', 'viewer' and 'frame' variables.
		map = new Map(this, new Point(0, 0), tileCountX, tileCountY, mineCount);

		viewer = new StandardViewer(this);
		//Enable DoubleBuffering to reduce flickering.
		viewer.setDoubleBuffered(true);

    // §
    player = new Player(0, 0);
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
					map = new Map(this, new Point(tilePos.x, tilePos.y), tileCountX, tileCountY, mineCount);
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
				map = new Map(this, new Point(0, 0), tileCountX, tileCountY, mineCount);
				end_Loss = false;
				end_Win = false;
				firstDig = true;
				// The value 'minesRemaning' is not reset at this point in this method, since it's already reset in the Map constructor that is called.
			}
			viewer.repaint();
		}
	}
}
