package drawing;

import exceptions.DrawingException;

public class ViewBox {

    private final double width;
    private final double height;
    private final double zoom;

    public ViewBox(double width, double height, double zoom) throws DrawingException {
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

    private void checkErrors() throws DrawingException {
        if (width < 0 || height < 0) {
            throw new DrawingException("The width and height of the view box must be positives.");
        }
    }
}
