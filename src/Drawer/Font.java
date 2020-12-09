package Drawer;

public class Font{
    private int font_size;
    private String font_style;

    public Font(int font_size, String font_style) {
        this.font_size = font_size;
        this.font_style = font_style;
    }

    public int getFont_size() {
        return font_size;
    }

    public String getFont_style() {
        return font_style;
    }
}
