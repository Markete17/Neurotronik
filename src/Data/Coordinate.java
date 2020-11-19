package Data;

public class Coordinate {
    private double [][] coordinateMatrix=new double[3][1];

    public Coordinate(double x, double y, double z) {

        this.coordinateMatrix[0][0]=x;
        this.coordinateMatrix[1][0]=y;
        this.coordinateMatrix[2][0]=z;
    }

    public Coordinate() {
    }

    public double getX() {
        return this.coordinateMatrix[0][0];
    }

    public double getY() {
        return this.coordinateMatrix[1][0];
    }

    public double getZ() {
        return this.coordinateMatrix[2][0];
    }

    public void setX(double x) {
        this.coordinateMatrix[0][0]=x;
    }

    public void setY(double y) {
        this.coordinateMatrix[1][0]=y;
    }

    public void setZ(double z) {
        this.coordinateMatrix[2][0]=z;
    }

    public double[][] getCoordinateMatrix() {
        return coordinateMatrix;
    }
}
