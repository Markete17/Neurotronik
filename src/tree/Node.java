package tree;

import data.Coordinate;
import data.Tuple;
import exceptions.LayersException;
import exceptions.TreeException;
import layers.*;
import shapes.Cube;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Node {
    private List<Cube> cubeList = new ArrayList<>();
    private Cube lastCube;
    private List<Node> parents;
    private final List<Node> children = new ArrayList<>();
    private final LayerController layerController;
    private Cube actualCube;

    public Node(LayerController layerController) {
        this.layerController = layerController;
    }

    public List<Cube> getCubeList() {
        return cubeList;
    }

    public void setCubeList(List<Cube> cubeList) {
        this.cubeList = cubeList;
    }

    public Cube getLastCube() {
        return lastCube;
    }

    public void setLastCube(Cube lastCube) {
        this.lastCube = lastCube;
    }

    public void setParents(List<Node> parents) {
        this.parents = parents;
    }

    public List<Node> getChildren() {
        return children;
    }

    public List<Node> getParents() {
        return parents;
    }

    public Cube getActualCube() {
        return actualCube;
    }

    public Node add(Input input) throws TreeException {
        this.hasInputLayerError();
        Cube inputCube = this.layerController.input(new Cube(new Coordinate(input.getX(), input.getY(), input.getZ()), this.layerController.getDrawSettings()));
        this.getCubeList().add(inputCube);
        setLastCube();
        this.setActualCube(this.getLastCube());

        return createAuxNode(inputCube);
    }

    public Node add(Conv2D conv2D) throws TreeException, LayersException {
        List<Cube> convolutionList;
        if (conv2D.getInput() == null) {
            checkInputLayerError();
            if (actualCube.isDenseLayer()) {
                throw new TreeException("Can not Conv2D a dense layer.");
            }
            convolutionList = this.layerController.conv2D(conv2D.getFilters(), conv2D.getKernelSize(), conv2D.getStrides(), conv2D.getPadding(), this.getActualCube());
        } else {
            this.hasInputLayerError();
            convolutionList = this.layerController.conv2D(conv2D.getFilters(), conv2D.getKernelSize(), conv2D.getStrides(), new Cube(new Coordinate(conv2D.getInput().getX(), conv2D.getInput().getY(), conv2D.getInput().getZ()), layerController.getDrawSettings()), conv2D.getPadding());
        }
        this.getCubeList().addAll(convolutionList);
        setLastCube();
        this.setActualCube(this.getLastCube());

        return createAuxNode(convolutionList);
    }

    public Node add(Deconv2D deconv2D) throws TreeException, LayersException {
        List<Cube> deconvolutionList;
        if (deconv2D.getInput() == null) {
            checkInputLayerError();
            if (actualCube.isDenseLayer()) {
                throw new TreeException("Can not Deconv2D a dense layer.");
            }
            deconvolutionList = this.layerController.deconv2D(deconv2D.getFilters(), deconv2D.getKernelSize(), deconv2D.getStrides(), deconv2D.getPadding(), this.getActualCube());
        } else {
            this.hasInputLayerError();
            deconvolutionList = this.layerController.deconv2D(deconv2D.getFilters(), deconv2D.getKernelSize(), deconv2D.getStrides(), new Cube(new Coordinate(deconv2D.getInput().getX(), deconv2D.getInput().getY(), deconv2D.getInput().getZ()), layerController.getDrawSettings()), deconv2D.getPadding());
        }
        this.getCubeList().addAll(deconvolutionList);
        setLastCube();
        this.setActualCube(this.getLastCube());
        return createAuxNode(deconvolutionList);
    }

    public void add(MaxPooling2D maxPooling2D) throws TreeException {
        checkInputLayerError();
        this.setActualCube(this.layerController.maxPooling2D(new Tuple(maxPooling2D.getTuple().getN1(), maxPooling2D.getTuple().getN2()), this.getActualCube()));
    }

    public Node add(Dense dense) {
        Cube denseCube = this.layerController.dense(dense.getVector());
        this.getCubeList().add(denseCube);
        setLastCube();
        this.setActualCube(this.getLastCube());
        return createAuxNode(denseCube);
    }

    public Node add(Concatenate concatenate) throws TreeException {
        for (Node node : concatenate.getNodes()) {
            if (node.getCubeList().isEmpty() || node.getCubeList() == null) {
                throw new TreeException("Could not concatenate because some node has no convolutions or input");
            }
        }
        Cube concatenatedCube = this.layerController.concatenate(concatenate.getNodes());
        this.getCubeList().add(concatenatedCube);
        setLastCube();
        this.setActualCube(this.getLastCube());

        return createAuxNode(concatenatedCube);
    }

    private void setLastCube() {
        if (!this.getCubeList().isEmpty()) {
            this.setLastCube(this.cubeList.get(this.cubeList.size() - 1));
        }
    }

    private void setActualCube(Cube actualCube) {
        this.actualCube = actualCube;
    }

    private void checkInputLayerError() throws TreeException {
        if (this.getCubeList().isEmpty() || this.getActualCube() == null) {
            throw new TreeException("The node does not have an input layer.");
        }
    }

    private void hasInputLayerError() throws TreeException {
        for (Cube cube : this.getCubeList()) {
            if (cube.isInputLayer()) {
                throw new TreeException("There is already an input layer in the node.");
            }
        }
    }

    private Node createAuxNode(List<Cube> cubeList) {
        Node node = new Node(layerController);
        node.setCubeList(cubeList);
        node.setLastCube(cubeList.get(cubeList.size() - 1));
        node.setActualCube(node.getLastCube());
        return node;
    }

    private Node createAuxNode(Cube cube) {
        Node node = new Node(layerController);
        List<Cube> cubes = new LinkedList<>();
        cubes.add(cube);
        node.setCubeList(cubes);
        node.setLastCube(cubeList.get(cubeList.size() - 1));
        node.setActualCube(node.getLastCube());
        return node;
    }
}
