package Models;

import Tree.NeuralNetworkTree;
import Tree.Node;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private final NeuralNetworkTree modelTree = new NeuralNetworkTree();

    public NeuralNetworkTree getModelTree() {
        return modelTree;
    }

    public void add(Node x1) {
        modelTree.addRoot(x1);
    }

    public void add(Node child, Node parent) {
        modelTree.add(child, parent);
    }

    public void addJump(Node n1, Node n2) {
        List<Node> jump = new ArrayList<>();
        jump.add(n1);
        jump.add(n2);
        getModelTree().getJumps().add(jump);
    }

    public void addShortcut(Node n1,Node n2){
        List<Node> shortcut = new ArrayList<>();
        shortcut.add(n1);
        shortcut.add(n2);
        getModelTree().getShortcuts().add(shortcut);
    }
}
