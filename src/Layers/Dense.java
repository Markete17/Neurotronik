package Layers;

public class Dense {
    private final double vector;

    public Dense(double vector) {
        this.vector = vector;
        this.checkError();
    }

    public double getVector() {
        return vector;
    }

    public void checkError() {
        if (this.vector <= 0) {
            throw new RuntimeException("The Dense function is poorly defined: (Vector must be a positive number.)");
        }
    }
}
