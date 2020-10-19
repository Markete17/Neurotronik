package com.company;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private Cube cube_actual=new Cube();

    public List<Cube> Conv2D(int filters,Tuple tuple,String activation,Cube input){
        List<Cube> cubeList=new ArrayList<>();
        this.cube_actual=input;
        cubeList.add(input);
        Cube CNNCube=new Cube();
        CNNCube.createConvCube(cube_actual.getCubeList().get(4).getZ(),tuple);
        cubeList.add(CNNCube);
        int origincoord=this.cube_actual.getCubeList().get(0).getZ();
        for(int i=0;i<8;i++){
            Coordinate3D coord=this.cube_actual.getCubeList().get(i);
            if(coord.getZ()!=origincoord){
                this.cube_actual.getCubeList().set(i,new Coordinate3D(coord.getX(),coord.getY(),filters));
            }
        }
        return cubeList;
    }

    //Conv2D function without input
    public Cube Conv2D(int filters,Tuple tuple,String activation){
        Cube CNNCube=new Cube();
        CNNCube.createConvCube(cube_actual.getCubeList().get(4).getZ(),tuple);
        int origincoord=this.cube_actual.getCubeList().get(0).getZ();
        for(int i=0;i<8;i++){
            Coordinate3D coord=this.cube_actual.getCubeList().get(i);
            if(coord.getZ()!=origincoord){
                this.cube_actual.getCubeList().set(i,new Coordinate3D(coord.getX(),coord.getY(),filters));
            }
        }
        return CNNCube;
    }

    public Cube MaxPooling2D(Tuple tuple){
        for(int i=0;i<8;i++){
            this.cube_actual.getCubeList().get(i).setX( (this.cube_actual.getCubeList().get(i).getX())/tuple.n1);
            this.cube_actual.getCubeList().get(i).setY( (this.cube_actual.getCubeList().get(i).getY())/tuple.n2);
        }
        return this.cube_actual;
    }
}
