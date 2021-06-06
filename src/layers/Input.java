package layers;

import exceptions.LayersException;

public class Input {
    private final double x;
    private final double y;
    private final double z;

    public Input(double x, double y, double z) throws LayersException {
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

    public void checkError() throws LayersException {
        if (this.getX() <= 0 || this.getY() <= 0 || this.getZ() <= 0) {
            throw new LayersException("The Input function is poorly defined: (Only positive input numbers.)");
        }
    }
}
