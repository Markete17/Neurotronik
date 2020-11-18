package Drawer;

import Data.Coordinate;
import Matrix.*;
import Shapes.Arrow;
import Shapes.Cube;
import Shapes.Pyramid;

import java.util.Arrays;
import java.util.List;

/** CON POLYLINE
 * pathBase +="<polyline points=\""+x[0].getX()+","+x[0].getY()+" "+x[1].getX()+","+x[1].getY()+" "+x[3].getX()+","+x[3].getY()+" "+x[2].getX()+","+x[2].getY()+" "+x[0].getX()+","+x[0].getY()+"\" "+"style=\"fill:"+color+";stroke:black;stroke-width:2\"/>\n";
 *                 pathBase +="<polyline points=\""+x[4].getX()+","+x[4].getY()+" "+x[5].getX()+","+x[5].getY()+" "+x[7].getX()+","+x[7].getY()+" "+x[6].getX()+","+x[6].getY()+" "+x[4].getX()+","+x[4].getY()+"\" "+"style=\"fill:"+color+";stroke:black;stroke-width:2\"/>\n";
 *                 pathBase +="<polyline points=\""+x[0].getX()+","+x[0].getY()+" "+x[4].getX()+","+x[4].getY()+" "+x[6].getX()+","+x[6].getY()+" "+x[2].getX()+","+x[2].getY()+" "+x[0].getX()+","+x[0].getY()+"\" "+"style=\"fill:"+color+";stroke:black;stroke-width:2\"/>\n";
 *
 *                 pathBase +="<polyline points=\""+x[1].getX()+","+x[1].getY()+" "+x[5].getX()+","+x[5].getY()+" "+x[7].getX()+","+x[7].getY()+" "+x[3].getX()+","+x[3].getY()+" "+x[1].getX()+","+x[1].getY()+"\" "+"style=\"fill:"+color+";stroke:black;stroke-width:3\"/>\n";
 *                 pathBase +="<polyline points=\""+x[0].getX()+","+x[0].getY()+" "+x[1].getX()+","+x[1].getY()+" "+x[5].getX()+","+x[5].getY()+" "+x[4].getX()+","+x[4].getY()+" "+x[0].getX()+","+x[0].getY()+"\" "+"style=\"fill:"+color+";stroke:black;stroke-width:3\"/>\n";
 *                 pathBase +="<polyline points=\""+x[2].getX()+","+x[2].getY()+" "+x[3].getX()+","+x[3].getY()+" "+x[7].getX()+","+x[7].getY()+" "+x[6].getX()+","+x[6].getY()+" "+x[2].getX()+","+x[2].getY()+"\" "+"style=\"fill:"+color+";stroke:black;stroke-width:3\"/>\n";
 *
 *                 */

public class SvgController {
    //CONSTANTS
    final double ALFA=60;
    final int SHIFT=50;


    double SHIFT_Y=0;
    double shift_YAux=0;
    double length=0;
    double lenghtAux=0;
    boolean activate=false;
    String color;

    //final svg file
    String svgString="";


