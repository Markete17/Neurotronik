package Layers;

public class Input {
    private final double x;
    private final double y;
    private final double z;

    public Input(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.checkError();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void checkError() {
        if (this.getX() <= 0 || this.getY() <= 0 || this.getZ() <= 0) {
            throw new RuntimeException("The Input function is poorly defined: (Only positive input numbers.)");
        }
    }
}
