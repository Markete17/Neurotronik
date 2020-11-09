package com.company;

public class Coordinate {
    private double x;
    private double y;
    private double z;
    private double [][] coordinateMatrix=new double[3][1];

    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.coordinateMatrix[0][0]=x;
        this.coordinateMatrix[1][0]=y;
        this.coordinateMatrix[2][0]=z;
    }

    public Coordinate() {
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

    public double[][] getCoordinateMatrix() {
        return coordinateMatrix;
    }
}
