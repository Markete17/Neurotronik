package Drawer;

public class DrawSettings {

    private Color color;
    private Alfa alfa;
    private Shift shift;
    private Font font;
    private Stroke stroke;
    private ViewBox viewBox;
    private boolean activateWidhtLogs;
    private boolean activateDepthLogs;


    //Default Settings
    public DrawSettings(){
        this.color=new Color("beige","red","darkturquoise","pink","black",0.5,0.5,0.75,1,0.75);
        this.alfa=new Alfa(30,60,0);
        this.shift=new Shift(30,50,50);
        this.font=new Font(4,"calibri");
        this.stroke=new Stroke("black",0.3);
        this.viewBox=new ViewBox(1000,1000,-100,-400,1000,1000);
        this.activateWidhtLogs=false;
        this.activateDepthLogs=false;
    }

    public DrawSettings(Color color, Alfa alfa, Shift shift, Font font, Stroke stroke, ViewBox viewBox, boolean activateDepthLogs, boolean activateWidhtLogs) {
        this.color = color;
        this.alfa = alfa;
        this.shift = shift;
        this.font = font;
        this.stroke=stroke;
        this.viewBox=viewBox;
        this.activateDepthLogs=activateDepthLogs;
        this.activateWidhtLogs=activateWidhtLogs;
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

    public String getPadding() {
        return padding;
    }

    public double logWidth(double num){
        if(activateWidhtLogs) {
            return Math.log(num) / Math.log(2);
        }
        return num;
    }

    public double logDepth(double num){
        if(activateDepthLogs){
            return Math.log(num) / Math.log(2);
        }
        return num;
    }


}
