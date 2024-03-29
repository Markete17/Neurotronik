package matrices;

import data.Coordinate;
import exceptions.MatrixException;

public class MatrixController {
    private double[][] matrix;

    public MatrixController(double alfaX, double alfaY, double alfaZ) throws MatrixException {
        RotationMatrixX rotationMatrixX = new RotationMatrixX(alfaX);
        RotationMatrixY rotationMatrixY = new RotationMatrixY(alfaY);
        RotationMatrixZ rotationMatrixZ = new RotationMatrixZ(alfaZ);
        this.matrix = multiply(rotationMatrixZ.getMatrix(), rotationMatrixX.getMatrix());
        this.matrix = multiply(this.matrix, rotationMatrixY.getMatrix());
    }

    public void rotate(Coordinate[] coordinates) throws MatrixException {
        setNewCoordinates(coordinates);
    }

    /**
     * Move the coordinates given an axis and length
     *
     * @param axis        the axix to move
     * @param coordinates the coordinates to move
     * @param length      the length to move
     */
    public void move(String axis, Coordinate[] coordinates, double length) throws MatrixException {
        switch (axis) {
            case "x": {
                for (int i = 0; i < 11; i++) {
                    coordinates[i].setX(coordinates[i].getX() + length);
                }
                break;
            }
            case "y": {
                for (int i = 0; i < 11; i++) {
                    coordinates[i].setY(coordinates[i].getY() + length);
                }
                break;
            }
            case "z": {
                for (int i = 0; i < 11; i++) {
                    coordinates[i].setZ(coordinates[i].getZ() + length);
                }
                break;
            }
            default:
                throw new MatrixException("'"+axis+"' is an invalid coordinate axis.");
        }

    }

    /**
     * Multiply matrices A and B to generate a new point c
     *
     * @param a first matrix
     * @param b second matrix
     * @return c matrix result of multiplying a x b
     */

    public double[][] multiply(double[][] a, double[][] b) throws MatrixException {
        double[][] c = new double[a.length][b[0].length];
        if (a[0].length == b.length) {
            for (int i = 0; i < c.length; i++)
                for (int j = 0; j < c[0].length; j++)
                    for (int k = 0; k < b.length; k++)
                        c[i][j] += a[i][k] * b[k][j];
            return c;
        }
        throw new MatrixException("Matrices cannot be multiplied (invalid dimensions).");
    }

    private void setNewCoordinates(Coordinate[] coordinates) throws MatrixException {
        double[][] c0 = multiply(matrix, coordinates[0].getCoordinateMatrix());
        double[][] c1 = multiply(matrix, coordinates[1].getCoordinateMatrix());
        double[][] c2 = multiply(matrix, coordinates[2].getCoordinateMatrix());
        double[][] c3 = multiply(matrix, coordinates[3].getCoordinateMatrix());
        double[][] c4 = multiply(matrix, coordinates[4].getCoordinateMatrix());
        double[][] c5 = multiply(matrix, coordinates[5].getCoordinateMatrix());
        double[][] c6 = multiply(matrix, coordinates[6].getCoordinateMatrix());
        double[][] c7 = multiply(matrix, coordinates[7].getCoordinateMatrix());
        double[][] c8 = multiply(matrix, coordinates[8].getCoordinateMatrix());
        double[][] c9 = multiply(matrix, coordinates[9].getCoordinateMatrix());
        double[][] c10 = multiply(matrix, coordinates[10].getCoordinateMatrix());

        coordinates[0] = new Coordinate(c0[0][0], c0[1][0], c0[2][0]);
        coordinates[1] = new Coordinate(c1[0][0], c1[1][0], c1[2][0]);
        coordinates[2] = new Coordinate(c2[0][0], c2[1][0], c2[2][0]);
        coordinates[3] = new Coordinate(c3[0][0], c3[1][0], c3[2][0]);
        coordinates[4] = new Coordinate(c4[0][0], c4[1][0], c4[2][0]);
        coordinates[5] = new Coordinate(c5[0][0], c5[1][0], c5[2][0]);
        coordinates[6] = new Coordinate(c6[0][0], c6[1][0], c6[2][0]);
        coordinates[7] = new Coordinate(c7[0][0], c7[1][0], c7[2][0]);
        coordinates[8] = new Coordinate(c8[0][0], c8[1][0], c8[2][0]);
        coordinates[9] = new Coordinate(c9[0][0], c9[1][0], c9[2][0]);
        coordinates[10] = new Coordinate(c10[0][0], c10[1][0], c10[2][0]);
    }

}
