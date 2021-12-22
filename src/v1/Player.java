package v1;

import java.awt.Point;

public class Player {
  float x;
  float y;
  int tileX;
  int tileY;
  float movespeed = 0.1f;

  public Player(int _x, int _y){
    x = _x;
    y = _y;
  }

  public void update(long elapsedTime, int leftRight, int upDown) {
    y += upDown * movespeed * elapsedTime;
    x += leftRight * movespeed * elapsedTime;
    //x_pos
		Point newPos = Settings.tileFromPixels((int)x, (int)y);
    tileX = newPos.x;
    tileY = newPos.y;
  }
}
