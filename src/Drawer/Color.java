package Drawer;

public class Color {
    //COLOR
    private String cubeColor;
    private String kernelColor;
    private String denseColor;
    private String pyramidColor;
    private String arrowColor;
    //OPACITY
    private double layerOpacity;
    private double kernelOpacity;
    private double convOpacity; //pyramid
    private double arrowOpacity;
    private double denseOpacity;

    public Color(String cube, String kernel, String dense, String pyramid, String arrow,double layerOpacity,double kernelOpacity,double convOpacity,double arrowOpacity,double denseOpacity) {
        this.cubeColor = cube;
        this.kernelColor = kernel;
        this.denseColor = dense;
        this.pyramidColor = pyramid;
        this.arrowColor = arrow;
        this.layerOpacity=layerOpacity;
        this.kernelOpacity=kernelOpacity;
        this.convOpacity=convOpacity;
        this.arrowOpacity=arrowOpacity;
        this.denseOpacity=denseOpacity;
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
