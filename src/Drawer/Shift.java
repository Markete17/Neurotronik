package Drawer;

public class Shift {

    private final double shiftLayers;//Spacing between layers
    private final double shiftNodes;//Spacing between nodes
    private final double shiftParent;//Spacing between parent and child

    public Shift(double shiftNodes, double shiftLayers, double shiftParent) {
        this.shiftNodes = shiftNodes;
        this.shiftLayers = shiftLayers;
        this.shiftParent = shiftParent;
        this.checkErrors();
    }

    public double getShiftLayers() {
        return shiftLayers;
    }

    public double getShiftNodes() {
        return shiftNodes;
    }

    public double getShiftParent() {
        return shiftParent;
    }

    public void checkErrors() {
        if (this.shiftLayers < 0) {
            throw new RuntimeException("Layers Distance must be a positive number.");
        }
        if (this.shiftNodes < 0) {
            throw new RuntimeException("Nodes Distance must be a positive number.");
        }
        if (this.shiftParent < 0) {
            throw new RuntimeException("Parent Distance must be a positive number.");
        }
    }
}
