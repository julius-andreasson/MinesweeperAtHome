package utils;

public class Settings {
    /**
     * Tile size in the x/y-dimensions. Measured in pixels.
     */
	public static final int tileSizeX = 30;
    public static final int tileSizeY = 30;

    /**
     * Number of tiles in the x/y-dimensions.
     */
    public static final int tileCountX = 16;
    public static final int tileCountY = 16;

    /**
     * Number of mines that are to be randomly spread over the map.
     */
    public static final int mineCount = 40;

    /**
     * Spacing between tiles in the x/y-dimensions.
     * Measured in pixels.
     */
    public static final int tileSpacingX = 3;
    public static final int tileSpacingY = 3;

    /**
     * Spacing between the different parts of the playing area in the x/y-dimensions.
     * Measured in pixels.
     */
    public static final int borderSizeX = 20;
    public static final int borderSizeY = 20;

    /**
     * Size of the UI at the top of the window.
     * Measured in pixels.
     */
    public static final int topUISizeY = 50;

    /**
     * Number of tiles the player needs to clear in order to win.
     */
    public static final int tilesToWin = tileCountX * tileCountY - mineCount;

    public static final boolean debug = false;

    public static final Point startingPoint = pixelsFromTile(tileCountX / 2, tileCountY / 2);

    public static Point pixelsFromTile(int x, int y) {
        return new Point(
            // x_pos      
            borderSizeX + x * (tileSizeX + tileSpacingX)
            ,
            // y_pos
            borderSizeY * 2 + topUISizeY + y * (tileSizeY + tileSpacingY)
        );
    }

    public static Point tileFromPixels(int x, int y) {
        return new Point(
            // x_pos
            (int)(
                (float)(x - borderSizeX) 
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
