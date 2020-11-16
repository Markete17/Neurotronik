package Shapes;

import Data.Coordinate;

import java.util.ArrayList;
import java.util.List;

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
    private List<Coordinate> coordinateList=new ArrayList<>(NUM_COORDINATES);



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

    public List<Coordinate> getCoordinateList() {
        return coordinateList;
    }

    private void initializeCube(Coordinate coordinate){
        this.x=coordinate.getX();
        this.y=coordinate.getY();
        this.z=coordinate.getZ();

        coordinateList.add(new Coordinate(0,0,0));
        coordinateList.add(new Coordinate(this.x,0,0));
        coordinateList.add(new Coordinate(0,this.y,0));
        coordinateList.add(new Coordinate(this.x,this.y,0));
        coordinateList.add(new Coordinate(0,0,this.z));
        coordinateList.add(new Coordinate(this.x,0,this.z));
        coordinateList.add(new Coordinate(0,this.y,this.z));
        coordinateList.add(new Coordinate(this.x,this.y,this.z));
    }
}
