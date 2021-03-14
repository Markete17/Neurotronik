package Shapes;

import Data.Coordinate;

public class Pyramid {

    final int NUM_COORDINATES = 5;
    private final Coordinate[] coordinates = new Coordinate[NUM_COORDINATES];
    private Coordinate vertex;

    public Pyramid(Coordinate[] coordinates, Coordinate point) {
        this.initializePyramid(coordinates, point);
    }

    /**
     * Initialize the pyramid
     *
     * @param coordinates the coordinates of the base of pyramid
     * @param point the vertex of pyramid
     */
    private void initializePyramid(Coordinate[] coordinates, Coordinate point) {
        this.vertex = new Coordinate(point.getX(), point.getY(), point.getZ());
        this.coordinates[0] = (new Coordinate(coordinates[0].getX(), coordinates[0].getY(), coordinates[0].getZ()));
        this.coordinates[1] = (new Coordinate(coordinates[1].getX(), coordinates[1].getY(), coordinates[1].getZ()));
        this.coordinates[2] = (new Coordinate(coordinates[2].getX(), coordinates[2].getY(), coordinates[2].getZ()));
        this.coordinates[3] = (new Coordinate(coordinates[3].getX(), coordinates[3].getY(), coordinates[3].getZ()));
        this.coordinates[4] = (new Coordinate(point.getX(), point.getY(), point.getZ()));
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public Coordinate getVertex() {
        return vertex;
    }
}