    public String draw(List<Cube> modelQueue) {
        this.addHeader();
            for(int i=0;i<modelQueue.size();i++) {
                Cube cube=modelQueue.get(i);
                color=selectColor(cube);

                if(!cube.isKernel()){
                    length+=lenghtAux;
                    SHIFT_Y+=0;
                }

                doTransformations(cube.getCoordinates());

                //Cube Path
                svgString += "\t\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+ cube.getCoordinates()[0].getX() +" "+ cube.getCoordinates()[0].getY() +" L"+ cube.getCoordinates()[1].getX() +" "+ cube.getCoordinates()[1].getY() +" L"+ cube.getCoordinates()[3].getX() +" "+ cube.getCoordinates()[3].getY() +" L"+ cube.getCoordinates()[2].getX() +" "+cube.getCoordinates()[2].getY()+" L"+ cube.getCoordinates()[0].getX() +" "+cube.getCoordinates()[0].getY()+"\"/>" + "\n";
                svgString += "\t\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+ cube.getCoordinates()[4].getX() +" "+cube.getCoordinates()[4].getY()+" L"+ cube.getCoordinates()[5].getX() +" "+cube.getCoordinates()[5].getY()+" L"+ cube.getCoordinates()[7].getX() +" "+cube.getCoordinates()[7].getY()+" L"+cube.getCoordinates()[6].getX()+" "+cube.getCoordinates()[6].getY()+" L"+cube.getCoordinates()[4].getX()+" "+cube.getCoordinates()[4].getY()+"\"/>" + "\n";
                svgString += "\t\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+ cube.getCoordinates()[0].getX()+" "+cube.getCoordinates()[0].getY()+" L"+cube.getCoordinates()[4].getX()+" "+cube.getCoordinates()[4].getY()+" L"+cube.getCoordinates()[6].getX()+" "+cube.getCoordinates()[6].getY()+" L"+cube.getCoordinates()[2].getX()+" "+cube.getCoordinates()[2].getY()+" L"+cube.getCoordinates()[0].getX()+" "+cube.getCoordinates()[0].getY()+"\"/>" + "\n";
                svgString += "\t\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+ cube.getCoordinates()[1].getX()+" "+cube.getCoordinates()[1].getY()+" L"+cube.getCoordinates()[5].getX()+" "+cube.getCoordinates()[5].getY()+" L"+cube.getCoordinates()[7].getX()+" "+cube.getCoordinates()[7].getY()+" L"+cube.getCoordinates()[3].getX()+" "+cube.getCoordinates()[3].getY()+" L"+cube.getCoordinates()[1].getX()+" "+cube.getCoordinates()[1].getY()+"\"/>" + "\n";
                svgString += "\t\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+cube.getCoordinates()[0].getX()+" "+cube.getCoordinates()[0].getY()+" L"+cube.getCoordinates()[1].getX()+" "+cube.getCoordinates()[1].getY()+" L"+cube.getCoordinates()[5].getX()+" "+cube.getCoordinates()[5].getY()+" L"+cube.getCoordinates()[4].getX()+" "+cube.getCoordinates()[4].getY()+" L"+cube.getCoordinates()[0].getX()+" "+cube.getCoordinates()[0].getY()+"\"/>" + "\n";
                svgString += "\t\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\" "+"M"+cube.getCoordinates()[2].getX()+" "+cube.getCoordinates()[2].getY()+" L"+cube.getCoordinates()[3].getX()+" "+cube.getCoordinates()[3].getY()+" L"+cube.getCoordinates()[7].getX()+" "+cube.getCoordinates()[7].getY()+" L"+cube.getCoordinates()[6].getX()+" "+cube.getCoordinates()[6].getY()+" L"+cube.getCoordinates()[2].getX()+" "+cube.getCoordinates()[2].getY()+"\"/>"+"\n\n";
                if(!cube.isKernel()){

                    lenghtAux=(cube.getZ())+SHIFT;

                    shift_YAux=cube.getZ()/2;
                }
                if(activate){

                    Cube kernelCube=modelQueue.get(i-1);
                    Coordinate vertex=calculateCenter(Arrays.copyOfRange(cube.getCoordinates(),4,8));

                    Pyramid pyramid=new Pyramid(Arrays.copyOf(kernelCube.getCoordinates(),4),new Coordinate(vertex.getX(),vertex.getY(),vertex.getZ()));
                    color="green";
                    svgString += "\t\t\t<path opacity=\"0.75\" fill=\"" + color + "\" d=\" "+"M"+ pyramid.getCoordinates()[0].getX() +" "+ pyramid.getCoordinates()[0].getY()  +" L"+ pyramid.getCoordinates()[1].getX()  +" "+ pyramid.getCoordinates()[0].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[0].getX() +" "+pyramid.getCoordinates()[0].getY()+"\"/>" + "\n";
                    svgString += "\t\t\t<path opacity=\"0.75\" fill=\"" + color + "\" d=\" "+"M"+ pyramid.getCoordinates()[0].getX() +" "+ pyramid.getCoordinates()[0].getY()  +" L"+ pyramid.getCoordinates()[2].getX()  +" "+ pyramid.getCoordinates()[2].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[0].getX() +" "+pyramid.getCoordinates()[0].getY()+"\"/>" + "\n";
                    svgString += "\t\t\t<path opacity=\"0.75\" fill=\"" + color + "\" d=\" "+"M"+ pyramid.getCoordinates()[1].getX() +" "+ pyramid.getCoordinates()[1].getY()  +" L"+ pyramid.getCoordinates()[3].getX()  +" "+ pyramid.getCoordinates()[3].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[1].getX() +" "+pyramid.getCoordinates()[1].getY()+"\"/>" + "\n";
                    svgString += "\t\t\t<path opacity=\"0.75\" fill=\"" + color + "\" d=\" "+"M"+ pyramid.getCoordinates()[2].getX() +" "+ pyramid.getCoordinates()[2].getY()  +" L"+ pyramid.getCoordinates()[3].getX()  +" "+ pyramid.getCoordinates()[3].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[2].getX() +" "+pyramid.getCoordinates()[2].getY()+"\"/>" + "\n";
                    activate=false;
                }

                if(cube.isDenseLayer()){
                    Cube lastCube=modelQueue.get(i-1);

                    Coordinate vertex1=calculateCenter(Arrays.copyOfRange(lastCube.getCoordinates(),0,4));
                    Coordinate vertex2=calculateCenter(Arrays.copyOfRange(cube.getCoordinates(),4,8));

                    Arrow arrow=new Arrow(vertex1,vertex2);
                    svgString += "\t\t\t<path opacity=\"0.75\" d=\" "+"M"+ arrow.getCoordinates()[0].getX()+" "+ arrow.getCoordinates()[0].getY()  +" L"+ arrow.getCoordinates()[1].getX()  +" "+ arrow.getCoordinates()[1].getY()  +" L"+ arrow.getCoordinates()[2].getX() +" "+ arrow.getCoordinates()[2].getY()+" M"+ arrow.getCoordinates()[1].getX() +" "+arrow.getCoordinates()[1].getY()+" L"+ arrow.getCoordinates()[3].getX()  +" "+ arrow.getCoordinates()[3].getY()  +"\"/>" + "\n";
                }

                if(cube.isKernel()){
                    activate=true;
                }
            }

        this.addFooter();
        return this.svgString;
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
        Matrix matrixRotX=new RotationMatrixX(ALFA);
        Matrix matrixRotY=new RotationMatrixY(ALFA);

        //Rotation on the Y-axis
        Coordinate a0=matrixRotY.multiply(matrixRotY.getMatrix(),coordinates[0].getCoordinateMatrix());
        Coordinate a1=matrixRotY.multiply(matrixRotY.getMatrix(),coordinates[1].getCoordinateMatrix());
        Coordinate a2=matrixRotY.multiply(matrixRotY.getMatrix(),coordinates[2].getCoordinateMatrix());
        Coordinate a3=matrixRotY.multiply(matrixRotY.getMatrix(),coordinates[3].getCoordinateMatrix());
        Coordinate a4=matrixRotY.multiply(matrixRotY.getMatrix(),coordinates[4].getCoordinateMatrix());
        Coordinate a5=matrixRotY.multiply(matrixRotY.getMatrix(),coordinates[5].getCoordinateMatrix());
        Coordinate a6=matrixRotY.multiply(matrixRotY.getMatrix(),coordinates[6].getCoordinateMatrix());
        Coordinate a7=matrixRotY.multiply(matrixRotY.getMatrix(),coordinates[7].getCoordinateMatrix());

        //Rotation on the X-axis
        coordinates[0]=matrixRotX.multiply(matrixRotX.getMatrix(),a0.getCoordinateMatrix());
        coordinates[1]=matrixRotX.multiply(matrixRotX.getMatrix(),a1.getCoordinateMatrix());
        coordinates[2]=matrixRotX.multiply(matrixRotX.getMatrix(),a2.getCoordinateMatrix());
        coordinates[3]=matrixRotX.multiply(matrixRotX.getMatrix(),a3.getCoordinateMatrix());
        coordinates[4]=matrixRotX.multiply(matrixRotX.getMatrix(),a4.getCoordinateMatrix());
        coordinates[5]=matrixRotX.multiply(matrixRotX.getMatrix(),a5.getCoordinateMatrix());
        coordinates[6]=matrixRotX.multiply(matrixRotX.getMatrix(),a6.getCoordinateMatrix());
        coordinates[7]=matrixRotX.multiply(matrixRotX.getMatrix(),a7.getCoordinateMatrix());

        //Translation
        coordinates[0].setX(coordinates[0].getX()+length);coordinates[0].setY(coordinates[0].getY()-SHIFT_Y);
        coordinates[1].setX(coordinates[1].getX()+length);coordinates[1].setY(coordinates[1].getY()-SHIFT_Y);
        coordinates[2].setX(coordinates[2].getX()+length);coordinates[2].setY(coordinates[2].getY()-SHIFT_Y);
        coordinates[3].setX(coordinates[3].getX()+length);coordinates[3].setY(coordinates[3].getY()-SHIFT_Y);
        coordinates[4].setX(coordinates[4].getX()+length);coordinates[4].setY(coordinates[4].getY()-SHIFT_Y);
        coordinates[5].setX(coordinates[5].getX()+length);coordinates[5].setY(coordinates[5].getY()-SHIFT_Y);
        coordinates[6].setX(coordinates[6].getX()+length);coordinates[6].setY(coordinates[6].getY()-SHIFT_Y);
        coordinates[7].setX(coordinates[7].getX()+length);coordinates[7].setY(coordinates[7].getY()-SHIFT_Y);
    }

    private Coordinate calculateCenter(Coordinate [] coordinates){
        double x=(coordinates[0].getX()+coordinates[1].getX()+coordinates[2].getX()+coordinates[3].getX())/4;
        double y=(coordinates[0].getY()+coordinates[1].getY()+coordinates[2].getY()+coordinates[3].getY())/4;
        double z=(coordinates[0].getZ()+coordinates[1].getZ()+coordinates[2].getZ()+coordinates[3].getZ())/4;
        return new Coordinate(x,y,z);
    }

    private void addHeader(){
        this.svgString="<html>\n" +
                "\t"+"<head>\n" +
                "\t\t"+"<title>Page Title</title>\n" +
                "\t\t"+"<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n" +
                "\t"+"</head>\n" +
                "\t"+"<body>\n" +
                "\t\t<svg width=\"1000px\" height=\"1000px\" viewBox=\"-300 -400 1000 1000\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "\n" +
                "\t\t<g stroke=\"black\" stroke-width=\"1\">\n";
    }
    private void addFooter(){
        this.svgString+="\t\t </g>\n\t\t"+"</svg> \n" +"\t"+"</body>\n"+"</html>";
    }
}
