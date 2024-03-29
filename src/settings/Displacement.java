package settings;

import exceptions.SettingsException;

public class Displacement {

    private final double layersDisplacement;//Spacing between layers
    private final double nodesDisplacement;//Spacing between nodes
    private final double parentDisplacement;//Spacing between parent and child

    public Displacement(double nodesDisplacement, double layersDisplacement, double parentDisplacement) throws SettingsException {
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

    private void checkErrors() throws SettingsException {
        if (this.layersDisplacement < 0) {
            throw new SettingsException("Layers distance must be a positive number.");
        }
        if (this.nodesDisplacement < 0) {
            throw new SettingsException("Nodes distance must be a positive number.");
        }
        if (this.parentDisplacement < 0) {
            throw new SettingsException("The distance between the parent node and children must be positive.");
        }
    }
}
