package Layers;

import Data.Tuple;

public class MaxPooling2D {
    private final Tuple tuple;

    public MaxPooling2D(Tuple tuple) {
        this.tuple = tuple;
        this.checkError();
    }

    public Tuple getTuple() {
        return tuple;
    }

    public void checkError() {
        if (this.tuple.getN1() <= 0 || this.tuple.getN2() <= 0) {
            throw new RuntimeException("The MaxPooling2D function is poorly defined: (Only positive numbers.)");
        }
    }
}
