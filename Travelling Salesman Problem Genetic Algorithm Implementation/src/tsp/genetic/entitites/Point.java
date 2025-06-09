package tsp.genetic.entitites;

/**
 * Point class that represents a 2D
 * point in Euclidean space used as
 * a coordinate of specific location
 * @auhtor Gökay Gülsoy 
 * @since 29/05/2025
 */
public class Point {

    private double x; // X coordinate of point
    private double y; // Y coordinate of point
    private int locLobel; // label representing location

    public Point(double x, double y, int locLobel) {
        this.x = x;
        this.y = y;
        this.locLobel = locLobel;
    }

    public double getXCoordinate() {
        return this.x;
    }

    public double getYCoordinate() {
        return this.y;
    }

    @Override
    public String toString() {
        return "Point(" + this.x + ", " + this.y + " Loc. Label: " + this.locLobel + ")";
    }

}
