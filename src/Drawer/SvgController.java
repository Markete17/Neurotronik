package Drawer;

import Data.Coordinate;
import Matrix.*;
import Shapes.Arrow;
import Shapes.Cube;
import Shapes.Pyramid;
import Tree.NeuralNetworkTree;
import Tree.Node;
import java.util.Arrays;
import java.util.List;

public class SvgController {

    //CONSTANTS
    //Vista aerea AlfaX=90 AlfaY=90
    //Vista normal AlfaX=30 AlfaY=60
    final double ALFAX=30;
    final double ALFAY=60;
    final int SHIFTZ =30;   //SHIFT Nodes
    final int SHIFTX =100; //SHIFT between nodes
    final int SHIFTP=75; //Parent SHIFT

    //GLOBAL VARIABLES
    double length=0;
    double lengthAux=0;
    boolean activate=false;
    Color color;
    double length2=0;
    double length2Aux=0;

    //FINAL SVG FILE
    String svgString="";
    String cubeString="";
    String kernelString="";

    public SvgController() {
        color=new Color("beige","red","darkturquoise","pink","black");
    }

    public String draw(NeuralNetworkTree modelTree) {
        this.shiftTree(modelTree);
        this.addHeader();
        for (int i = 0; i < modelTree.getNodes().length; i++) {
            for (int j = 0; j < modelTree.getNodes()[i].size(); j++) {
                Node node=modelTree.getNodes()[i].get(j);
                drawNode(node);
                clear();
                if(modelTree.isParent(node)){
                    drawUnions(node);
                }
            }
        }
        this.addFooter();
        return svgString;
    }

    private void clear() {
        svgString+=kernelString+cubeString;
        kernelString="";
        cubeString="";
    }

    public void shiftTree(NeuralNetworkTree modelTree) {
        modelTree.initializeNodes();
        for(int i=0;i<modelTree.getNodes().length;i++){
            //First Level
            if(i==0) {
                for (int j = 0; j < modelTree.getNodes()[i].size(); j++) {
                    Node node = modelTree.getNodes()[i].get(j);
                    if(j!=0){
                        double l;
                        if(node.getCubeList().get(0).getX()>modelTree.getNodes()[i].get(j-1).getCubeList().get(0).getX()) {

                            l = Math.abs(node.getCubeList().get(0).getCoordinates()[0].getX() - length2Aux);
                        }
                        else{
                            l = Math.abs(length2Aux-node.getCubeList().get(0).getCoordinates()[0].getX());
                        }
                        length2+=l+ SHIFTX;

                    }
                    length2Aux = node.getCubeList().get(0).getCoordinates()[1].getX();
                    this.shiftNode(node);
                    length=0;
                    lengthAux=0;
                }
            }
            //Other levels
            else{
                for (int j = 0; j < modelTree.getNodes()[i].size(); j++) {
                    Node node = modelTree.getNodes()[i].get(j);
                    Node firstChild=node.getChildren().get(0);
                    Node lastChild=node.getChildren().get(node.getChildren().size()-1);
                    Coordinate centerChild1=calculateCenter(firstChild.getCubeList().get(0).getCoordinates());
                    Coordinate centerChild2=calculateCenter(lastChild.getCubeList().get(0).getCoordinates());
                    length2=(Math.abs(centerChild1.getX()+centerChild2.getX())/2);
                    double depth=maxDepth(node.getChildren());
                    double depthCube=node.getCubeList().get(0).getCoordinates()[1].getZ();
                    double l=Math.abs(depth+depthCube);
                    length=l+SHIFTP;
                    this.shiftNode(node);
                }
                length=0;
                lengthAux=0;
            }
            length2=0;
        }
    }

    private double maxDepth(List<Node> children) {
        double max=Double.MIN_VALUE;
        for(Node child:children){
            if(child.getLastCube().getCoordinates()[0].getZ()>max){
                max=child.getLastCube().getCoordinates()[0].getZ();
            }
        }
        return max;
    }

    /**
     * Draw a node with SVG
     * @param node
     * @return
     */
    public void drawNode(Node node) {
        List<Cube> modelQueue=node.getCubeList();
            for(int i=0;i<modelQueue.size();i++) {
                Cube cube=modelQueue.get(i);
                doTransformations(cube.getCoordinates());
                drawCube(cube);
                if(activate){

                    Cube kernelCube=modelQueue.get(i-1);
                    Coordinate vertex=calculateRandomPoint(cube.getCoordinates());

                    Pyramid pyramid=new Pyramid(Arrays.copyOf(kernelCube.getCoordinates(),4),new Coordinate(vertex.getX(),vertex.getY(),vertex.getZ()));
                    drawPyramid(pyramid);
                    activate=false;
                }

                if(cube.isDenseLayer()){
                    try {
                        Cube lastCube = modelQueue.get(i - 1);

                        Coordinate vertex1 = calculateCenter(Arrays.copyOfRange(lastCube.getCoordinates(), 0, 4));
                        Coordinate vertex2 = calculateCenter(Arrays.copyOfRange(cube.getCoordinates(), 4, 8));

                        Arrow arrow = new Arrow(vertex1, vertex2);
                        drawArrow(arrow);
                    }catch(Exception e){

                    }
                }

                if(cube.isKernel()){
                    activate=true;
                }
            }


    }

