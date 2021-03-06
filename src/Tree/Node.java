package Tree;

import Shapes.Cube;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final List<Cube> cubeList = new ArrayList<>();
    private Cube lastCube;
    private Node parent;
    private final List<Node> children = new ArrayList<>();

    public Node() {
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

    public void add(List<Cube> cubeList) {
        this.getCubeList().addAll(cubeList);
        if (!this.getCubeList().isEmpty()) {
            this.setLastCube(this.cubeList.get(this.cubeList.size() - 1));
        }
    }
}
