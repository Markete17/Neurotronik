package Drawer;

public class Color {
    private String cube;
    private String kernel;
    private String dense;
    private String pyramid;
    private String arrow;

    public Color(String cube, String kernel, String dense, String pyramid, String arrow) {
        this.cube = cube;
        this.kernel = kernel;
        this.dense = dense;
        this.pyramid = pyramid;
        this.arrow = arrow;
    }

    public String getCube() {
        return cube;
    }

    public String getKernel() {
        return kernel;
    }

    public String getDense() {
        return dense;
    }

    public String getPyramid() {
        return pyramid;
    }

    public String getArrow() {
        return arrow;
    }
}