    /**
     * Draw the junction between the parent and child nodes.
     * @param parent
     */

    private void drawUnions(Node parent) {
        for(Node child:parent.getChildren()){
            Cube lastCube = child.getLastCube();
            Coordinate vertex1 = calculateCenter(Arrays.copyOfRange(lastCube.getCoordinates(), 0, 4));
            Coordinate vertex2 = calculateCenter(Arrays.copyOfRange(parent.getCubeList().get(0).getCoordinates(), 4, 8));
            Arrow arrow = new Arrow(vertex1, vertex2);
            drawArrow(arrow);
        }

    }

    /**
     * Draw a cube
     * @param cube
     */
    private void drawCube(Cube cube) {
        //Cube Path
        String color=selectColor(cube);
        if(cube.isKernel()) {
            kernelString += "\t\t<path opacity=\"1\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
            kernelString += "\t\t<path opacity=\"1\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + "\"/>" + "\n";
            kernelString += "\t\t<path opacity=\"1\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
            kernelString += "\t\t<path opacity=\"1\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + "\"/>" + "\n";
            kernelString += "\t\t<path opacity=\"1\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
            kernelString += "\t\t<path opacity=\"1\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + "\"/>" + "\n\n";
        }
        else {
            cubeString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
            cubeString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + "\"/>" + "\n";
            cubeString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
            cubeString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + "\"/>" + "\n";
            cubeString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
            cubeString += "\t\t<path opacity=\"0.5\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + "\"/>" + "\n\n";
            cubeString += "\t\t<text x=\"" + (cube.getCoordinates()[4].getX() + cube.getCoordinates()[6].getX()) / 2 + "\" y=\"" + (cube.getCoordinates()[4].getY() + cube.getCoordinates()[6].getY()) / 2 + "\" font-size=\"5\">" + (int) (cube.getY()) + "</text>\n";
            cubeString += "\t\t<text x=\"" + (cube.getCoordinates()[6].getX() + cube.getCoordinates()[7].getX()) / 2 + "\" y=\"" + (cube.getCoordinates()[6].getY() + cube.getCoordinates()[7].getY()) / 2 + "\" font-size=\"5\">" + (int) (cube.getX()) + "</text>\n";
            cubeString += "\t\t<text x=\"" + (cube.getCoordinates()[4].getX() + cube.getCoordinates()[0].getX()) / 2 + "\" y=\"" + (cube.getCoordinates()[0].getY() + cube.getCoordinates()[4].getY()) / 2 + "\" font-size=\"5\">" + (int) (cube.getZ()) + "</text>\n";
        }
        }

    /**
     * Draw a pyramid
     * @param pyramid
     */
    private void drawPyramid(Pyramid pyramid) {
        svgString += "\t\t<path opacity=\"0.75\" fill=\"" + color.getPyramid() + "\" d=\""+"M"+ pyramid.getCoordinates()[0].getX() +" "+ pyramid.getCoordinates()[0].getY()  +" L"+ pyramid.getCoordinates()[1].getX()  +" "+ pyramid.getCoordinates()[1].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[0].getX() +" "+pyramid.getCoordinates()[0].getY()+"\"/>" + "\n";
        svgString += "\t\t<path opacity=\"0.75\" fill=\"" + color.getPyramid() + "\" d=\""+"M"+ pyramid.getCoordinates()[0].getX() +" "+ pyramid.getCoordinates()[0].getY()  +" L"+ pyramid.getCoordinates()[2].getX()  +" "+ pyramid.getCoordinates()[2].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[0].getX() +" "+pyramid.getCoordinates()[0].getY()+"\"/>" + "\n";
        svgString += "\t\t<path opacity=\"0.75\" fill=\"" + color.getPyramid() + "\" d=\""+"M"+ pyramid.getCoordinates()[1].getX() +" "+ pyramid.getCoordinates()[1].getY()  +" L"+ pyramid.getCoordinates()[3].getX()  +" "+ pyramid.getCoordinates()[3].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[1].getX() +" "+pyramid.getCoordinates()[1].getY()+"\"/>" + "\n";
        svgString += "\t\t<path opacity=\"0.75\" fill=\"" + color.getPyramid() + "\" d=\""+"M"+ pyramid.getCoordinates()[2].getX() +" "+ pyramid.getCoordinates()[2].getY()  +" L"+ pyramid.getCoordinates()[3].getX()  +" "+ pyramid.getCoordinates()[3].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[2].getX() +" "+pyramid.getCoordinates()[2].getY()+"\"/>" + "\n\n";
    }

