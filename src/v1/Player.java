package v1;

import java.awt.Point;

class Player {
  float x, y;
  int tileX, tileY;
  float movespeed = 0.1f;

  public Player(){
    reset();
  }

  void reset(){
    x = Settings.startingPoint.x;
    y = Settings.startingPoint.y;
  }

  void update(long elapsedTime, float leftRight, float upDown) {
    y += upDown * movespeed * elapsedTime;
    x += leftRight * movespeed * elapsedTime;
    //x_pos
		Point newPos = Settings.tileFromPixels((int)x, (int)y);
    tileX = newPos.x;
    tileY = newPos.y;
  }
}
