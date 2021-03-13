package Layers;

import Data.Coordinate;
import Data.Tuple;
import Drawer.DrawSettings;
import Shapes.Cube;
import Tree.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Layers {

    private Cube cube_actual=new Cube();
    private DrawSettings drawSettings;

    public Layers(DrawSettings drawSettings) {
        this.drawSettings = drawSettings;
    }

    public List<Cube> Input(Cube input){
        List<Cube> cubeList=new ArrayList<>();
        cube_actual=input;
        cubeList.add(input);
        return cubeList;
    }

    /** CONV2D Layer
     *  Is a 2D Convolution Layer, this layer creates a convolution kernel that is wind with layers input which helps produce a tensor of outputs.
     * @param filters
     * @param kernel_size
     * @param strides
     * @param input
     * @param padding
     * @return
     */
    public List<Cube> Conv2D(double filters, Tuple kernel_size, Tuple strides, Cube input, String padding){
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
    public List<Cube> Conv2D(double filters,Tuple kernel_size,Tuple strides,String padding){
        List<Cube> cubeList=new ArrayList<>();
        Cube CNNCube=createKernel(this.cube_actual.getZ(),kernel_size);
        cubeList.add(CNNCube);
        setConvolution(filters,kernel_size,strides,padding);
        cubeList.add(this.cube_actual);
        return cubeList;
    }

    /** MAX POOLING 2D
     * Max pooling operation for 2D spatial data.
     * Downsamples the input representation by taking the maximum value over the window defined by pool_size for
     * each dimension along the features axis. The window is shifted by strides in each dimension.
     * @param tuple
     * @return
     */
    public void MaxPooling2D(Tuple tuple){
        setPooling(tuple);
    }

    /** DENSE LAYER
     * A dense layer is just a regular layer of neurons in a neural network.
     * Each neuron recieves input from all the neurons in the previous layer, thus densely connected
     * @param vector
     * @return
     */
    public List<Cube> Dense(double vector){
        List<Cube> cubeList=new ArrayList<>();
        Cube cube =new Cube(new Coordinate(10,vector,10),drawSettings);
        cube.setDenseLayer(true);
        this.cube_actual=cube;
        cubeList.add(cube);
        return cubeList;
    }

    private void setPooling(Tuple tuple) {
        double x= (this.cube_actual.getX())/tuple.getN1();
        double y=(this.cube_actual.getY())/tuple.getN2();

        this.setNewDimensions(x,y,this.cube_actual.getZ());
    }

    private void setConvolution(double filters,Tuple kernel_size,Tuple strides,String padding) {
        double output_w=this.cube_actual.getX();
        double output_h=this.cube_actual.getY();
        if(strides!=null && padding!=null){
            if(padding.equals("valid")) {
                output_w = (this.cube_actual.getX() - kernel_size.getN1() + 1) / strides.getN1();
                output_h = (this.cube_actual.getY() - kernel_size.getN2() + 1) / strides.getN2();
            }
            if(padding.equals("same")){
                output_w = (this.cube_actual.getX())/ strides.getN1();
                output_h = (this.cube_actual.getY())/ strides.getN2();

            }
        }
        this.setNewDimensions(output_w,output_h,filters);

    }

    /**
     * Create new Kernel
     * @param z
     * @param tuple
     * @return
     */
    private Cube createKernel(double z,Tuple tuple){
        Coordinate coordinates=new Coordinate(tuple.getN1(),tuple.getN2(),z);
        Cube kernel=new Cube(coordinates,drawSettings);
        kernel.setKernel(true);
        return kernel;
    }

    private void setNewDimensions(double x,double y,double z){
        Coordinate coordinate=new Coordinate(x,y,z);
        this.cube_actual= new Cube(coordinate,drawSettings);

    }

    public List<Cube> concatenate(int mode,Node... nodes) {
        //to concatenate layers, they have to have the same dimensions
        Node node = (Node) Arrays.stream(nodes).toArray()[0];
        boolean error=false;
        for(Node n:nodes){
            if (n.getLastCube().getX() != node.getLastCube().getX() || n.getLastCube().getY() != node.getLastCube().getY() || n.getLastCube().getZ() != node.getLastCube().getZ()) {
                error = true;
                break;
            }
        }
        if(!error){
            if(mode==0){
                return new ArrayList<>();
            }
            List<Cube> cubeList=new ArrayList<>();
            Cube newCube= new Cube(new Coordinate(node.getLastCube().getX(), node.getLastCube().getY(), node.getLastCube().getZ()),drawSettings);
                cubeList.add(newCube);
                this.cube_actual = newCube;

            return cubeList;
        }
        throw new RuntimeException("The outputs are not the same size.");
    }
}