    /**
     * Draw an arrow
     * @param arrow
     */
    private void drawArrow(Arrow arrow) {

        svgString += "\t\t<path opacity=\"0.75\" stroke=\""+ color.getArrow() + "\" d=\""+"M"+ arrow.getVertex1().getX()+" "+ arrow.getVertex1().getY()  +" L"+ arrow.getVertex2().getX() +" "+ arrow.getVertex2().getY() +"\"/>" + "\n\n";
        svgString += "\t\t<circle opacity=\"0.75\" cx=\""+arrow.getVertex2().getX()+"\" cy=\""+arrow.getVertex2().getY()+"\" r=\"1\" fill=\""+color.getArrow()+"\" />\n";
    }

    /**
     * Select the color of the cube.
     * @param cube
     * @return cube color.
     */

    private String selectColor(Cube cube) {
        if (cube.isKernel()) {
            return color.getKernel();
        }
        else if(!cube.isKernel() && !cube.isDenseLayer()){
            return color.getCube();
        }
        else{
            return color.getDense();
        }
    }



    /**
     * Method that rotates the cube points
     * @return The 8 coordinates of the cube transformed
     */
    private void doTransformations(Coordinate[] coordinates) {

        MatrixController matrixController=new MatrixController();

        //Rotation on the Y-axis
        matrixController.rotate("y",coordinates,ALFAY);

        //Rotation on the X-axis
        matrixController.rotate("x",coordinates,ALFAX);
    }

    /**
     * Compute the center of a square
     * @param coordinates
     * @return
     */
    private Coordinate calculateCenter(Coordinate [] coordinates){
        double x=(coordinates[0].getX()+coordinates[1].getX()+coordinates[2].getX()+coordinates[3].getX())/4;
        double y=(coordinates[0].getY()+coordinates[1].getY()+coordinates[2].getY()+coordinates[3].getY())/4;
        double z=(coordinates[0].getZ()+coordinates[1].getZ()+coordinates[2].getZ()+coordinates[3].getZ())/4;
        return new Coordinate(x,y,z);
    }

    /**
     * Compute a random point for the kernel
     * @param coordinates
     * @return
     */
    private Coordinate calculateRandomPoint(Coordinate[] coordinates) {
        double x1=coordinates[4].getX();
        double x2=coordinates[5].getX();

        double y1=coordinates[4].getY();
        double y2=coordinates[6].getY();

        double x_random=x2 + (Math.random() * ((x1 -x2) + 1)); //[-x,x]
        double y_random=y2 + (Math.random() * ((y1 -y2) + 1)); //[-y,y]
        return new Coordinate(x_random,y_random,coordinates[4].getZ());
    }

    /**
     * SVG header
     */
    private void addHeader(){
        this.svgString="<svg width=\"1000px\" height=\"1000px\" viewBox=\"-100 -400 1000 1000\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                        "\t<g stroke=\"black\" stroke-width=\"0.5\">\n";
    }

    /**
     * SVG Footer
     */
    private void addFooter(){
        this.svgString+="\t </g>\n"+"</svg>";
    }

    /**
     * Shift the Neural Network
     * @param node
     */
    private void shiftNode(Node node){
        List<Cube> modelQueue=node.getCubeList();
        MatrixController matrixController=new MatrixController();
        for(int i=0;i<modelQueue.size();i++){
            Cube cube=modelQueue.get(i);

            matrixController.move("x",cube.getCoordinates(),length2);

            //Difference in length between the Z of the previous cube (it is X because it is rotated) and the Z of the current cube

            if(!cube.isKernel() && i!=0){
                double l=Math.abs(lengthAux-cube.getCoordinates()[4].getZ());
                length=l+ SHIFTZ;
            }
            matrixController.move("z",cube.getCoordinates(),length);

            if(!cube.isKernel()) {
                lengthAux = cube.getCoordinates()[0].getZ();
            }
            if(cube.isKernel()){
                Cube cube_actual=modelQueue.get(i-1);
                moveKernel(cube_actual,cube);
            }
        }
    }

    /**
     * Move the kernel to a random position
     * @param cube_actual
     * @param kernel
     */
    private void moveKernel(Cube cube_actual,Cube kernel){
        MatrixController matrixController=new MatrixController();
        double dif=(cube_actual.getCoordinates()[3].getY()-kernel.getCoordinates()[3].getY());

        double x_random=-dif + (Math.random() * ((dif +dif) + 1)); //[-x,x]
        double y_random=-dif + (Math.random() * ((dif +dif) + 1)); //[-y,y]

        matrixController.move("x",kernel.getCoordinates(),x_random);
        matrixController.move("y",kernel.getCoordinates(),y_random);
    }
}
