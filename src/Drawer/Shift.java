package Drawer;

public class Shift {

    private double shiftLayers;//Spacing between layers
    private double shiftNodes;//Spacing between nodes
    private double shiftParent;//Spacing between parent and child

    public Shift(double shiftNodes, double shiftLayers, double shiftParent) {
        this.shiftNodes = shiftNodes;
        this.shiftLayers = shiftLayers;
        this.shiftParent = shiftParent;
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
}
