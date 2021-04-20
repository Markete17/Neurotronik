package Layers;

import Data.Tuple;

public class Conv2D {
    private final double filters;
    private final Tuple kernel_size;
    private final Tuple strides;
    private final String padding;
    private Input input;

    public Conv2D(double filters, Tuple kernel_size, Tuple strides, String padding) {
        this.filters = filters;
        this.kernel_size = kernel_size;
        this.strides = strides;
        this.padding = padding;
    }

    public Conv2D(double filters, Tuple kernel_size, Tuple strides, String padding, Input input) {
        this.filters = filters;
        this.kernel_size = kernel_size;
        this.strides = strides;
        this.padding = padding;
        this.input = input;
    }

    public double getFilters() {
        return filters;
    }

    public Tuple getKernel_size() {
        return kernel_size;
    }

    public Tuple getStrides() {
        return strides;
    }

    public String getPadding() {
        return padding;
    }

    public Input getInput() {
        return input;
    }
}
