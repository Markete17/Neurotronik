package Drawer;

public class Color {
    private String cubeColor;
    private String kernelColor;
    private String denseColor;
    private String pyramidColor;
    private String arrowColor;

    public Color(String cube, String kernel, String dense, String pyramid, String arrow) {
        this.cubeColor = cube;
        this.kernelColor = kernel;
        this.denseColor = dense;
        this.pyramidColor = pyramid;
        this.arrowColor = arrow;
    }

    public String getCube() {
        return cubeColor;
    }

    public String getKernel() {
        return kernelColor;
    }

    public String getDense() {
        return denseColor;
    }

    public String getPyramid() {
        return pyramidColor;
    }

    public String getArrow() {
        return arrowColor;
    }
}
