package settings;

import exceptions.SettingsException;

public class Stroke {
    private final String strokeColor;
    private final double strokeWidth;

    public Stroke(String strokeColor, double strokeWidth) throws SettingsException {
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

    private void checkErrors() throws SettingsException {
        if (strokeWidth < 0) {
            throw new SettingsException("Stroke width must be a positive number.");
        }
    }
}
