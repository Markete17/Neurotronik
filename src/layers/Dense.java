package layers;

import exceptions.LayersException;

public class Dense {
    private final double vector;

    public Dense(double vector) throws LayersException {
        this.vector = vector;
        this.checkError();
    }

    public double getVector() {
        return vector;
    }

    private void checkError() throws LayersException {
        if (this.vector <= 0) {
            throw new LayersException("The Dense layer is poorly defined: (Vector must be a positive number).");
        }
    }
}
