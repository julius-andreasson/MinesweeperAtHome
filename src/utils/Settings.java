package utils;

public class Settings {
    /**
     * tileSizeX sets the size of tiles in the x-dimension.
     */
	public static final int tileSizeX = 30;

    /**
     * tileSizeY sets the size of tiles in the y-dimension.
     */
    public static final int tileSizeY = 30;

    /**
     * tileCountX sets the number of tiles in the x-dimension.
     */
    public static final int tileCountX = 16;

    /**
     * tileCountY sets the number of tiles in the y-dimension.
     */
    public static final int tileCountY = 16;

    /**
     * mineCount sets the number of mines that are to be randomly spread over the map.
     */
    public static final int mineCount = 40;

    /**
     * tileSpacingX sets the size of the spacing between tiles in the x-dimension.
     */
    public static final int tileSpacingX = 3;

    /**
     * tileSpacingY sets the size of the spacing between tiles in the y-dimension.
     */
    public static final int tileSpacingY = 3;

    /**
     * borderSizeX sets the size of the spacing between the different areas of the playing area in the x-dimension.
     */
    public static final int borderSizeX = 20;

    /**
     * borderSizeY sets the size of the spacing between the different areas of the playing area in the y-dimension.
     */
    public static final int borderSizeY = 20;

    /**
     * topUISizeY sets the size of the UI at the top of the window.
     */
    public static final int topUISizeY = 50;

    public static final int tilesToWin = tileCountX * tileCountY - mineCount;

    /**
     * debug
     */
    public static boolean debug = false;

    public static Point startingPoint = pixelsFromTile(tileCountX / 2, tileCountY / 2);

    public static Point pixelsFromTile(int x, int y) {
        return new Point(
            // x_pos      
            borderSizeX + x * (tileSizeX + tileSpacingX)
            ,
            //y_pos
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
