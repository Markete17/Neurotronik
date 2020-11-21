package Layers;

import Data.Coordinate;
import Data.Tuple;
import Shapes.Cube;

import java.util.ArrayList;
import java.util.List;

public class Layers {

    private Cube cube_actual=new Cube();

    public List<Cube> Conv2D(int filters, Tuple kernel_size, Tuple strides, Cube input, String padding){
        List<Cube> cubeList=new ArrayList<>();
        cube_actual=input;
        cubeList.add(input);
        Cube CNNCube=createKernel(this.cube_actual.getZ(),kernel_size);
        cubeList.add(CNNCube);
        setConvolution(filters,kernel_size,strides,padding);
        cubeList.add(this.cube_actual);
        return cubeList;
    }

    //Conv2D function without input
    public List<Cube> Conv2D(int filters,Tuple kernel_size,Tuple strides,String padding){
        List<Cube> cubeList=new ArrayList<>();
        Cube CNNCube=createKernel(this.cube_actual.getZ(),kernel_size);
        cubeList.add(CNNCube);
        setConvolution(filters,kernel_size,strides,padding);
        cubeList.add(this.cube_actual);
        return cubeList;
    }

    //Pooling
    public Cube MaxPooling2D(Tuple tuple){
        setPooling(tuple);
        return this.cube_actual;
    }

    //Dense Layer
    public List<Cube> Dense(double vector){
        List<Cube> cubeList=new ArrayList<>();
        Cube cube =new Cube(new Coordinate(10,vector,10));
        cube.setDenseLayer(true);
        this.cube_actual=cube;
        cubeList.add(cube);
        return cubeList;
    }

    private void setPooling(Tuple tuple) {
        double x= (this.cube_actual.getX())/tuple.getN1();
        double y=(this.cube_actual.getY())/tuple.getN2();

        this.setNewDimensions(x,y);
    }

    private void setConvolution(int filters,Tuple kernel_size,Tuple strides,String padding) {
        double output_w=this.cube_actual.getX();
        double output_h=this.cube_actual.getY();
        if(padding=="valid"){
            output_w= (this.cube_actual.getX()-kernel_size.getN1()+1)/strides.getN1();
            output_h= (this.cube_actual.getY()-kernel_size.getN2()+1)/strides.getN2();

        }
        this.setNewDimensions(output_w,output_h,filters);

    }

    private Cube createKernel(double z,Tuple tuple){
        Coordinate coordinates=new Coordinate(tuple.getN1(),tuple.getN2(),z);
        Cube kernel=new Cube(coordinates);
        kernel.setKernel(true);
        return kernel;
    }

    private void setNewDimensions(double x,double y,double z){
        Coordinate coordinate=new Coordinate(x,y,z);
        Cube newCube=new Cube(coordinate);
        this.cube_actual=newCube;

    }

    private void setNewDimensions(double x,double y){
        Coordinate coordinate=new Coordinate(x,y,this.cube_actual.getZ());
        Cube newCube=new Cube(coordinate);
        this.cube_actual=newCube;

    }
}
