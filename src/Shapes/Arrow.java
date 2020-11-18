package Shapes;

import Data.Coordinate;

public class Arrow {

    final int NUM_COORDINATES = 4;
    private Coordinate[] coordinates =new Coordinate[NUM_COORDINATES];
    private Coordinate vertex1;
    private Coordinate vertex2;

    public Arrow(Coordinate vertex1, Coordinate vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;

        coordinates[0]=new Coordinate(vertex1.getX(),vertex1.getY(),vertex1.getZ());
        coordinates[1]=new Coordinate(vertex2.getX(),vertex2.getY(),vertex2.getZ());
        coordinates[2]=new Coordinate(vertex2.getX()-5,vertex2.getY()-5,vertex2.getZ()-5);
        coordinates[3]=new Coordinate(vertex2.getX()-5,vertex2.getY()+5,vertex2.getZ()-5);
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }
}
