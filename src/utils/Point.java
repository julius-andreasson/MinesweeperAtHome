package utils;

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

    public Point translated(int dx, int dy) {
        return new Point(this.x + dx, this.y + dy);
    }

    public Point copy() {
        return new Point(this.x, this.y);
    }
}
