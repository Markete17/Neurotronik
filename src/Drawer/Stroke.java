package Drawer;

public class Stroke {
    private final String stroke_color;
    private final double stroke_width;

    public Stroke(String stroke_color, double stroke_width) {
        this.stroke_color = stroke_color;
        this.stroke_width = stroke_width;
    }

    public String getStroke_color() {
        return stroke_color;
    }

    public double getStroke_width() {
        return stroke_width;
    }
}
