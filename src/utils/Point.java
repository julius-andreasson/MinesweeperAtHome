package utils;

import java.util.Arrays;
import java.util.Collection;

public class Point {
    private int x;
    private int y;

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public Point copy() {
        return new Point(this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Point other) {
            return x == other.x && y == other.y;
        }
        return false;
    }

    /*
     * Returns the 4 adjacent tiles, **excluding** diagonally adjacent.
     */
    public static Collection<Point> getSurroundingCross(Point p) {
        return Arrays.asList(
                new Point(p.x() - 1, p.y()),
                new Point(p.x(), p.y() - 1),
                new Point(p.x(), p.y() + 1),
                new Point(p.x() + 1, p.y())
        );
    }

    /*
     * Returns the 8 adjacent tiles, **including** diagonally adjacent.
     */
    public static Collection<Point> getSurroundingSquare(Point p) {
        return Arrays.asList(
                new Point(p.x() - 1, p.y() - 1),
                new Point(p.x() - 1, p.y()),
                new Point(p.x() - 1, p.y() + 1),
                new Point(p.x(), p.y() - 1),
                new Point(p.x(), p.y() + 1),
                new Point(p.x() + 1, p.y() - 1),
                new Point(p.x() + 1, p.y()),
                new Point(p.x() + 1, p.y() + 1)
        );
    }
}
