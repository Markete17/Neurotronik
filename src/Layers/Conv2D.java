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
        this.checkErrors();
    }

    public Conv2D(double filters, Tuple kernel_size, Tuple strides, String padding, Input input) {
        this.filters = filters;
        this.kernel_size = kernel_size;
        this.strides = strides;
        this.padding = padding;
        this.input = input;
        this.checkErrors();
        this.input.checkError();
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

    public void checkErrors() {
        if (filters <= 0) {
            throw new RuntimeException("The Conv2D function is poorly defined: (Filters must be a positive number.)");
        }
        if (kernel_size.getN1() <= 0 || kernel_size.getN2() <= 0) {
            throw new RuntimeException("The Conv2D function is poorly defined: (Kernel must have positive numbers.)");
        }

        if (strides.getN1() <= 0 || strides.getN2() <= 0) {
            throw new RuntimeException("The Input function is poorly defined: (Strides must have positive numbers.)");
        }
    }
}
