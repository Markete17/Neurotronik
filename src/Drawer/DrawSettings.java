package Drawer;

public class DrawSettings {

    private Color color;
    private Alfa alfa;
    private Shift shift;
    private Font font;
    private Stroke stroke;
    private ViewBox viewBox;


    //Default Settings
    public DrawSettings(){
        this.color=new Color("beige","red","darkturquoise","pink","black");
        this.alfa=new Alfa(30,60,0);
        this.shift=new Shift(30,50,50);
        this.font=new Font(5,"italic");
        this.stroke=new Stroke("black",0.5);
        this.viewBox=new ViewBox(1000,1000,-100,-400,1000,1000);
    }

    public DrawSettings(Color color, Alfa alfa, Shift shift, Font font, Stroke stroke, ViewBox viewBox) {
        this.color = color;
        this.alfa = alfa;
        this.shift = shift;
        this.font = font;
        this.stroke=stroke;
        this.viewBox=viewBox;
    }

    public Color getColor() {
        return color;
    }

    public Alfa getAlfa() {
        return alfa;
    }

    public Shift getShift() {
        return shift;
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
}
