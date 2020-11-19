package Matrix;

import Data.Coordinate;

public class MatrixController {

    public MatrixController() {
    }

    public void rotate(String axis, Coordinate [] coordinates, double degrees){
        switch (axis){
            case "x":{
                Matrix rotationMatrixX=new RotationMatrixX(degrees);
                setNewCoordinates(rotationMatrixX,coordinates);
                break;

            }
            case "y":{
                Matrix rotationMatrixY=new RotationMatrixY(degrees);
                setNewCoordinates(rotationMatrixY,coordinates);
                break;

            }
            case "z":{
                Matrix rotationMatrixZ=new RotationMatrixZ(degrees);
                setNewCoordinates(rotationMatrixZ,coordinates);
                break;
            }
        }
    }

    public void move(String axis,Coordinate [] coordinates, double length){
        switch (axis){
            case "x":{
                coordinates[0].setX(coordinates[0].getX()+length);
                coordinates[1].setX(coordinates[1].getX()+length);
                coordinates[2].setX(coordinates[2].getX()+length);
                coordinates[3].setX(coordinates[3].getX()+length);
                coordinates[4].setX(coordinates[4].getX()+length);
                coordinates[5].setX(coordinates[5].getX()+length);
                coordinates[6].setX(coordinates[6].getX()+length);
                coordinates[7].setX(coordinates[7].getX()+length);
                break;
            }
            case "y":{
                coordinates[0].setY(coordinates[0].getY()+length);
                coordinates[1].setY(coordinates[1].getY()+length);
                coordinates[2].setY(coordinates[2].getY()+length);
                coordinates[3].setY(coordinates[3].getY()+length);
                coordinates[4].setY(coordinates[4].getY()+length);
                coordinates[5].setY(coordinates[5].getY()+length);
                coordinates[6].setY(coordinates[6].getY()+length);
                coordinates[7].setY(coordinates[7].getY()+length);
                break;
            }
            case "z":{
                coordinates[0].setZ(coordinates[0].getZ()+length);
                coordinates[1].setZ(coordinates[1].getZ()+length);
                coordinates[2].setZ(coordinates[2].getZ()+length);
                coordinates[3].setZ(coordinates[3].getZ()+length);
                coordinates[4].setZ(coordinates[4].getZ()+length);
                coordinates[5].setZ(coordinates[5].getZ()+length);
                coordinates[6].setZ(coordinates[6].getZ()+length);
                coordinates[7].setZ(coordinates[7].getZ()+length);
                break;
            }
        }

    }

    private void setNewCoordinates(Matrix rotateMatrix, Coordinate[] coordinates) {
        coordinates[0]=multiply(rotateMatrix.getMatrix(),coordinates[0].getCoordinateMatrix());
        coordinates[1]=multiply(rotateMatrix.getMatrix(),coordinates[1].getCoordinateMatrix());
        coordinates[2]=multiply(rotateMatrix.getMatrix(),coordinates[2].getCoordinateMatrix());
        coordinates[3]=multiply(rotateMatrix.getMatrix(),coordinates[3].getCoordinateMatrix());
        coordinates[4]=multiply(rotateMatrix.getMatrix(),coordinates[4].getCoordinateMatrix());
        coordinates[5]=multiply(rotateMatrix.getMatrix(),coordinates[5].getCoordinateMatrix());
        coordinates[6]=multiply(rotateMatrix.getMatrix(),coordinates[6].getCoordinateMatrix());
        coordinates[7]=multiply(rotateMatrix.getMatrix(),coordinates[7].getCoordinateMatrix());
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
