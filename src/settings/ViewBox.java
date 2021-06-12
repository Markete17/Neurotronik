package settings;

import exceptions.SettingsException;

public class ViewBox {

    private final double width;
    private final double height;
    private final double zoom;

    public ViewBox(double width, double height, double zoom) throws SettingsException {
        this.width = width;
        this.height = height;
        this.zoom = -zoom;
        this.checkErrors();
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

    private void checkErrors() throws SettingsException {
        if (width < 0 || height < 0) {
            throw new SettingsException("The width and height of the view box must be positives.");
        }
    }
}
