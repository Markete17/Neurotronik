package shapes;

import data.Coordinate;

public class Arrow implements Shape {

    private static final int NUM_COORDINATES = 2;
    private final Coordinate[] coordinates = new Coordinate[NUM_COORDINATES];
    private final Coordinate vertex1;
    private final Coordinate vertex2;

    public Arrow(Coordinate vertex1, Coordinate vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;

        coordinates[0] = new Coordinate(vertex1.getX(), vertex1.getY(), vertex1.getZ());
        coordinates[1] = new Coordinate(vertex2.getX(), vertex2.getY(), vertex2.getZ());
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public Coordinate getVertex1() {
        return vertex1;
    }

    public Coordinate getVertex2() {
        return vertex2;
    }
}
