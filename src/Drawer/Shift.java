package Drawer;

public class Shift {

    private double shiftNodesZ;//SHIFT Nodes
    private double shiftNodesX;//SHIFT between nodes
    private double shiftParent;//SHIFT Parent

    public Shift(double shiftNodesZ, double shiftNodesX, double shiftParent) {
        this.shiftNodesZ = shiftNodesZ;
        this.shiftNodesX = shiftNodesX;
        this.shiftParent = shiftParent;
    }

    public double getShiftNodesZ() {
        return shiftNodesZ;
    }

    public double getShiftNodesX() {
        return shiftNodesX;
    }

    public double getShiftParent() {
        return shiftParent;
    }
}
