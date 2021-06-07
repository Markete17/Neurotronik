package drawing;

import exceptions.DrawingException;

public class Stroke {
    private final String strokeColor;
    private final double strokeWidth;

    public Stroke(String strokeColor, double strokeWidth) throws DrawingException {
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

    private void checkErrors() throws DrawingException {
        if (strokeWidth < 0) {
            throw new DrawingException("Stroke width must be a positive number.");
        }
    }
}
