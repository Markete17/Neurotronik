package Matrix;

public interface Matrix {

    double [][] matrix=new double[3][3];

    void initializeMatrix(double alfa);

    default double[][] getMatrix() {
        return matrix;
    }


}
