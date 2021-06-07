package drawing;

import exceptions.DrawingException;

public class Font {
    private final int fontSize;
    private final String fontFamily;
    private String fontColor;

    public Font(int fontSize, String fontFamily, String fontColor) throws DrawingException {
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

    private void checkErrors() throws DrawingException {
        if (fontSize < 0) {
            throw new DrawingException("Bad Settings. The size must be a positive number.");
        }
    }
}
