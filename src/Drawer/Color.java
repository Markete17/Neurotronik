package Drawer;

public class Color {
    //COLOR
    private final String cubeColor;
    private final String kernelColor;
    private final String denseColor;
    private final String pyramidColor;
    private final String arrowColor;
    //OPACITY
    private final double layerOpacity;
    private final double kernelOpacity;
    private final double convOpacity; //pyramid
    private final double arrowOpacity;
    private final double denseOpacity;

    public Color(String cube, String kernel, String dense, String pyramid, String arrow, double layerOpacity, double kernelOpacity, double convOpacity, double arrowOpacity, double denseOpacity) {
        this.cubeColor = cube;
        this.kernelColor = kernel;
        this.denseColor = dense;
        this.pyramidColor = pyramid;
        this.arrowColor = arrow;
        this.layerOpacity = layerOpacity;
        this.kernelOpacity = kernelOpacity;
        this.convOpacity = convOpacity;
        this.arrowOpacity = arrowOpacity;
        this.denseOpacity = denseOpacity;
    }

    public String getCubeColor() {
        return cubeColor;
    }

    public String getKernelColor() {
        return kernelColor;
    }

    public String getDenseColor() {
        return denseColor;
    }

    public String getPyramidColor() {
        return pyramidColor;
    }

    public String getArrowColor() {
        return arrowColor;
    }

    public double getLayerOpacity() {
        return layerOpacity;
    }

    public double getKernelOpacity() {
        return kernelOpacity;
    }

    public double getConvOpacity() {
        return convOpacity;
    }

    public double getArrowOpacity() {
        return arrowOpacity;
    }

    public double getDenseOpacity() {
        return denseOpacity;
    }
}
