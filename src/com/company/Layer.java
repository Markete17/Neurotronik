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
        CNNCube.createKernel(this.cube_actual.getZ(),kernel_size);
        cubeList.add(CNNCube);
        setConvolution(filters,kernel_size,strides,padding);
        cubeList.add((cloneCube(this.cube_actual)));
        return cubeList;
    }

    //Conv2D function without input
    public List<Cube> Conv2D(int filters,Tuple kernel_size,Tuple strides, String activation,String padding){
        List<Cube> cubeList=new ArrayList<>();
        Cube CNNCube=new Cube();
        CNNCube.createKernel(this.cube_actual.getZ(),kernel_size);
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
        int x= (this.cube_actual.getX())/tuple.n1;
        int y=(this.cube_actual.getY())/tuple.n2;

        this.setNewDimensions(x,y);
    }

    private Cube cloneCube(Cube cube){
        Cube clone=new Cube(new Triple(cube.getX(),cube.getY(),cube.getZ()));
        return clone;
    }

    private void setConvolution(int filters,Tuple kernel_size,Tuple strides,String padding) {
        int output_w=this.cube_actual.getX();
        int output_h=this.cube_actual.getY();
        if(padding=="valid"){
            output_w= (this.cube_actual.getX()-kernel_size.n1+1)/strides.getN1();
            output_h= (this.cube_actual.getY()-kernel_size.n2+1)/strides.getN2();

        }
        this.setNewDimensions(output_w,output_h,filters);

    }

    private void setNewDimensions(int x,int y,int z){
        this.cube_actual.setX(x);
        this.cube_actual.setY(y);
        this.cube_actual.setZ(z);

    }

    private void setNewDimensions(int x,int y){
        this.cube_actual.setX(x);
        this.cube_actual.setY(y);

    }

}
