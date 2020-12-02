package Models;

import Tree.NeuralNetworkTree;
import Tree.Node;

public class Model {

    private NeuralNetworkTree modelTree=new NeuralNetworkTree();

    public NeuralNetworkTree getModelTree() {
        return modelTree;
    }

    public void add(Node x1) {
        modelTree.addRoot(x1);
    }

    public void add(Node child,Node parent){
        modelTree.add(child,parent);
    }
}
