package Tree;

import Shapes.Cube;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NeuralNetworkTree {
    private Node root;
    private List<Node>[] nodes;
    private final List<List<Node>> jumps = new ArrayList<>();

    public List<Node>[] getNodes() {
        return nodes;
    }

    public List<List<Node>> getJumps() {
        return jumps;
    }

    /**
     * Creates an empty tree.
     */
    public NeuralNetworkTree() {
        root = null;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public boolean isLeaf(Node node) {
        return (node.getChildren() == null) || (node.getChildren().isEmpty());
    }

    public boolean isRoot(Node node) {
        return (node == this.root());
    }

    private Node root() throws RuntimeException {
        if (root == null) {
            throw new RuntimeException("There is no parent node in the model.");
        }
        return root;
    }

    public void addRoot(Node node) {
        if (isEmpty()) {
            this.root = node;
        } else {
            throw new RuntimeException("The model already has a parent node.");
        }


    }

    public void add(Node child, Node parent) {
        parent.getChildren().add(child);
        child.setParent(parent);
    }

    public void initializeNodes() {
        int maxDepth = maxDepth(root());
        this.nodes = new List[maxDepth];
        for (int i = 0; i < maxDepth; i++) {
            this.nodes[i] = new ArrayList<>();
        }
        levels(root(), maxDepth);
        check();
        Collections.reverse(Arrays.asList(this.nodes));
    }

    private void levels(Node node, int maxDepth) {
        if (node != null) {
            if (this.isLeaf(node)) {
                if (!this.nodes[maxDepth - 1].contains(node))
                    this.nodes[maxDepth - 1].add(node);
            } else {
                int level = level(node);
                if (!this.nodes[level].contains(node)) {
                    this.nodes[level].add(node);
                }
                for (Node child : node.getChildren()) {
                    levels(child, maxDepth);
                }
            }
        }
    }

    private int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }

        int max = Integer.MIN_VALUE;

        for (Node child : root.getChildren()) {
            max = Math.max(max, maxDepth(child));
        }

        return 1 + Math.max(max, 0);
    }

    private int level(Node node) {
        int l = 0;
        while (!isParent(node)) {
            node = node.getParent();
            l++;
        }
        return l;
    }

    public double greaterDepthChild(List<Node> children) {
        double max = Double.MIN_VALUE;
        for (Node child : children) {
            if (child.getLastCube().getCoordinates()[0].getZ() > max) {
                max = child.getLastCube().getCoordinates()[0].getZ();
            }
        }
        return max;
    }

    public Node findLastChild(List<Node> nodes) {
        double max = Double.MIN_VALUE;
        Node lastChild = null;
        for (Node node : nodes) {
            Cube cube = node.getCubeList().get(0);
            if (cube.getCoordinates()[1].getX() > max) {
                max = cube.getCoordinates()[1].getX();
                lastChild = node;
            }
        }
        return lastChild;
    }

    public Node findFirstChild(List<Node> nodes) {
        double min = Double.MAX_VALUE;
        Node firstChild = null;
        for (Node node : nodes) {
            Cube cube = node.getCubeList().get(0);
            if (cube.getCoordinates()[1].getX() < min) {
                min = cube.getCoordinates()[1].getX();
                firstChild = node;
            }
        }
        return firstChild;
    }

    private boolean isParent(Node node) {
        return node.getParent() == null;
    }

    /**
     * Check if the Neural network has been poorly defined.
     */
    private void check() {
        for (List<Node> nodes : this.getNodes()) {
            for (Node node : nodes) {
                if (node.getCubeList().isEmpty() || node.getCubeList() == null) {
                    throw new RuntimeException("The neural network is poorly defined. There may be a node that has not added convolutions.");
                }
            }
        }
    }
}
