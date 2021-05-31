package Tree;

import Data.Coordinate;
import Data.Tuple;
import Layers.*;
import Shapes.Cube;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final List<Cube> cubeList = new ArrayList<>();
    private Cube lastCube;
    private Node parent;
    private final List<Node> children = new ArrayList<>();
    private final LayerController layerController;
    private Cube actualCube;

    public Node(LayerController layerController) {
        this.layerController = layerController;
    }

    public List<Cube> getCubeList() {
        return cubeList;
    }

    public Cube getLastCube() {
        return lastCube;
    }

    public void setLastCube(Cube lastCube) {
        this.lastCube = lastCube;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public Cube getActualCube() {
        return actualCube;
    }

    public void add(Input input) {
        this.hasInputLayerError();
        this.getCubeList().add(this.layerController.Input(new Cube(new Coordinate(input.getX(), input.getY(), input.getZ()), this.layerController.getDrawSettings())));
        setLastCube();
        this.setActualCube(this.getLastCube());
    }

    public void add(Conv2D conv2D) {
        if (conv2D.getInput() == null) {
            checkInputLayerError();
            if (actualCube.isDenseLayer()) {
                throw new RuntimeException("Can not Conv2D a dense layer.");
            }
            this.getCubeList().addAll(this.layerController.Conv2D(conv2D.getFilters(), conv2D.getKernel_size(), conv2D.getStrides(), conv2D.getPadding(), this.getActualCube()));
        } else {
            this.hasInputLayerError();
            this.getCubeList().addAll(this.layerController.Conv2D(conv2D.getFilters(), conv2D.getKernel_size(), conv2D.getStrides(), new Cube(new Coordinate(conv2D.getInput().getX(), conv2D.getInput().getY(), conv2D.getInput().getZ()), layerController.getDrawSettings()), conv2D.getPadding()));
        }
        setLastCube();
        this.setActualCube(this.getLastCube());
    }

    public void add(Deconv2D deconv2D){

        if (deconv2D.getInput() == null) {
            checkInputLayerError();
            if (actualCube.isDenseLayer()) {
                throw new RuntimeException("Can not Deconv2D a dense layer.");
            }
            this.getCubeList().addAll(this.layerController.Deconv2D(deconv2D.getFilters(), deconv2D.getKernel_size(), deconv2D.getStrides(), deconv2D.getPadding(), this.getActualCube()));
        } else {
            this.hasInputLayerError();
            this.getCubeList().addAll(this.layerController.Deconv2D(deconv2D.getFilters(), deconv2D.getKernel_size(), deconv2D.getStrides(), new Cube(new Coordinate(deconv2D.getInput().getX(), deconv2D.getInput().getY(), deconv2D.getInput().getZ()), layerController.getDrawSettings()), deconv2D.getPadding()));
        }
        setLastCube();
        this.setActualCube(this.getLastCube());
    }

    public void add(MaxPooling2D maxPooling2D) {
        checkInputLayerError();
        this.setActualCube(this.layerController.MaxPooling2D(new Tuple(maxPooling2D.getTuple().getN1(), maxPooling2D.getTuple().getN2()), this.getActualCube()));
    }

    public void add(Dense dense) {
        this.getCubeList().add(this.layerController.Dense(dense.getVector()));
        setLastCube();
        this.setActualCube(this.getLastCube());
    }

    public void add(Concatenate concatenate) {
        for (Node node : concatenate.getNodes()) {
            if (node.getCubeList().isEmpty() || node.getCubeList() == null) {
                throw new RuntimeException("Could not concatenate because some node has no convolutions or input");
            }
        }
        this.getCubeList().add(this.layerController.Concatenate(concatenate.getNodes()));
        setLastCube();
        this.setActualCube(this.getLastCube());
    }

    private void setLastCube() {
        if (!this.getCubeList().isEmpty()) {
            this.setLastCube(this.cubeList.get(this.cubeList.size() - 1));
        }
    }

    private void setActualCube(Cube actualCube) {
        this.actualCube = actualCube;
    }

    private void checkInputLayerError(){
        if (this.getCubeList().isEmpty() || this.getActualCube() == null) {
            throw new RuntimeException("The node does not have an input layer.");
        }
    }
    private void hasInputLayerError(){
        for (Cube cube : this.getCubeList()) {
            if (cube.isInputLayer()) {
                    throw new RuntimeException("There is already an input layer.");
            }
        }
    }
}
