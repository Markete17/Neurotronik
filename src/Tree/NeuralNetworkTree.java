package Tree;

import java.util.ArrayList;

public class NeuralNetworkTree {
    private Node root;


    /**
     * Creates an empty tree.
     */
    public NeuralNetworkTree() {
        root = null;
    }

    public boolean isEmpty() {
        return (root==null);
    }

    public boolean isInternal(Node node) {
        return !isLeaf(node);
    }

    public boolean isLeaf(Node node) {
        return (node.getChildren() == null) || (node.getChildren().isEmpty());
    }

    public boolean isRoot(Node node) {
        return (node == this.root());
    }

    public Node root() throws RuntimeException {
        if (root == null) {
            throw new RuntimeException("The tree is empty");
        }
        return root;
    }

    public void addRoot(Node node){
        if(isEmpty()) {
            this.root = node;
        }
        else {
            throw new RuntimeException("Tree already has a root");
        }


    }

    public void add(Node child,Node parent) {
        parent.getChildren().add(child);
        child.setParent(parent);
    }





}
