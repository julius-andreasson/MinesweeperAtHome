package v1;

public class Settings {
  int
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

  public Settings(
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
    tileSpacingX = _tileSpacingX;
    tileSpacingY = _tileSpacingY;
    borderSizeX = _borderSizeX;
    borderSizeY = _borderSizeY;
    topUISizeY = _topUISizeY;
  }
}