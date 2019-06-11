package tsp;

public class TSPCoordinate {

    private double x;
    private double y;


    public TSPCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "x=" + x + "y=" + y;
    }
}
