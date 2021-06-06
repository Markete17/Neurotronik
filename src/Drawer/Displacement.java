package Drawer;

public class Displacement {

    private final double layersDisplacement;//Spacing between layers
    private final double nodesDisplacement;//Spacing between nodes
    private final double parentDisplacement;//Spacing between parent and child

    public Displacement(double nodesDisplacement, double layersDisplacement, double parentDisplacement) {
        this.nodesDisplacement = nodesDisplacement;
        this.layersDisplacement = layersDisplacement;
        this.parentDisplacement = parentDisplacement;
        this.checkErrors();
    }

    public double getDisplacementLayers() {
        return layersDisplacement;
    }

    public double getNodesDisplacement() {
        return nodesDisplacement;
    }

    public double getParentDisplacement() {
        return parentDisplacement;
    }

    public void checkErrors() {
        if (this.layersDisplacement < 0) {
            throw new RuntimeException("Layers Distance must be a positive number.");
        }
        if (this.nodesDisplacement < 0) {
            throw new RuntimeException("Nodes Distance must be a positive number.");
        }
        if (this.parentDisplacement < 0) {
            throw new RuntimeException("Parent Distance must be a positive number.");
        }
    }
}
