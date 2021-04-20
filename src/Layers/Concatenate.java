package Layers;

import Tree.Node;

public class Concatenate {
    private final Node[] nodes;

    public Concatenate(Node... nodes) {
        this.nodes = nodes;
    }

    public Node[] getNodes() {
        return nodes;
    }
}
