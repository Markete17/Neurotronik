package Matrix;

import Data.Coordinate;

public class MatrixController {
    private double[][] matrix;

    public MatrixController(double alfaX, double alfaY, double alfaZ) {
        RotationMatrixX rotationMatrixX = new RotationMatrixX(alfaX);
        RotationMatrixY rotationMatrixY = new RotationMatrixY(alfaY);
        RotationMatrixZ rotationMatrixZ = new RotationMatrixZ(alfaZ);
        this.matrix = multiply(rotationMatrixZ.getMatrix(), rotationMatrixX.getMatrix());
        this.matrix = multiply(this.matrix, rotationMatrixY.getMatrix());
    }

    public void rotate(Coordinate[] coordinates) {
        setNewCoordinates(coordinates);
    }

    /**
     * Move the coordinates given an axis and length
     *
     * @param axis        the axix to move
     * @param coordinates the coordinates to move
     * @param length      the length to move
     */
    public void move(String axis, Coordinate[] coordinates, double length) {
        switch (axis) {
            case "x": {
                coordinates[0].setX(coordinates[0].getX() + length);
                coordinates[1].setX(coordinates[1].getX() + length);
                coordinates[2].setX(coordinates[2].getX() + length);
                coordinates[3].setX(coordinates[3].getX() + length);
                coordinates[4].setX(coordinates[4].getX() + length);
                coordinates[5].setX(coordinates[5].getX() + length);
                coordinates[6].setX(coordinates[6].getX() + length);
                coordinates[7].setX(coordinates[7].getX() + length);
                coordinates[8].setX(coordinates[8].getX() + length);
                break;
            }
            case "y": {
                coordinates[0].setY(coordinates[0].getY() + length);
                coordinates[1].setY(coordinates[1].getY() + length);
                coordinates[2].setY(coordinates[2].getY() + length);
                coordinates[3].setY(coordinates[3].getY() + length);
                coordinates[4].setY(coordinates[4].getY() + length);
                coordinates[5].setY(coordinates[5].getY() + length);
                coordinates[6].setY(coordinates[6].getY() + length);
                coordinates[7].setY(coordinates[7].getY() + length);
                coordinates[8].setY(coordinates[8].getY() + length);
                break;
            }
            case "z": {
                coordinates[0].setZ(coordinates[0].getZ() + length);
                coordinates[1].setZ(coordinates[1].getZ() + length);
                coordinates[2].setZ(coordinates[2].getZ() + length);
                coordinates[3].setZ(coordinates[3].getZ() + length);
                coordinates[4].setZ(coordinates[4].getZ() + length);
                coordinates[5].setZ(coordinates[5].getZ() + length);
                coordinates[6].setZ(coordinates[6].getZ() + length);
                coordinates[7].setZ(coordinates[7].getZ() + length);
                coordinates[8].setZ(coordinates[8].getZ() + length);
                break;
            }
        }

    }

    /**
     * Multiply matrices A and B to generate a new point c
     *
     * @param a first matrix
     * @param b second matrix
     * @return c matrix result of multiplying a x b
     */

    public double[][] multiply(double[][] a, double[][] b) {
        double[][] c = new double[a.length][b[0].length];
        for (int i = 0; i < c.length; i++)
            for (int j = 0; j < c[0].length; j++)
                for (int k = 0; k < b.length; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    private void setNewCoordinates(Coordinate[] coordinates) {
        double[][] c0 = multiply(matrix, coordinates[0].getCoordinateMatrix());
        double[][] c1 = multiply(matrix, coordinates[1].getCoordinateMatrix());
        double[][] c2 = multiply(matrix, coordinates[2].getCoordinateMatrix());
        double[][] c3 = multiply(matrix, coordinates[3].getCoordinateMatrix());
        double[][] c4 = multiply(matrix, coordinates[4].getCoordinateMatrix());
        double[][] c5 = multiply(matrix, coordinates[5].getCoordinateMatrix());
        double[][] c6 = multiply(matrix, coordinates[6].getCoordinateMatrix());
        double[][] c7 = multiply(matrix, coordinates[7].getCoordinateMatrix());
        double[][] c8 = multiply(matrix, coordinates[8].getCoordinateMatrix());

        coordinates[0] = new Coordinate(c0[0][0], c0[1][0], c0[2][0]);
        coordinates[1] = new Coordinate(c1[0][0], c1[1][0], c1[2][0]);
        coordinates[2] = new Coordinate(c2[0][0], c2[1][0], c2[2][0]);
        coordinates[3] = new Coordinate(c3[0][0], c3[1][0], c3[2][0]);
        coordinates[4] = new Coordinate(c4[0][0], c4[1][0], c4[2][0]);
        coordinates[5] = new Coordinate(c5[0][0], c5[1][0], c5[2][0]);
        coordinates[6] = new Coordinate(c6[0][0], c6[1][0], c6[2][0]);
        coordinates[7] = new Coordinate(c7[0][0], c7[1][0], c7[2][0]);
        coordinates[8] = new Coordinate(c8[0][0], c8[1][0], c8[2][0]);
    }

}
