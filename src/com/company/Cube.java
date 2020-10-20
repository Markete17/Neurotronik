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

    private ArrayList<Coordinate3D> cubeList=new ArrayList<>();

    public Cube() {
    }

    public Cube(ArrayList<Coordinate3D> cubeList) {
        this.cubeList = cubeList;
    }

    //input image
    public Cube(Triple dimensions) {
        this.cubeList.add(new Coordinate3D(0,0,0));//add origin
        this.cubeList.add(new Coordinate3D(dimensions.x,0,0));
        this.cubeList.add(new Coordinate3D(0,dimensions.y,0));
        this.cubeList.add(new Coordinate3D(dimensions.x,dimensions.y,0));
        this.cubeList.add(new Coordinate3D(0,0,dimensions.z));
        this.cubeList.add(new Coordinate3D(dimensions.x,0,dimensions.z));
        this.cubeList.add(new Coordinate3D(0,dimensions.y,dimensions.z));
        this.cubeList.add(new Coordinate3D(dimensions.x,dimensions.y,dimensions.z));
    }

    public void createConvCube(int z,Tuple tuple){
        this.cubeList.add(new Coordinate3D(0,0,0));
        this.cubeList.add(new Coordinate3D(tuple.n1,0,0));
        this.cubeList.add(new Coordinate3D(0,tuple.n2,0));
        this.cubeList.add(new Coordinate3D(tuple.n1,tuple.n2,0));
        this.cubeList.add(new Coordinate3D(0,0,z));
        this.cubeList.add(new Coordinate3D(tuple.n1,0,z));
        this.cubeList.add(new Coordinate3D(0,tuple.n2,z));
        this.cubeList.add(new Coordinate3D(tuple.n1,tuple.n2,z));
    }



    public ArrayList<Coordinate3D> getCubeList() {
        return this.cubeList;
    }

    public void setCubeList(ArrayList<Coordinate3D> cubeList) {
        this.cubeList = cubeList;
    }

}
