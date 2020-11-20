package Matrix;

public class RotationMatrixZ implements Matrix {

    public RotationMatrixZ(double alfa) {
        this.initializeMatrix(alfa);
    }

    public double[][] getMatrix() {
        return matrix;
    }

    /**
     * Initialize the rotation matrix in Z
     * @param alfa
     *
     *  ( cos(a)    0   sin(a) )
     *  ( 0         1   0      )
     *  ( -sin(a)   0   cos(a) )
     */

    @Override
    public void initializeMatrix(double alfa) {
        this.matrix[0][0] = Math.cos(Math.toRadians(alfa));
        this.matrix[1][0] = Math.sin(Math.toRadians(alfa));
        this.matrix[2][0] = 0;

        this.matrix[0][1] = -(Math.sin(Math.toRadians(alfa)));
        this.matrix[1][1] = Math.cos(Math.toRadians(alfa));
        this.matrix[2][1] = 0;

        this.matrix[0][2] = 0;
        this.matrix[1][2] = 0;
        this.matrix[2][2] = 1;

    }
}
