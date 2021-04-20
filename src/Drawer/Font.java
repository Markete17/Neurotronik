package Drawer;

public class Font {
    private final int font_size;
    private final String font_family;
    private String font_color;

    public Font(int font_size, String font_family, String font_color) {
        this.font_size = font_size;
        this.font_family = font_family;
        this.font_color = font_color;
        this.checkErrors();
    }

    public int getFont_size() {
        return font_size;
    }

    public String getFont_family() {
        return font_family;
    }

    public String getFont_color() {
        return font_color;
    }

    public void checkErrors() {
        if (font_size < 0) {
            throw new RuntimeException("Bad Settings. The size must be a positive number.");
        }
    }
}
