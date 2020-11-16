package Matrix;

import Data.Coordinate;

public abstract class Matrix {

    protected final double [][] matrix=new double[3][3];

    public abstract void initializeMatrix(double alfa);

    public double[][] getMatrix() {
        return matrix;
    }

    /**
     * Multiply matrices A and B to generate a new point c
     * @param a
     * @param b
     * @return
     */

    public Coordinate multiply(double[][] a, double[][] b) {
        double[][] c = new double[3][1];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        Coordinate output=new Coordinate(c[0][0],c[1][0],c[2][0]);
        return output;
    }


}
