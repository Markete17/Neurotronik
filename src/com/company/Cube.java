package com.company;

import java.util.ArrayList;

public class Cube{

    /*
        Array Index

        Bottom          Up
       4-------5     6-------7
       /      /      /      /
      /      /      /      /
  0  /______/ 1  2 /______/ 3


    */

    private int x;
    private int y;
    private int z;
    private boolean isKernel=false;

    public Cube() {
    }


    //input image
    public Cube(Triple dimensions) {
        this.x=dimensions.getN1();
        this.y=dimensions.getN2();
        this.z=dimensions.getN3();
    }

    public void createKernel(int z,Tuple tuple){
        this.x=tuple.getN1();
        this.y=tuple.getN2();
        this.z=z;
        this.setKernel(true);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public boolean isKernel() {
        return isKernel;
    }

    public void setKernel(boolean kernel) {
        isKernel = kernel;
    }
}
