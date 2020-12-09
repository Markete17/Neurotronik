package Drawer;

public class ViewBox {

    private double width;
    private double height;
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public ViewBox(double width, double height, double x1, double y1, double x2, double y2) {
        this.width = width;
        this.height = height;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }
}
