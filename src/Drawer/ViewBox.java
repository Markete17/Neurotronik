package Drawer;

public class ViewBox {

    private final double width;
    private final double height;
    private final double zoom;

    public ViewBox(double width, double height, double zoom) {
        this.width = width;
        this.height = height;
        this.zoom = -zoom;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getZoom() {
        return zoom;
    }
}
