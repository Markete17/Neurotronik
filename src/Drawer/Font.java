package Drawer;

public class Font{
    private int font_size;
    private String font_family;
    private String font_color;

    public Font(int font_size, String font_family) {
        this.font_size = font_size;
        this.font_family = font_family;
    }

    public int getFont_size() {
        return font_size;
    }

    public String getFont_family() {
        return font_family;
    }
}
