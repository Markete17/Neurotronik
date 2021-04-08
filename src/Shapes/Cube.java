package Shapes;

import Data.Coordinate;
import Drawer.DrawSettings;

public class Cube {

    final int NUM_COORDINATES = 8;

    /*
        Array Index

        Bottom          Up
       4-------5     6-------7
       /      /      /      /
      /      /      /      /
  0  /______/ 1  2 /______/ 3


    */

    private double x;
    private double y;
    private double z;
    private boolean isKernel = false;
    private boolean isDenseLayer = false;
    private final Coordinate[] coordinates = new Coordinate[NUM_COORDINATES + 1];

    public Cube() {
    }

    //input image
    public Cube(Coordinate coordinates, DrawSettings drawSettings) {
        this.initializeCube(coordinates, drawSettings);
    }

    /**
     * Initialize the cube given coordinates
     *
     * @param coordinate the coordinates of the cube
     */
    private void initializeCube(Coordinate coordinate, DrawSettings drawSettings) {
        this.x = coordinate.getX();
        this.y = coordinate.getY();
        this.z = coordinate.getZ();

        double x_aux = drawSettings.logWidth(this.x);
        double y_aux = this.y;
        double z_aux = drawSettings.logDepth(this.z);

        coordinates[0] = (new Coordinate(-(x_aux / 2), -(y_aux / 2), z_aux / 2));
        coordinates[1] = (new Coordinate(x_aux / 2, -(y_aux / 2), z_aux / 2));
        coordinates[2] = (new Coordinate(-(x_aux / 2), (y_aux / 2), z_aux / 2));
        coordinates[3] = (new Coordinate(x_aux / 2, y_aux / 2, z_aux / 2));
        coordinates[4] = (new Coordinate(-(x_aux / 2), -(y_aux / 2), -(z_aux / 2)));
        coordinates[5] = (new Coordinate(x_aux / 2, -(y_aux / 2), -(z_aux / 2)));
        coordinates[6] = (new Coordinate(-(x_aux / 2), y_aux / 2, -(z_aux / 2)));
        coordinates[7] = (new Coordinate(x_aux / 2, y_aux / 2, -(z_aux / 2)));

        double x_random = Math.random() * (coordinates[5].getX() - coordinates[4].getX()) + coordinates[4].getX();
        double y_random = Math.random() * (coordinates[6].getY() - coordinates[4].getY()) + coordinates[4].getY();
        coordinates[8] = new Coordinate(x_random, y_random, coordinates[4].getZ());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public boolean isKernel() {
        return isKernel;
    }

    public void setKernel(boolean kernel) {
        isKernel = kernel;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    /**
     * @return true if the cube is a dense layer, false otherwise
     */
    public boolean isDenseLayer() {
        return isDenseLayer;
    }

    public void setDenseLayer(boolean denseLayer) {
        isDenseLayer = denseLayer;
    }
}
