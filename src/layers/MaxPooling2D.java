package layers;

import data.Tuple;
import exceptions.LayersException;

public class MaxPooling2D {
    private final Tuple tuple;

    public MaxPooling2D(Tuple poolSize) throws LayersException {
        this.tuple = poolSize;
        this.checkError();
    }

    public Tuple getTuple() {
        return tuple;
    }

    private void checkError() throws LayersException {
        if (this.tuple.getN1() <= 0 || this.tuple.getN2() <= 0) {
            throw new LayersException("The MaxPooling2D layer is poorly defined: (Only positive numbers).");
        }
    }
}
