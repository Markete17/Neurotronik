package tree;

import exceptions.TreeException;
import shapes.Cube;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NeuralNetworkTree {
    private Node root;
    private List<Node>[] nodes;
    private final List<List<Node>> jumps = new ArrayList<>();
    private final List<List<Node>> shortcuts = new ArrayList<>();

    public List<Node>[] getNodes() {
        return nodes;
    }

    public List<List<Node>> getJumps() {
        return jumps;
    }

    public List<List<Node>> getShortcuts() {
        return this.shortcuts;
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

    public boolean isRoot(Node node) throws TreeException {
        return (node == this.root());
    }

    public Node root() throws TreeException {
        if (root == null) {
            throw new TreeException("There is no parent node in the model.");
        }
        return root;
    }

    public void addRoot(Node node) throws TreeException {
        if (isEmpty()) {
            this.root = node;
        } else {
            throw new TreeException("The model already has a parent node.");
        }


    }

    public void add(Node child, Node parent) {
        parent.getChildren().add(child);
        if (child.getParents() == null) {
            child.setParents(new ArrayList<>());
        }
        child.getParents().add(parent);
    }

    public void initializeNodes() throws TreeException {
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
                int level = level(node, 0);
                if (!this.nodes[level].contains(node)) {
                    this.nodes[level].add(node);
                }
                for (Node child : node.getChildren()) {
                    levels(child, maxDepth);
                }
            }
        }
    }

    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }

        int max = Integer.MIN_VALUE;

        for (Node child : root.getChildren()) {
            max = Math.max(max, maxDepth(child));
        }

        return 1 + Math.max(max, 0);
    }

    public int level(Node node, int level) {
        if (node.getParents() != null) {
            level++;
            for (Node parent : node.getParents()) {
                level = Math.max(level, level(parent, level));
            }
        }
        return level;
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

    /**
     * Check if the Neural network has been poorly defined.
     */
    private void check() throws TreeException {
        for (List<Node> nodeList : this.getNodes()) {
            for (Node node : nodeList) {
                if (node.getCubeList().isEmpty() || node.getCubeList() == null) {
                    throw new TreeException("The neural network is poorly defined. There may be a node that has not added convolutions.");
                }
            }
        }
    }
}
