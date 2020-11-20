package Matrix;

public interface Matrix {

    final double [][] matrix=new double[3][3];

    public void initializeMatrix(double alfa);

    public default double[][] getMatrix() {
        return matrix;
    }


}
