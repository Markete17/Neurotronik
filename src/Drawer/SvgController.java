package Drawer;

import Data.Coordinate;
import Matrix.*;
import Shapes.Cube;

import java.util.Deque;

public class SvgController {
    final double ALFA=30;
    final int SHIFT=100;
    int scaleX=2;
    int scaleY=2;
    int scaleZ=5;
    String color;
    double length=100;
    double lenghtAux=0;
    double auxY;
    double auxX;
    int i;
    String svgString="";

    public String draw(Deque<Cube> modelQueue) {
        this.addHeader();
        Matrix matrixRotX=new RotationMatrixX(ALFA);
        Matrix matrixRotY=new RotationMatrixY(ALFA);
        for(Cube cube:modelQueue) {
            Coordinate a0=matrixRotY.multiply(matrixRotY.getMatrix(),cube.getCoordinateList().get(0).getCoordinateMatrix());
            Coordinate a1=matrixRotY.multiply(matrixRotY.getMatrix(),cube.getCoordinateList().get(1).getCoordinateMatrix());
            Coordinate a2=matrixRotY.multiply(matrixRotY.getMatrix(),cube.getCoordinateList().get(2).getCoordinateMatrix());
            Coordinate a3=matrixRotY.multiply(matrixRotY.getMatrix(),cube.getCoordinateList().get(3).getCoordinateMatrix());
            Coordinate a4=matrixRotY.multiply(matrixRotY.getMatrix(),cube.getCoordinateList().get(4).getCoordinateMatrix());
            Coordinate a5=matrixRotY.multiply(matrixRotY.getMatrix(),cube.getCoordinateList().get(5).getCoordinateMatrix());
            Coordinate a6=matrixRotY.multiply(matrixRotY.getMatrix(),cube.getCoordinateList().get(6).getCoordinateMatrix());
            Coordinate a7=matrixRotY.multiply(matrixRotY.getMatrix(),cube.getCoordinateList().get(7).getCoordinateMatrix());

            Coordinate x0=matrixRotX.multiply(matrixRotX.getMatrix(),a0.getCoordinateMatrix());
            Coordinate x1=matrixRotX.multiply(matrixRotX.getMatrix(),a1.getCoordinateMatrix());
            Coordinate x2=matrixRotX.multiply(matrixRotX.getMatrix(),a2.getCoordinateMatrix());
            Coordinate x3=matrixRotX.multiply(matrixRotX.getMatrix(),a3.getCoordinateMatrix());
            Coordinate x4=matrixRotX.multiply(matrixRotX.getMatrix(),a4.getCoordinateMatrix());
            Coordinate x5=matrixRotX.multiply(matrixRotX.getMatrix(),a5.getCoordinateMatrix());
            Coordinate x6=matrixRotX.multiply(matrixRotX.getMatrix(),a6.getCoordinateMatrix());
            Coordinate x7=matrixRotX.multiply(matrixRotX.getMatrix(),a7.getCoordinateMatrix());

            if(!cube.isKernel()){
                this.color="orange";
                this.i=1;
            }
            else{
                this.color="pink";
                this.i=0;
            }

                String pathBase = "";
                String pathCube = "";
                pathBase += "m " + length + " 200 v " + String.valueOf(-x2.getY() * scaleY) + " m 0 " + x2.getY() * scaleY + " h " + x4.getX() * scaleZ + " m " + String.valueOf(-(x4.getX() * scaleZ)) + " 0 l " + String.valueOf(-cube.getX() * scaleX) + " " + cube.getY() * scaleY;
                pathCube += "m " + length + " 200"+ "m "+String.valueOf(-cube.getX()*scaleX)+" "+cube.getY()*scaleY+" v " + String.valueOf(-cube.getY() * scaleY) + " l " + cube.getX() * scaleX + " " + String.valueOf(-cube.getY() * scaleY) + " h " + String.valueOf(cube.getZ() * scaleZ) + " v " + cube.getY() * scaleY + " l " + (String.valueOf(-cube.getX() * scaleX) + " " + cube.getY() * scaleY + " h " + String.valueOf(-(cube.getZ() * scaleZ)) + " m 0 " + String.valueOf(-cube.getY() * scaleY) + " h " + String.valueOf(cube.getZ() * scaleZ) + " v " + cube.getY() * scaleY + " m 0 " + String.valueOf(-cube.getY() * scaleY) + " l " + cube.getX() * scaleX + " " + String.valueOf(-cube.getY() * scaleY));
                svgString += "\t\t\t<path fill=\"" + this.color + "\" d=\" "+pathCube + "\"/>" + "\n";
                svgString += "\t\t\t<path stroke-dasharray=\"5,5\" d=\" "+pathBase + "\"/>" + "\n";

                if(!cube.isKernel()) {
                    auxY=cube.getY();
                    auxX=length-cube.getX();
                   lenghtAux=length+SHIFT+(cube.getZ()*scaleZ);
                   svgString+="\t\t\t<text stroke=\""+this.color+"\""+" x=\""+String.valueOf(this.length-cube.getX())+"\" y=\""+String.valueOf(200-(cube.getY()*scaleY +50))+"\" style=\"font-family: Consolas\">"+cube.getX()+"x"+cube.getY()+"x"+cube.getZ()+"</text>\n\n";
                }
                else{
                    length=lenghtAux;
                    svgString+="\t\t\t<text stroke=\""+this.color+"\""+" x=\""+String.valueOf(auxX)+"\" y=\""+String.valueOf(200-(auxY*scaleY+25))+"\" style=\"font-family: Consolas\">"+cube.getX()+"x"+cube.getY()+"x"+cube.getZ()+"</text>\n\n";
                }
            }

        this.addFooter();
        return this.svgString;
    }

    private void addHeader(){
        this.svgString="<html>\n" +
                "\t"+"<head>\n" +
                "\t\t"+"<title>Page Title</title>\n" +
                "\t\t"+"<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n" +
                "\t"+"</head>\n" +
                "\t"+"<body>\n" +
                "\t\t"+"<svg width=\"10000\" height=\"10000\"> \n\n"+"\t\t<g fill=\"none\" opacity=\"0.75\" stroke=\"black\" stroke-width=\"1\">\n";
    }

    private void addFooter(){
        this.svgString+="\t\t </g>\n\t\t"+"</svg> \n" +"\t"+"</body>\n"+"</html>";
    }
}
