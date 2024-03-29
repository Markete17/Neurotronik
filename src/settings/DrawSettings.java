package settings;

import exceptions.SettingsException;

public class DrawSettings {
    private static final String BLACK = "black";
    private final Color color;
    private final Alfa alfa;
    private final Displacement displacement;
    private final Font font;
    private final Stroke stroke;
    private final ViewBox viewBox;
    private final boolean activateWidhtLogs;
    private final boolean activateDepthLogs;
    private final boolean activateLayerDimensions;
    private final boolean activateKernelDimensions;

    //Default Settings
    public DrawSettings() throws SettingsException {
        this.color = new Color("lavender", "orange", "darkturquoise", "darkturquoise", "pink", BLACK, 0.5, 0.5, 0.75, 0.75, 0.5, 0.75);
        this.alfa = new Alfa(30, 60, 0);
        this.displacement = new Displacement(100, 50, 50);
        this.font = new Font(6, "calibri", BLACK);
        this.stroke = new Stroke(BLACK, 0.3);
        this.viewBox = new ViewBox(3000, 2000, 0);
        this.activateWidhtLogs = false;
        this.activateDepthLogs = false;
        this.activateLayerDimensions = true;
        this.activateKernelDimensions = true;
    }

    //Developer custom settings
    public DrawSettings(Color color, Alfa alfa, Displacement displacement, Font font, Stroke stroke, ViewBox viewBox, boolean activateDepthLogs, boolean activateWidhtLogs, boolean activateLayerDimensions, boolean activateKernelDimensions) {
        this.color = color;
        this.alfa = alfa;
        this.displacement = displacement;
        this.font = font;
        this.stroke = stroke;
        this.viewBox = viewBox;
        this.activateDepthLogs = activateDepthLogs;
        this.activateWidhtLogs = activateWidhtLogs;
        this.activateLayerDimensions = activateLayerDimensions;
        this.activateKernelDimensions = activateKernelDimensions;
    }

    public Color getColor() {
        return color;
    }

    public Alfa getAlfa() {
        return alfa;
    }

    public Displacement getDisplacement() {
        return displacement;
    }

    public Font getFont() {
        return font;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public ViewBox getViewBox() {
        return viewBox;
    }

    public boolean isActivateLayerDimensions() {
        return activateLayerDimensions;
    }

    public boolean isActivateKernelDimensions() {
        return activateKernelDimensions;
    }

    public double logWidth(double num) {
        if (activateWidhtLogs) {
            return Math.log(num) / Math.log(2);
        }
        return num;
    }

    public double logDepth(double num) {
        if (activateDepthLogs) {
            return Math.log(num) / Math.log(2);
        }
        return num;
    }
}
