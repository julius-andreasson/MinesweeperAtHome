package main;

import utils.Point;

public interface Map {

    boolean checkTile(Point p);

    boolean checkWin();

    boolean isChecked(Point p);

    void toggleFlagged(Point p);

    boolean isTileWithinBounds(Point p);

    String getTilesCleared();

    boolean isFlagged(Point p);

    boolean hasMine(Point p);

    int getSurroundingMines(Point p);

    String getRemainingFlags();

}
