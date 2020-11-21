package Drawer;

import Data.Coordinate;
import Matrix.*;
import Shapes.Arrow;
import Shapes.Cube;
import Shapes.Pyramid;

import java.util.Arrays;
import java.util.List;

public class SvgController {
    //CONSTANTS
    final double ALFA=40;
    final int SHIFT=75;
    double length=0;
    double lengthAux=0;
    boolean activate=false;
    String color;

    //final svg file
    String svgString="";


    public String draw(List<Cube> modelQueue) {
        this.addHeader();
        this.shift(modelQueue);
            for(int i=0;i<modelQueue.size();i++) {
                Cube cube=modelQueue.get(i);
                color=selectColor(cube);
                doTransformations(cube.getCoordinates());
                drawCube(cube);
                if(activate){

                    Cube kernelCube=modelQueue.get(i-1);
                    Coordinate vertex=calculateRandomPoint(cube.getCoordinates());

                    Pyramid pyramid=new Pyramid(Arrays.copyOf(kernelCube.getCoordinates(),4),new Coordinate(vertex.getX(),vertex.getY(),vertex.getZ()));
                    color="silver";
                    drawPyramid(pyramid);
                    activate=false;
                }

                if(cube.isDenseLayer()){
                    Cube lastCube=modelQueue.get(i-1);

                    Coordinate vertex1=calculateCenter(Arrays.copyOfRange(lastCube.getCoordinates(),0,4));
                    Coordinate vertex2=calculateCenter(Arrays.copyOfRange(cube.getCoordinates(),4,8));

                    Arrow arrow=new Arrow(vertex1,vertex2);
                    drawArrow(arrow);
                }

                if(cube.isKernel()){
                    activate=true;
                }
            }

        this.addFooter();
        return this.svgString;
    }

    private void drawCube(Cube cube) {
        //Cube Path
        svgString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+ cube.getCoordinates()[0].getX() +" "+ cube.getCoordinates()[0].getY() +" L"+ cube.getCoordinates()[1].getX() +" "+ cube.getCoordinates()[1].getY() +" L"+ cube.getCoordinates()[3].getX() +" "+ cube.getCoordinates()[3].getY() +" L"+ cube.getCoordinates()[2].getX() +" "+cube.getCoordinates()[2].getY()+" L"+ cube.getCoordinates()[0].getX() +" "+cube.getCoordinates()[0].getY()+"\"/>" + "\n";
        svgString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+ cube.getCoordinates()[4].getX() +" "+cube.getCoordinates()[4].getY()+" L"+ cube.getCoordinates()[5].getX() +" "+cube.getCoordinates()[5].getY()+" L"+ cube.getCoordinates()[7].getX() +" "+cube.getCoordinates()[7].getY()+" L"+cube.getCoordinates()[6].getX()+" "+cube.getCoordinates()[6].getY()+" L"+cube.getCoordinates()[4].getX()+" "+cube.getCoordinates()[4].getY()+"\"/>" + "\n";
        svgString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+ cube.getCoordinates()[0].getX()+" "+cube.getCoordinates()[0].getY()+" L"+cube.getCoordinates()[4].getX()+" "+cube.getCoordinates()[4].getY()+" L"+cube.getCoordinates()[6].getX()+" "+cube.getCoordinates()[6].getY()+" L"+cube.getCoordinates()[2].getX()+" "+cube.getCoordinates()[2].getY()+" L"+cube.getCoordinates()[0].getX()+" "+cube.getCoordinates()[0].getY()+"\"/>" + "\n";
        svgString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+ cube.getCoordinates()[1].getX()+" "+cube.getCoordinates()[1].getY()+" L"+cube.getCoordinates()[5].getX()+" "+cube.getCoordinates()[5].getY()+" L"+cube.getCoordinates()[7].getX()+" "+cube.getCoordinates()[7].getY()+" L"+cube.getCoordinates()[3].getX()+" "+cube.getCoordinates()[3].getY()+" L"+cube.getCoordinates()[1].getX()+" "+cube.getCoordinates()[1].getY()+"\"/>" + "\n";
        svgString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+cube.getCoordinates()[0].getX()+" "+cube.getCoordinates()[0].getY()+" L"+cube.getCoordinates()[1].getX()+" "+cube.getCoordinates()[1].getY()+" L"+cube.getCoordinates()[5].getX()+" "+cube.getCoordinates()[5].getY()+" L"+cube.getCoordinates()[4].getX()+" "+cube.getCoordinates()[4].getY()+" L"+cube.getCoordinates()[0].getX()+" "+cube.getCoordinates()[0].getY()+"\"/>" + "\n";
        svgString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+cube.getCoordinates()[2].getX()+" "+cube.getCoordinates()[2].getY()+" L"+cube.getCoordinates()[3].getX()+" "+cube.getCoordinates()[3].getY()+" L"+cube.getCoordinates()[7].getX()+" "+cube.getCoordinates()[7].getY()+" L"+cube.getCoordinates()[6].getX()+" "+cube.getCoordinates()[6].getY()+" L"+cube.getCoordinates()[2].getX()+" "+cube.getCoordinates()[2].getY()+"\"/>"+"\n\n";
    }

