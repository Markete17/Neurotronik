package com.company;

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

    public void createKernel(double z,Tuple tuple){
        Coordinate coordinates=new Coordinate(tuple.getN1(),tuple.getN2(),z);
        this.initializeCube(coordinates);
        this.setKernel(true);
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

    private void initializeCube(Coordinate coordinate){
        this.x=coordinate.getX();
        this.y=coordinate.getY();
        this.z=coordinate.getZ();

        coordinateList.add(new Coordinate(0,0,0));
        coordinateList.add(new Coordinate((double)coordinate.getX(),0,0));
        coordinateList.add(new Coordinate(0,coordinate.getY(),0));
        coordinateList.add(new Coordinate(coordinate.getX(),coordinate.getY(),0));
        coordinateList.add(new Coordinate(0,0,coordinate.getZ()));
        coordinateList.add(new Coordinate(coordinate.getX(),0,(double)coordinate.getZ()));
        coordinateList.add(new Coordinate(0,coordinate.getY(),coordinate.getZ()));
        coordinateList.add(new Coordinate(coordinate.getX(),coordinate.getY(),coordinate.getZ()));
    }
}
