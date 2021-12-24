package v1;

import java.awt.Point;

public class Settings {
  static int
    tileSizeX = 30, 
    tileSizeY = 30,
    tileCountX = 16,
    tileCountY = 16,
    mineCount = 40,
    tileSpacingX = 3, 
    tileSpacingY = 3,
    borderSizeX = 20,
    borderSizeY = 20,
    topUISizeY = 50;
  
  static boolean debug = false;

  static float 
    renderingAngle = 0.5f,
    sprintSpeedModifier = 2f;
  
  static Point startingPoint = pixelsFromTile(tileCountX / 2, tileCountY / 2);
  
  static Point pixelsFromTile(int x, int y) {
    return new Point(
      // x_pos      
      borderSizeX + x * (tileSizeX + tileSpacingX)
      ,
      //y_pos
      borderSizeY * 2 + topUISizeY + y * (tileSizeY + tileSpacingY)
    );
  }

  static Point tileFromPixels(int x, int y) {
    return new Point(
      // x_pos
      (int)(
        (float)(x - (borderSizeX)) 
        / 
        (float)(tileSizeX + tileSpacingX)
      ),
      //y_pos
      (int)(
        (float)(y - (borderSizeY * 3 + topUISizeY)) 
        / 
        (float)(tileSizeY + tileSpacingY)
      )
    );
  }
}