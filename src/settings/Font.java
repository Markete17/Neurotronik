package settings;

import exceptions.SettingsException;

public class Font {
    private final int fontSize;
    private final String fontFamily;
    private String fontColor;

    public Font(int fontSize, String fontFamily, String fontColor) throws SettingsException {
        this.fontSize = fontSize;
        this.fontFamily = fontFamily;
        this.fontColor = fontColor;
        this.checkErrors();
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public String getFontColor() {
        return fontColor;
    }

    private void checkErrors() throws SettingsException {
        if (fontSize < 0) {
            throw new SettingsException("The font size must be a positive number.");
        }
    }
}
