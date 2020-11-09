package com.company;

public class Matrix {


    protected final double [][] rotationX=new double[3][3];
    protected final double [][] rotationY=new double[3][3];
    protected final double [][] rotationZ=new double[3][3];

    public Matrix(double alfa) {
        this.initializeRotationMatrixX(alfa);
        this.initializeRotationMatrixY(alfa);
        this.initializeRotationMatrixZ(alfa);
    }

    /**
     * Initialize the rotation matrix in X
     * @param alfa
     *
     *  ( 1  0       0       )
     *  ( 0  cos(a)  -sin(a) )
     *  ( 0  sin(a)  cos(a)  )
     */

    private void initializeRotationMatrixX(double alfa) {
        this.rotationX[0][0] = 1;
        this.rotationX[1][0] = 0;
        this.rotationX[2][0] = 0;

        this.rotationX[0][1] = 0;
        this.rotationX[1][1] = Math.cos(alfa);
        this.rotationX[2][1] = Math.sin(alfa);

        this.rotationX[0][3] = 0;
        this.rotationX[1][3] = -Math.sin(alfa);
        this.rotationX[2][3] = Math.cos(alfa);
    }

    /**
     * Initialize the rotation matrix in Y
     * @param alfa
     *
     *  ( cos(a)    0   sin(a) )
     *  ( 0         1   0      )
     *  ( -sin(a)   0   cos(a) )
     */

    private void initializeRotationMatrixY(double alfa) {
        this.rotationY[0][0] = Math.cos(alfa);
        this.rotationY[1][0] = 0;
        this.rotationY[2][0] = -Math.sin(alfa);

        this.rotationY[0][1] = 0;
        this.rotationY[1][1] = 1;
        this.rotationY[2][1] = 0;

        this.rotationY[0][3] = Math.sin(alfa);
        this.rotationY[1][3] = 0;
        this.rotationY[2][3] = Math.cos(alfa);
    }

    /**
     * Initialize the rotation matrix in Z
     * @param alfa
     *
     *  ( cos(a)    0   sin(a) )
     *  ( 0         1   0      )
     *  ( -sin(a)   0   cos(a) )
     */

    private void initializeRotationMatrixZ(double alfa) {
        this.rotationZ[0][0] = Math.cos(alfa);
        this.rotationZ[1][0] = Math.sin(alfa);
        this.rotationZ[2][0] = 0;

        this.rotationZ[0][1] = -Math.sin(alfa);
        this.rotationZ[1][1] = Math.cos(alfa);
        this.rotationZ[2][1] = 0;

        this.rotationZ[0][3] = 0;
        this.rotationZ[1][3] = 0;
        this.rotationZ[2][3] = 1;
    }

    /**
     * Multiply matrices A and B to generate a new point c
     * @param a
     * @param b
     * @return
     */

    public static Coordinate multiply(double[][] a, double[][] b) {
        double[][] c = new double[3][1];
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b[0].length; j++) {
                    for (int k = 0; k < a[0].length; k++) {
                        // aquí se multiplica la matriz
                        c[i][j] += a[i][k] * b[k][j];
                    }
                }
            }

            Coordinate output=new Coordinate(c[0][0],c[1][0],c[2][0]);
        return output;
    }
}
