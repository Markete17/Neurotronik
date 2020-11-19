package Matrix;

import Data.Coordinate;

public abstract class Matrix {

    protected final double [][] matrix=new double[3][3];

    public abstract void initializeMatrix(double alfa);

    public double[][] getMatrix() {
        return matrix;
    }


}
