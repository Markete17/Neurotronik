package com.company;

import java.util.ArrayList;

public class Cube {

    private ArrayList<Coordinate3D> cubeList=new ArrayList<>();

    public Cube() {
    }

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
}
