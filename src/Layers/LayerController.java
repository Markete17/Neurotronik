package Layers;

import Data.Coordinate;
import Data.Tuple;
import Drawer.DrawSettings;
import Shapes.Cube;
import Tree.Node;

import java.util.ArrayList;
import java.util.List;

public class LayerController {
    private final DrawSettings drawSettings;

    public LayerController(DrawSettings drawSettings) {
        this.drawSettings = drawSettings;
    }

    public DrawSettings getDrawSettings() {
        return drawSettings;
    }

    /**
     * The input image
     *
     * @param input input image is a cube
     * @return the input
     */
    public List<Cube> Input(Cube input) {
        List<Cube> cubeList = new ArrayList<>();
        input.setInputLayer(true);
        cubeList.add(input);
        return cubeList;
    }

    /**
     * CONV2D Layer
     * Is a 2D Convolution Layer, this layer creates a convolution kernel that is wind with layers input which helps produce a tensor of outputs.
     *
     * @param filters     number of filters
     * @param kernel_size size of the kernel
     * @param strides     strides tuple
     * @param input       input image
     * @param padding     padding of cnn
     * @return the list of cubes
     */
    public List<Cube> Conv2D(double filters, Tuple kernel_size, Tuple strides, Cube input, String padding, Cube actualCube) {
        List<Cube> cubeList = new ArrayList<>();
        input.setInputLayer(true);
        cubeList.add(input);
        Cube CNNCube = createKernel(actualCube.getZ(), kernel_size);
        cubeList.add(CNNCube);
        Cube convolution = setConvolution(filters, kernel_size, strides, padding, actualCube);
        cubeList.add(convolution);
        return cubeList;
    }

    //Conv2D function without input
    public List<Cube> Conv2D(double filters, Tuple kernel_size, Tuple strides, String padding, Cube actualCube) {
        List<Cube> cubeList = new ArrayList<>();
        Cube CNNCube = createKernel(actualCube.getZ(), kernel_size);
        cubeList.add(CNNCube);
        Cube convolution = setConvolution(filters, kernel_size, strides, padding, actualCube);
        cubeList.add(convolution);
        return cubeList;
    }

    /**
     * MAX POOLING 2D
     * Max pooling operation for 2D spatial data.
     * Downsamples the input representation by taking the maximum value over the window defined by pool_size for
     * each dimension along the features axis. The window is shifted by strides in each dimension.
     *
     * @param tuple x and y pooling
     */
    public Cube MaxPooling2D(Tuple tuple, Cube actualCube) {
        return setPooling(tuple, actualCube);
    }

    /**
     * DENSE LAYER
     * A dense layer is just a regular layer of neurons in a neural network.
     * Each neuron recieves input from all the neurons in the previous layer, thus densely connected
     *
     * @param vector vector length
     * @return list of cubes - dense layer
     */
    public List<Cube> Dense(double vector) {
        List<Cube> cubeList = new ArrayList<>();
        Cube cube = new Cube(new Coordinate(10, vector, 10), drawSettings);
        cube.setDenseLayer(true);
        cubeList.add(cube);
        return cubeList;
    }

    /**
     * Concatenates nodes
     *
     * @param nodes the concatenated nodes
     * @return the cube concatenated
     */
    public List<Cube> concatenate(Node... nodes) {
        List<Cube> cubeList = new ArrayList<>();
        double x = 0;
        double y = 0;
        double z = 0;
        for (Node n : nodes) {
            x += n.getLastCube().getX();
            y += n.getLastCube().getY();
            z += n.getLastCube().getZ();
        }
        Cube newCube = new Cube(new Coordinate(x, y, z), drawSettings);
        cubeList.add(newCube);
        return cubeList;
    }

    private Cube setPooling(Tuple tuple, Cube actualCube) {
        double x = (actualCube.getX()) / tuple.getN1();
        double y = (actualCube.getY()) / tuple.getN2();

        return this.setNewDimensions(x, y, actualCube.getZ());
    }

    private Cube setConvolution(double filters, Tuple kernel_size, Tuple strides, String padding, Cube actualCube) {
        double output_w = actualCube.getX();
        double output_h = actualCube.getY();
        if (strides != null && padding != null) {
            if (padding.equals("valid")) {
                output_w = (actualCube.getX() - kernel_size.getN1() + 1) / strides.getN1();
                output_h = (actualCube.getY() - kernel_size.getN2() + 1) / strides.getN2();
            } else if (padding.equals("same")) {
                output_w = (actualCube.getX()) / strides.getN1();
                output_h = (actualCube.getY()) / strides.getN2();
            } else {
                throw new RuntimeException("The padding '" + padding + "' is not supported.");
            }
        }
        return this.setNewDimensions(output_w, output_h, filters);
    }

    /**
     * Create new Kernel
     *
     * @param z     depth of previous cube
     * @param tuple kernel tuple
     * @return kernel
     */
    private Cube createKernel(double z, Tuple tuple) {
        Coordinate coordinates = new Coordinate(tuple.getN1(), tuple.getN2(), z);
        Cube kernel = new Cube(coordinates, drawSettings);
        kernel.setKernel(true);
        return kernel;
    }

    private Cube setNewDimensions(double x, double y, double z) {
        Coordinate coordinate = new Coordinate(x, y, z);
        return new Cube(coordinate, drawSettings);
    }
}
