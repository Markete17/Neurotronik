package com.company;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private Cube cube_actual=new Cube();

    public List<Cube> Conv2D(int filters,Tuple tuple,String activation,Cube input){
        List<Cube> cubeList=new ArrayList<>();
        cube_actual=this.cloneCube(input);
        cubeList.add(input);
        Cube CNNCube=new Cube();
        CNNCube.createConvCube(cube_actual.getCubeList().get(4).getZ(),tuple);
        cubeList.add(CNNCube);
        setConvolution(filters);
        cubeList.add((cloneCube(this.cube_actual)));
        return cubeList;
    }

    //Conv2D function without input
    public List<Cube> Conv2D(int filters,Tuple tuple,String activation){
        List<Cube> cubeList=new ArrayList<>();
        Cube CNNCube=new Cube();
        CNNCube.createConvCube(cube_actual.getCubeList().get(4).getZ(),tuple);
        cubeList.add(CNNCube);
        setConvolution(filters);
        cubeList.add(cloneCube(this.cube_actual));
        return cubeList;
    }

    public List<Cube> MaxPooling2D(Tuple tuple){
        List<Cube> cubeList=new ArrayList<>();
        Cube CNNCube=new Cube();
        CNNCube.createConvCube(cube_actual.getCubeList().get(4).getZ(),tuple);
        cubeList.add(CNNCube);
        setPooling(tuple);
        cubeList.add(cloneCube(this.cube_actual));
        return cubeList;
    }

    private void setPooling(Tuple tuple) {
        int x= (this.cube_actual.getCubeList().get(1).getX())/tuple.n1;
        int y=(this.cube_actual.getCubeList().get(2).getY())/tuple.n2;

        this.cube_actual.getCubeList().get(2).setY(y);
        this.cube_actual.getCubeList().get(3).setY(y);
        this.cube_actual.getCubeList().get(6).setY(y);
        this.cube_actual.getCubeList().get(7).setY(y);

        this.cube_actual.getCubeList().get(1).setX(x);
        this.cube_actual.getCubeList().get(3).setX(x);
        this.cube_actual.getCubeList().get(5).setX(x);
        this.cube_actual.getCubeList().get(7).setX(x);
    }

    private Cube cloneCube(Cube c){
        Cube clone=new Cube();
        for(Coordinate3D coord:c.getCubeList()){
            clone.getCubeList().add(new Coordinate3D(coord.getX(),coord.getY(),coord.getZ()));
        }
        return clone;
    }

    private void setConvolution(int filters) {
        this.cube_actual.getCubeList().get(4).setZ(filters);
        this.cube_actual.getCubeList().get(5).setZ(filters);
        this.cube_actual.getCubeList().get(6).setZ(filters);
        this.cube_actual.getCubeList().get(7).setZ(filters);
    }

}
