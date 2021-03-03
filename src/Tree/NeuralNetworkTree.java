package Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NeuralNetworkTree {
    private Node root;
    private List[] nodes;

    public List<Node>[] getNodes() {
        return nodes;
    }

    /**
     * Creates an empty tree.
     */
    public NeuralNetworkTree() {
        root = null;
    }

    public boolean isEmpty() {
        return (root==null);
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

    public void initializeNodes(){
        int maxDepth=maxDepth(root());
        this.nodes=new List[maxDepth];
        for(int i=0;i<maxDepth;i++){
            this.nodes[i]=new ArrayList<>();
        }
        levels(root(),0,maxDepth);
        Collections.reverse(Arrays.asList(this.nodes));
    }

    public void levels(Node node,int level,int maxDepth){
        if(node!=null){
            if(this.isLeaf(node)){
                this.nodes[maxDepth-1].add(node);
            }
            else {
                this.nodes[level(node)].add(node);
                for (Node child :node.getChildren()){
                    levels(child,level+1,maxDepth);
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

    public int level(Node node){
        int l=0;
        while(!isParent(node)){
            node=node.getParent();
            l++;
        }
        return l;
    }

    public boolean isParent(Node node){
        return node.getParent()==null;
    }
}
