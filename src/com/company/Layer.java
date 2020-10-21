package com.company;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private Cube cube_actual=new Cube();

    public List<Cube> Conv2D(int filters,Tuple kernel_size,Tuple strides, String activation,Cube input,String padding){
        List<Cube> cubeList=new ArrayList<>();
        cube_actual=this.cloneCube(input);
        cubeList.add(input);
        Cube CNNCube=new Cube();
        CNNCube.createKernel(cube_actual.getCubeList().get(4).getZ(),kernel_size);
        cubeList.add(CNNCube);
        setConvolution(filters,kernel_size,strides,padding);
        cubeList.add((cloneCube(this.cube_actual)));
        return cubeList;
    }

    //Conv2D function without input
    public List<Cube> Conv2D(int filters,Tuple kernel_size,Tuple strides, String activation,String padding){
        List<Cube> cubeList=new ArrayList<>();
        Cube CNNCube=new Cube();
        CNNCube.createKernel(cube_actual.getCubeList().get(4).getZ(),kernel_size);
        cubeList.add(CNNCube);
        setConvolution(filters,kernel_size,strides,padding);
        cubeList.add(cloneCube(this.cube_actual));
        return cubeList;
    }

    public Cube MaxPooling2D(Tuple tuple){
        setPooling(tuple);
        return this.cloneCube(this.cube_actual);
    }

    private void setPooling(Tuple tuple) {
        int x= (this.cube_actual.getCubeList().get(1).getX())/tuple.n1;
        int y=(this.cube_actual.getCubeList().get(2).getY())/tuple.n2;

        this.setNewDimensionsXY(x,y);
    }

    private Cube cloneCube(Cube cube){
        Cube clone=new Cube();
        for(Coordinate3D coord:cube.getCubeList()){
            clone.getCubeList().add(new Coordinate3D(coord.getX(),coord.getY(),coord.getZ()));
        }
        return clone;
    }

    private void setConvolution(int filters,Tuple kernel_size,Tuple strides,String padding) {
        if(padding=="valid"){
            int output_w= (this.cube_actual.getCubeList().get(1).getX()-kernel_size.n1+1)/strides.getN1();
            int output_h= (this.cube_actual.getCubeList().get(2).getY()-kernel_size.n2+1)/strides.getN2();

            this.setNewDimensionsXY(output_w,output_h);

        }
        this.setNewDimensionsZ(filters);

    }

    private void setNewDimensionsXY(int x,int y){

        this.cube_actual.getCubeList().get(1).setX(x);
        this.cube_actual.getCubeList().get(3).setX(x);
        this.cube_actual.getCubeList().get(5).setX(x);
        this.cube_actual.getCubeList().get(7).setX(x);

        this.cube_actual.getCubeList().get(2).setY(y);
        this.cube_actual.getCubeList().get(3).setY(y);
        this.cube_actual.getCubeList().get(6).setY(y);
        this.cube_actual.getCubeList().get(7).setY(y);

    }

    private void setNewDimensionsZ(int z){

        this.cube_actual.getCubeList().get(4).setZ(z);
        this.cube_actual.getCubeList().get(5).setZ(z);
        this.cube_actual.getCubeList().get(6).setZ(z);
        this.cube_actual.getCubeList().get(7).setZ(z);

    }

}
