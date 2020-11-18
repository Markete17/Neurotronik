package Shapes;

import Data.Coordinate;

public class Cube{

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
    private boolean isKernel=false;
    private boolean isDenseLayer=false;
    private Coordinate [] coordinates =new Coordinate[NUM_COORDINATES];



    public Cube() {
    }


    //input image
    public Cube(Coordinate coordinates) {
        this.initializeCube(coordinates);
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
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
    private void initializeCube(Coordinate coordinate){
        this.x=coordinate.getX();
        this.y=coordinate.getY();
        this.z=coordinate.getZ();

        coordinates[0]=(new Coordinate(-(this.x/2),-(this.y/2),this.z/2));
        coordinates[1]=(new Coordinate(this.x/2,-(this.y/2),this.z/2));
        coordinates[2]=(new Coordinate(-(this.x/2),(this.y/2),this.z/2));
        coordinates[3]=(new Coordinate(this.x/2,this.y/2,this.z/2));
        coordinates[4]=(new Coordinate(-(this.x/2),-(this.y/2),-(this.z/2)));
        coordinates[5]=(new Coordinate(this.x/2,-(this.y/2),-(this.z/2)));
        coordinates[6]=(new Coordinate(-(this.x/2),this.y/2,-(this.z/2)));
        coordinates[7]=(new Coordinate(this.x/2,this.y/2,-(this.z/2)));
    }

    public boolean isDenseLayer() {
        return isDenseLayer;
    }

    public void setDenseLayer(boolean denseLayer) {
        isDenseLayer = denseLayer;
    }
}
