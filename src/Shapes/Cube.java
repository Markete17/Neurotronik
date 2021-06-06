package Shapes;

import Data.Coordinate;
import Drawer.DrawSettings;

public class Cube implements Shape {

    final int NUMCOORDINATES = 8;

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
    private boolean isInputLayer = false;
    private final Coordinate[] coordinates = new Coordinate[NUMCOORDINATES + 3];

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

        double xAux = drawSettings.logWidth(this.x);
        double yAux = this.y;
        double zAux = drawSettings.logDepth(this.z);

        coordinates[0] = (new Coordinate(-(xAux / 2), -(yAux / 2), zAux / 2));
        coordinates[1] = (new Coordinate(xAux / 2, -(yAux / 2), zAux / 2));
        coordinates[2] = (new Coordinate(-(xAux / 2), (yAux / 2), zAux / 2));
        coordinates[3] = (new Coordinate(xAux / 2, yAux / 2, zAux / 2));
        coordinates[4] = (new Coordinate(-(xAux / 2), -(yAux / 2), -(zAux / 2)));
        coordinates[5] = (new Coordinate(xAux / 2, -(yAux / 2), -(zAux / 2)));
        coordinates[6] = (new Coordinate(-(xAux / 2), yAux / 2, -(zAux / 2)));
        coordinates[7] = (new Coordinate(xAux / 2, yAux / 2, -(zAux / 2)));

        double xRandom = Math.random() * (coordinates[5].getX() - coordinates[4].getX()) + coordinates[4].getX();
        double yRandom = Math.random() * (coordinates[6].getY() - coordinates[4].getY()) + coordinates[4].getY();
        coordinates[8] = new Coordinate(xRandom, yRandom, coordinates[4].getZ());

        //Encoder
        coordinates[9] = new Coordinate(0, coordinates[0].getY() - 50, 0);
        coordinates[10] = new Coordinate(0, coordinates[0].getY(), 0);
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

    public boolean isInputLayer() {
        return isInputLayer;
    }

    public void setInputLayer(boolean inputLayer) {
        isInputLayer = inputLayer;
    }
}
