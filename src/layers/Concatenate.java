package layers;

import tree.Node;

public class Concatenate {
    private final Node[] nodes;

    public Concatenate(Node... nodes) {
        this.nodes = nodes;
    }

    public Node[] getNodes() {
        return nodes;
    }
}