    private void drawPyramid(Pyramid pyramid) {
        svgString += "\t\t<path opacity=\"0.75\" fill=\"" + color + "\" d=\" "+"M"+ pyramid.getCoordinates()[0].getX() +" "+ pyramid.getCoordinates()[0].getY()  +" L"+ pyramid.getCoordinates()[1].getX()  +" "+ pyramid.getCoordinates()[1].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[0].getX() +" "+pyramid.getCoordinates()[0].getY()+"\"/>" + "\n";
        svgString += "\t\t<path opacity=\"0.75\" fill=\"" + color + "\" d=\" "+"M"+ pyramid.getCoordinates()[0].getX() +" "+ pyramid.getCoordinates()[0].getY()  +" L"+ pyramid.getCoordinates()[2].getX()  +" "+ pyramid.getCoordinates()[2].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[0].getX() +" "+pyramid.getCoordinates()[0].getY()+"\"/>" + "\n";
        svgString += "\t\t<path opacity=\"0.75\" fill=\"" + color + "\" d=\" "+"M"+ pyramid.getCoordinates()[1].getX() +" "+ pyramid.getCoordinates()[1].getY()  +" L"+ pyramid.getCoordinates()[3].getX()  +" "+ pyramid.getCoordinates()[3].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[1].getX() +" "+pyramid.getCoordinates()[1].getY()+"\"/>" + "\n";
        svgString += "\t\t<path opacity=\"0.75\" fill=\"" + color + "\" d=\" "+"M"+ pyramid.getCoordinates()[2].getX() +" "+ pyramid.getCoordinates()[2].getY()  +" L"+ pyramid.getCoordinates()[3].getX()  +" "+ pyramid.getCoordinates()[3].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[2].getX() +" "+pyramid.getCoordinates()[2].getY()+"\"/>" + "\n\n";
    }

    private void drawArrow(Arrow arrow) {

        svgString += "\t\t<path opacity=\"0.75\" d=\" "+"M"+ arrow.getVertex1().getX()+" "+ arrow.getVertex1().getY()  +" L"+ arrow.getVertex2().getX() +" "+ arrow.getVertex2().getY() +"\"/>" + "\n\n";
        svgString += "\t\t<circle opacity=\"0.75\" cx=\""+arrow.getVertex2().getX()+"\" cy=\""+arrow.getVertex2().getY()+"\" r=\"2\" stroke=\"black\" stroke-width=\"1\" fill=\"black\" />\n";
    }

    /**
     * Cube kernel => Pink, Cube CNN =>Orange, Dense Layer =>Turquoise
     * @param cube
     * @return cube color.
     */

    private String selectColor(Cube cube) {
        if (cube.isKernel()) {
            return "pink";
        }
        else if(!cube.isKernel() && !cube.isDenseLayer()){
            return "orange";
        }
        else{
            return "darkturquoise";
        }
    }



    /**
     * Method that rotates the cube points
     * @return The 8 coordinates of the cube transformed
     */
    private void doTransformations(Coordinate[] coordinates) {

        MatrixController matrixController=new MatrixController();

        //Rotation on the Y-axis
        matrixController.rotate("y",coordinates,ALFA);

        //Rotation on the X-axis
        matrixController.rotate("x",coordinates,ALFA);
    }

    private Coordinate calculateCenter(Coordinate [] coordinates){
        double x=(coordinates[0].getX()+coordinates[1].getX()+coordinates[2].getX()+coordinates[3].getX())/4;
        double y=(coordinates[0].getY()+coordinates[1].getY()+coordinates[2].getY()+coordinates[3].getY())/4;
        double z=(coordinates[0].getZ()+coordinates[1].getZ()+coordinates[2].getZ()+coordinates[3].getZ())/4;
        return new Coordinate(x,y,z);
    }

    private Coordinate calculateRandomPoint(Coordinate[] coordinates) {
        double x1=coordinates[4].getZ();
        double x2=coordinates[5].getZ();

        double y1=coordinates[4].getY();
        double y2=coordinates[6].getY();

        double x_random=x2 + (Math.random() * ((x1 -x2) + 1)); //[-x,x]
        double y_random=y2 + (Math.random() * ((y1 -y2) + 1)); //[-y,y]
        return new Coordinate(coordinates[4].getX(),y_random,x_random);
    }

    private void addHeader(){
        this.svgString="<svg width=\"1000px\" height=\"1000px\" viewBox=\"-300 -400 1000 1000\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                        "\t<g stroke=\"black\" stroke-width=\"1\">\n";
    }
    private void addFooter(){
        this.svgString+="\t </g>\n"+"</svg>";
    }

    private void shift(List<Cube> modelQueue){
        MatrixController matrixController=new MatrixController();
        for(int i=0;i<modelQueue.size();i++){
            Cube cube=modelQueue.get(i);
            matrixController.rotate("y",cube.getCoordinates(),90);
            //Difference in length between the Z of the previous cube (it is X because it is rotated) and the Z of the current cube
            if(!cube.isKernel() && i!=0){
                double l=Math.abs(lengthAux-cube.getCoordinates()[4].getX());
                length=l+SHIFT;
            }
            matrixController.move("x",cube.getCoordinates(),length);

            if(!cube.isKernel()) {
                lengthAux = cube.getCoordinates()[0].getX();
            }
            if(cube.isKernel()){
                Cube cube_actual=modelQueue.get(i-1);
                moveKernel(cube_actual,cube);
            }
        }
    }
    private void moveKernel(Cube cube_actual,Cube kernel){
        MatrixController matrixController=new MatrixController();
        double dif=(cube_actual.getCoordinates()[3].getY()-kernel.getCoordinates()[3].getY());

        double x_random=-dif + (Math.random() * ((dif +dif) + 1)); //[-x,x]
        double y_random=-dif + (Math.random() * ((dif +dif) + 1)); //[-y,y]

        matrixController.move("z",kernel.getCoordinates(),x_random);
        matrixController.move("y",kernel.getCoordinates(),y_random);
    }

}
