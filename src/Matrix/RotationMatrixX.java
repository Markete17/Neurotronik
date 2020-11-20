package Matrix;

public class RotationMatrixX implements Matrix {

    public RotationMatrixX(double alfa) {
        this.initializeMatrix(alfa);
    }

    public double[][] getMatrix() {
        return matrix;
    }

    /**
     * Initialize the rotation matrix in X
     * @param alfa
     *
     *  ( 1  0       0       )
     *  ( 0  cos(a)  -sin(a) )
     *  ( 0  sin(a)  cos(a)  )
     */
    @Override
    public void initializeMatrix(double alfa) {
        this.matrix[0][0] = 1;
        this.matrix[1][0] = 0;
        this.matrix[2][0] = 0;

        this.matrix[0][1] = 0;
        this.matrix[1][1] = Math.cos(Math.toRadians(alfa));
        this.matrix[2][1] = Math.sin(Math.toRadians(alfa));

        this.matrix[0][2] = 0;
        this.matrix[1][2] = -(Math.sin(Math.toRadians(alfa)));
        this.matrix[2][2] = Math.cos(Math.toRadians(alfa));
    }
}
