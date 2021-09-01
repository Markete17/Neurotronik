package layers;

import data.Tuple;
import exceptions.LayersException;

public class Conv2D {
    private double filters;
    private Tuple kernelSize;
    private Tuple strides;
    private String padding;
    private Input input;

    public Conv2D(double filters, Tuple kernelSize, Tuple strides, String padding) throws LayersException {
        setParameters(filters, kernelSize, strides, padding);
    }

    public Conv2D(double filters, Tuple kernelSize, Tuple strides, String padding, Input input) throws LayersException {
        setParameters(filters, kernelSize, strides, padding);
        this.input = input;
        input.checkError();
    }

    private void setParameters(double filters, Tuple kernelSize, Tuple strides, String padding) throws LayersException {
        this.filters = filters;
        this.kernelSize = kernelSize;
        this.strides = strides;
        this.padding = padding;
        this.checkErrors();
    }

    public double getFilters() {
        return filters;
    }

    public Tuple getKernelSize() {
        return kernelSize;
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

    private void checkErrors() throws LayersException {
        if (filters <= 0) {
            throw new LayersException("The Conv2D layer is poorly defined: (Filters must be a positive number).");
        }
        if (kernelSize.getN1() <= 0 || kernelSize.getN2() <= 0) {
            throw new LayersException("The Conv2D layer is poorly defined: (Kernel must have positive numbers).");
        }

        if (strides.getN1() <= 0 || strides.getN2() <= 0) {
            throw new LayersException("The Conv2D layer is poorly defined: (Strides must have positive numbers).");
        }
    }
}
