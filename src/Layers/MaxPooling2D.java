package Layers;

import Data.Tuple;

public class MaxPooling2D {
    private final Tuple tuple;

    public MaxPooling2D(Tuple tuple) {
        this.tuple = tuple;
    }

    public Tuple getTuple() {
        return tuple;
    }
}
