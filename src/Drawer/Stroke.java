package Drawer;

public class Stroke {
    private final String strokeColor;
    private final double strokeWidth;

    public Stroke(String strokeColor, double strokeWidth) {
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
        this.checkErrors();
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void checkErrors() {
        if (strokeWidth < 0) {
            throw new RuntimeException("Stroke width must be a positive number.");
        }
    }
}
