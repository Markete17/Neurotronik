package matrices;

public class RotationMatrixZ implements Matrix {

    private final double[][] matrix = new double[3][3];

    public RotationMatrixZ(double alfa) {
        this.initializeMatrix(alfa);
    }

    /**
     * Initialize the rotation matrix in Z
     *
     * @param alfa ( cos(a) -sin(a)  0 )
     *             ( sin(a) cos(a)   0 )
     *             (   0      0      1 )
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

    @Override
    public double[][] getMatrix() {
        return this.matrix;
    }
}
