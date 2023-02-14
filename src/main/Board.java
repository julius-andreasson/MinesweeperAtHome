package main;

import java.util.*;
import java.util.stream.Collectors;

import utils.Point;
import utils.Settings;

public class Board {
	private State state;
	private boolean firstDig = true;
	private MineMap map;
	private int remainingFlags = Settings.mineCount;
	private int tilesCleared;
	private Point selectedTile;
	
	public Board(Point startingPoint, int xSize, int ySize) {
		selectedTile = startingPoint;
		map = new MineMap(xSize, ySize, startingPoint);

		state = State.ONGOING;
	}

	public State getState() {
		return state;
	}

	public String getTilesCleared() {
		return Integer.toString(tilesCleared);
	}

    public boolean hasMine(Point p) {
        return map.hasMine(p);
    }

    public int getSurroundingMines(Point p) {
        return map.surroundingMines(p);
    }

    public String getRemainingFlags() {
        return Integer.toString(remainingFlags);
    }

	public boolean isChecked(Point p) {
        return map.isChecked(p);
    }

    public boolean isFlagged(Point p) {
        return map.isFlagged(p);
    }

	public void action(Action action) {
		if (map.isTileWithinBounds(selectedTile)) {
			//This prevents any action but reset to go through if the game has been lost. 
			if (state != State.ONGOING && action != Action.RESET) {
				action = Action.NONE;
			}

			switch (action) {
				case DIG -> {
					if (firstDig) {
						/* In order to avoid the risk of losing on your first dig,
						 * the first dig is handled differently.
						 */
						reset();
						firstDig = false;
					}
					if (checkTile(selectedTile)) {
						state = State.LOST;
					} else {
						checkWin();
					}
				}
				case FLAG -> flag(selectedTile);
				case RESET -> {
					//On the next line, the Map constructor resets 'minesRemaining'.
					reset();
					state = State.ONGOING;
					firstDig = true;
				}
				// The value 'minesRemaining' is not reset at this point in this method, since it's already reset in the Map constructor that is called.
				default -> {
				}
			}
		}
	}

	public void move(Direction dir, int steps) {
		int x=0, y=0;
		switch (dir) {
			case LEFT -> x--;
			case RIGHT -> x++;
			case UP -> y--;
			case DOWN -> y++;
			default -> {
			}
		}
		selectedTile.translate(x*steps, y*steps);
		selectedTile = clipToBounds(selectedTile);
    }

	private Point clipToBounds(Point p) {
		Point res = p.copy();
		res = new Point(Math.max(res.x(), 0), Math.max(res.y(), 0));
		res = new Point(Math.min(res.x(), map.width() - 1), Math.min(res.y(), map.height() - 1));
		return res;
	}

	public boolean isCurrentTile(Point p) {
		return p.equals(selectedTile);
	}

	private void flag(Point tilePos) {
		if(!isChecked(tilePos)) {
			toggleFlagged(tilePos);
			checkWin();
		}
	}

	private Set<Point> getNext(Point p) {
		/*
		* For each tile next to the tile in question;
		* If it exists (is within bounds), it should be returned.
		*/
		return Point
				.getSurroundingCross(p)
				.stream()
				.filter(map::isTileWithinBounds)
				.collect(Collectors.toSet());
	}

	// A flood-fill function that find an interconnected area
	private Collection<Point> getAdjacentZeroes(Point p) {
    	// Initialize a queue. Add the starting point to it.
		Queue<Point> frontier = new LinkedList<>();
		frontier.add(p);

		var reached = new HashSet<Point>();
		reached.add(p);
    
		while (!frontier.isEmpty()) {
			Point current = frontier.remove();

			for (Point next : getNext(current)) {
				if (!reached.contains(next)) {
					reached.add(next);
					if (getSurroundingMines(next) == 0) {
						frontier.add(next);
						if (isFlagged(next)) {
							setFlagged(next, false);
						}
					}
				}
			}
		}
		return reached;
	}

	/**
	 * When a tile is turned _Checked_, it's either a mine (in which case you lose) or not (in which case a chain reaction might be appropriate).
	 */
	private boolean checkTile(Point p) {
		if(!isFlagged(p)) {
			if(map.hasMine(p)) {
				return true;
			} else {
				check(p);
				//If the tile has no nearby mines; clear all adjacent tiles with no surrounding mines.
				if(map.surroundingMines(p) == 0) {
					//Get surrounding tiles and check each of them.
					for (Point adjacent : getAdjacentZeroes(p)) {
						check(adjacent);
						if (isFlagged(adjacent)) {
							setFlagged(adjacent, false);
						}
					}
				}
			}
		}
		return false;
	}

	// Checks a tile.
	private void check(Point p) {
		//Check if it's already checked - if it is, don't bother redoing it - this to avoid counting the point twice.
		if (!isChecked(p)) {
			tilesCleared++;
			map.setChecked(p, true);
		}
	}

	/**
	 * This is a separate method since it has to be checked both when a tile is cleared and when a flag is placed.
	 */
	public void checkWin() {
		//If all the flags are placed and all the empty tiles are dug up, the player wins.
		if (
			tilesCleared == Settings.tilesToWin &&
			remainingFlags == 0
		) {
			state = State.WON;
		}
	}

	private void toggleFlagged(Point p) {
		var b = map.isFlagged(p);
		setFlagged(p, !b);
	}

    private void setFlagged(Point tilePos, boolean b) {
		map.setFlagged(tilePos, b);
		remainingFlags += b ? -1 : 1;
    }

    private void reset() {
		tilesCleared = 0;
		remainingFlags = Settings.mineCount;
		map = map.reset(selectedTile);
	}
}