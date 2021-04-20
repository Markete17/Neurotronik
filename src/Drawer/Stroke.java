package Drawer;

public class Stroke {
    private final String stroke_color;
    private final double stroke_width;

    public Stroke(String stroke_color, double stroke_width) {
        this.stroke_color = stroke_color;
        this.stroke_width = stroke_width;
        this.checkErrors();
    }

    public String getStroke_color() {
        return stroke_color;
    }

    public double getStroke_width() {
        return stroke_width;
    }

    public void checkErrors() {
        if (stroke_width < 0) {
            throw new RuntimeException("Stroke width must be a positive number.");
        }
    }
}
