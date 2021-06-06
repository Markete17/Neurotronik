package Drawer;

public class Font {
    private final int fontSize;
    private final String fontFamily;
    private String fontColor;

    public Font(int fontSize, String fontFamily, String fontColor) {
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

    public void checkErrors() {
        if (fontSize < 0) {
            throw new RuntimeException("Bad Settings. The size must be a positive number.");
        }
    }
}
