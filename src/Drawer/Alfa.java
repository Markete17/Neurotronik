package Drawer;

public class Alfa {
        private double alfaX;
        private double alfaY;
        private double alfaZ;

        public Alfa(double alfaX, double alfaY, double alfaZ) {
            this.alfaX = alfaX;
            this.alfaY = alfaY;
            this.alfaZ = alfaZ;
        }

    public double getAlfaX() {
        return alfaX;
    }

    public double getAlfaY() {
        return alfaY;
    }

    public double getAlfaZ() {
        return alfaZ;
    }

    public Alfa checkAlfa(){
            if(this.alfaX<-90){
                this.alfaX=-90;
            }
            if(this.alfaX>90){
                this.alfaX=90;
            }
            if(this.alfaY>360){
                this.alfaY=360;
            }
            if(this.alfaY<-360){
                this.alfaY=-360;
            }
            return this;
    }
}
