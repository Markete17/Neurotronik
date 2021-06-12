package settings;

import exceptions.SettingsException;

public class Color {
    //COLOR
    private final String inputColor;
    private final String cubeColor;
    private final String kernelColor;
    private final String denseColor;
    private final String pyramidColor;
    private final String arrowColor;
    //OPACITY
    private final double inputOpacity;
    private final double layerOpacity;
    private final double kernelOpacity;
    private final double convOpacity; //pyramid
    private final double arrowOpacity;
    private final double denseOpacity;

    public Color(String input, String cube, String kernel, String dense, String pyramid, String arrow, double inputOpacity, double layerOpacity, double kernelOpacity, double convOpacity, double arrowOpacity, double denseOpacity) throws SettingsException {
        this.inputColor = input;
        this.cubeColor = cube;
        this.kernelColor = kernel;
        this.denseColor = dense;
        this.pyramidColor = pyramid;
        this.arrowColor = arrow;
        this.inputOpacity = inputOpacity;
        this.layerOpacity = layerOpacity;
        this.kernelOpacity = kernelOpacity;
        this.convOpacity = convOpacity;
        this.arrowOpacity = arrowOpacity;
        this.denseOpacity = denseOpacity;
        this.checkErrors();
    }

    public String getInputColor() {
        return inputColor;
    }

    public String getCubeColor() {
        return cubeColor;
    }

    public String getKernelColor() {
        return kernelColor;
    }

    public String getDenseColor() {
        return denseColor;
    }

    public String getPyramidColor() {
        return pyramidColor;
    }

    public String getArrowColor() {
        return arrowColor;
    }

    public double getInputOpacity() {
        return inputOpacity;
    }

    public double getLayerOpacity() {
        return layerOpacity;
    }

    public double getKernelOpacity() {
        return kernelOpacity;
    }

    public double getConvOpacity() {
        return convOpacity;
    }

    public double getArrowOpacity() {
        return arrowOpacity;
    }

    public double getDenseOpacity() {
        return denseOpacity;
    }

    private void checkErrors() throws SettingsException {
        if(this.arrowOpacity<0){
            throw new SettingsException("The connector opacity must be a positive number");
        }
        if(this.inputOpacity<0){
            throw new SettingsException("The input opacity must be a positive number");
        }
        if(this.layerOpacity<0){
            throw new SettingsException("The convolutinal layer opacity must be a positive number");
        }
        if(this.kernelOpacity<0){
            throw new SettingsException("The arrow opacity must be a positive number");
        }
        if(this.convOpacity<0){
            throw new SettingsException("The convolution connector opacity must be a positive number");
        }
        if(this.denseOpacity<0){
            throw new SettingsException("The dense opacity must be a positive number");
        }
    }
}
