package com.company;

import java.util.Deque;

public class SvgController {
    final int SHIFT=100;
    int scaleX=2;
    int scaleY=2;
    int scaleZ=5;
    String color;
    int length=100;
    int lenghtAux=0;
    int auxY;
    int auxX;
    int i;
    String svgString="<html>\n" +
            "\t"+"<head>\n" +
            "\t\t"+"<title>Page Title</title>\n" +
            "\t\t"+"<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n" +
            "\t"+"</head>\n" +
            "\t"+"<body>\n" +
            "\t\t"+"<svg width=\"10000\" height=\"10000\"> \n\n"+"\t\t<g fill=\"none\" opacity=\"0.75\" stroke=\"black\" stroke-width=\"1\">\n";

    public String draw(Deque<Cube> modelQueue) {
        for(Cube cube:modelQueue) {

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
                pathBase += "m " + length + " 200 v " + String.valueOf(-cube.getY() * scaleY) + " m 0 " + cube.getY() * scaleY + " h " + cube.getZ() * scaleZ + " m " + String.valueOf(-(cube.getZ() * scaleZ)) + " 0 l " + String.valueOf(-cube.getX() * scaleX) + " " + cube.getY() * scaleY;
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

        svgString+="\t\t </g>\n\t\t"+"</svg> \n" +"\t"+"</body>\n"+"</html>";
        return this.svgString;
    }
}
