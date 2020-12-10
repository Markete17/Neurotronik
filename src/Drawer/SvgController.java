package Drawer;

import Data.Coordinate;
import Matrix.*;
import Shapes.Arrow;
import Shapes.Cube;
import Shapes.Pyramid;
import Tree.NeuralNetworkTree;
import Tree.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SvgController {

    //CONSTANTS
    //Vista aerea AlfaX=90 AlfaY=90
    //Vista normal AlfaX=30 AlfaY=60
    //SHIFTZ = 30 SHIFTX = 100 SHIFTP = 75

    private Alfa alfa;
    private Color color;
    private Font font;
    private Shift shift;
    private Stroke stroke;
    private ViewBox viewBox;

    //GLOBAL VARIABLES
    double length=0;
    double lengthAux=0;
    boolean activate=false;
    double length2=0;
    double length2Aux=0;

    //FINAL SVG FILE
    List<SortNode> drawOrderList=new ArrayList<>();
    String svgString="";
    String kernelString="";

    private class SortNode implements Comparable<SortNode>{
        private String svgString;
        private double z;

        public String getSvgString() {
            return svgString;
        }

        public double getZ() {
            return z;
        }

        public SortNode(String svgString, double z) {
            this.svgString = svgString;
            this.z = z;
        }

        // Compare method
        @Override
        public int compareTo(SortNode sn) {
            if(this.z<sn.getZ()){
                return 1;
            }
            else if(this.z==sn.getZ()){
                return -1;
            }
            else{
                return -1;
            }
        }
    }

    public SvgController(DrawSettings settings) {
        this.alfa=settings.getAlfa();
        this.color=settings.getColor();
        this.font=settings.getFont();
        this.shift=settings.getShift();
        this.stroke=settings.getStroke();
        this.viewBox=settings.getViewBox();
    }

    public String draw(NeuralNetworkTree modelTree) {
        this.shiftTree(modelTree);
        this.addHeader();
        for (int i = 0; i < modelTree.getNodes().length; i++) {
            for (int j = 0; j < modelTree.getNodes()[i].size(); j++) {
                Node node=modelTree.getNodes()[i].get(j);
                drawNode(node);
                if(modelTree.isParent(node)){
                    drawUnions(node);
                }
            }
        }
        Collections.sort(drawOrderList);
        svgString+=kernelString;
        for(int i = 0;i<drawOrderList.size();i++){
            svgString+=drawOrderList.get(i).getSvgString();
        }
        this.addFooter();
        return svgString;
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
                        length2+=l+ shift.getShiftNodes();

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
                    length=l+shift.getShiftParent();
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
     * @param node the node of the tree to be drawn.
     */
    public void drawNode(Node node) {
        List<Cube> modelQueue=node.getCubeList();
            for(int i=0;i<modelQueue.size();i++) {
                Cube cube=modelQueue.get(i);
                doTransformations(cube.getCoordinates());

                if(activate){

                    Cube kernelCube=modelQueue.get(i-1);
                    Coordinate vertex=calculateRandomPoint(cube.getCoordinates());

                    Pyramid pyramid=new Pyramid(Arrays.copyOf(kernelCube.getCoordinates(),4),new Coordinate(vertex.getX(),vertex.getY(),vertex.getZ()));
                    drawPyramid(pyramid);
                    activate=false;
                }
                drawCube(cube);

                if(cube.isDenseLayer()){
                    try {
                        Cube lastCube = modelQueue.get(i - 1);

                        Coordinate vertex1 = calculateCenter(Arrays.copyOfRange(lastCube.getCoordinates(), 0, 4));
                        Coordinate vertex2 = calculateCenter(Arrays.copyOfRange(cube.getCoordinates(), 4, 8));

                        Arrow arrow = new Arrow(vertex1, vertex2);
                        drawArrow(arrow);
                    }catch(Exception e){
                        //continue
                    }
                }

                if(cube.isKernel()){
                    activate=true;
                }
            }
    }

    /**
     * Draw the junction between the parent and child nodes.
     * @param parent node
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
     * @param cube to draw
     */
    private void drawCube(Cube cube) {
        //Cube Path
        String color=selectColor(cube);
        double opacity=selectOpacity(cube);
        String svg="";
            svg += "\t\t<path opacity=\""+opacity+"\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
            svg += "\t\t<path opacity=\""+opacity+"\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + "\"/>" + "\n";
            svg += "\t\t<path opacity=\""+opacity+"\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
            svg += "\t\t<path opacity=\""+opacity+"\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + "\"/>" + "\n";
            svg += "\t\t<path opacity=\""+opacity+"\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
            svg += "\t\t<path opacity=\""+opacity+"\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + "\"/>" + "\n";
            svg += "\t\t<text x=\"" + ((cube.getCoordinates()[4].getX() + cube.getCoordinates()[6].getX()) / 2) + "\" y=\"" + (cube.getCoordinates()[4].getY() + cube.getCoordinates()[6].getY()) / 2  + "\" font-family=\""+font.getFont_family()+"\" font-size=\""+font.getFont_size()+"\">" + (int) (cube.getY()) + "</text>\n";
            svg += "\t\t<text x=\"" + ((cube.getCoordinates()[6].getX() + cube.getCoordinates()[7].getX()) / 2) + "\" y=\"" + (cube.getCoordinates()[6].getY() + cube.getCoordinates()[7].getY()) / 2 + "\" font-family=\""+font.getFont_family()+"\" font-size=\""+font.getFont_size()+"\">" + (int) (cube.getX()) + "</text>\n";
            svg += "\t\t<text x=\"" + ((cube.getCoordinates()[4].getX() + cube.getCoordinates()[0].getX()) / 2) + "\" y=\"" + (cube.getCoordinates()[0].getY() + cube.getCoordinates()[4].getY()) / 2 + "\" font-family=\""+font.getFont_family()+"\" font-size=\""+font.getFont_size()+"\">" + (int) (cube.getZ()) + "</text>\n\n";

       if(cube.isKernel()){
           kernelString+=svg;
       }
       else {
           double z = calculateAverageZ(cube.getCoordinates());

           SortNode sn = new SortNode(svg, z);
           this.drawOrderList.add(sn);
       }
    }

    /**
     * Draw a pyramid
     * @param pyramid to draw
     */
    private void drawPyramid(Pyramid pyramid) {
        String svg="";
        svg += "\t\t<path opacity=\""+color.getConvOpacity()+"\" fill=\"" + color.getPyramidColor() + "\" d=\""+"M"+ pyramid.getCoordinates()[0].getX() +" "+ pyramid.getCoordinates()[0].getY()  +" L"+ pyramid.getCoordinates()[1].getX()  +" "+ pyramid.getCoordinates()[1].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[0].getX() +" "+pyramid.getCoordinates()[0].getY()+"\"/>" + "\n";
        svg += "\t\t<path opacity=\""+color.getConvOpacity()+"\" fill=\"" + color.getPyramidColor() + "\" d=\""+"M"+ pyramid.getCoordinates()[0].getX() +" "+ pyramid.getCoordinates()[0].getY()  +" L"+ pyramid.getCoordinates()[2].getX()  +" "+ pyramid.getCoordinates()[2].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[0].getX() +" "+pyramid.getCoordinates()[0].getY()+"\"/>" + "\n";
        svg += "\t\t<path opacity=\""+color.getConvOpacity()+"\" fill=\"" + color.getPyramidColor() + "\" d=\""+"M"+ pyramid.getCoordinates()[1].getX() +" "+ pyramid.getCoordinates()[1].getY()  +" L"+ pyramid.getCoordinates()[3].getX()  +" "+ pyramid.getCoordinates()[3].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[1].getX() +" "+pyramid.getCoordinates()[1].getY()+"\"/>" + "\n";
        svg += "\t\t<path opacity=\""+color.getConvOpacity()+"\" fill=\"" + color.getPyramidColor() + "\" d=\""+"M"+ pyramid.getCoordinates()[2].getX() +" "+ pyramid.getCoordinates()[2].getY()  +" L"+ pyramid.getCoordinates()[3].getX()  +" "+ pyramid.getCoordinates()[3].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[2].getX() +" "+pyramid.getCoordinates()[2].getY()+"\"/>" + "\n\n";
        double z=calculateAverageZ(pyramid.getCoordinates());
        SortNode sn=new SortNode(svg,z);
        this.drawOrderList.add(sn);
    }

    /**
     * Draw an arrow
     * @param arrow to draw
     */
    private void drawArrow(Arrow arrow) {
        String svg="";
        svg +="<!-- Arrow -->\n";
        svg += "\t\t<path opacity=\""+color.getArrowOpacity()+"\" stroke=\""+ color.getArrowColor() + "\" d=\""+"M"+ arrow.getVertex1().getX()+" "+ arrow.getVertex1().getY()  +" L"+ arrow.getVertex2().getX() +" "+ arrow.getVertex2().getY() +"\"/>" + "\n";
        svg += "\t\t<circle opacity=\""+color.getArrowOpacity()+"\" cx=\""+arrow.getVertex2().getX()+"\" cy=\""+arrow.getVertex2().getY()+"\" r=\"1\" fill=\""+color.getArrowColor()+"\" />\n";
        double z=calculateAverageZ(arrow.getCoordinates());
        SortNode sn=new SortNode(svg,z);
        this.drawOrderList.add(sn);
    }

    /**
     * Select the color of the cube.
     * @param cube the actual cube to draw
     * @return cube color.
     */

    private String selectColor(Cube cube) {
        if (cube.isKernel()) {
            return color.getKernelColor();
        }
        else if(!cube.isKernel() && !cube.isDenseLayer()){
            return color.getCubeColor();
        }
        else{
            return color.getDenseColor();
        }
    }

    /**
     * Select the opacity of the cube.
     * @param cube the actual cube to draw
     * @return cube opacity.
     */

    private double selectOpacity(Cube cube) {
        if (cube.isKernel()) {
            return color.getKernelOpacity();
        }
        else if(!cube.isKernel() && !cube.isDenseLayer()){
            return color.getLayerOpacity();
        }
        else{
            return color.getDenseOpacity();
        }
    }


    /**
     * Method that rotates the cube points
     */
    private void doTransformations(Coordinate[] coordinates) {

        MatrixController matrixController=new MatrixController();

        //Rotation on the Y-axis
        matrixController.rotate("y",coordinates,alfa.getAlfaY());

        //Rotation on the X-axis
        matrixController.rotate("x",coordinates,alfa.getAlfaX());
    }

    /**
     * Compute the center of a square
     * @param coordinates the coordinates to calculate its center
     * @return the center
     */
    private Coordinate calculateCenter(Coordinate [] coordinates){
        double x=(coordinates[0].getX()+coordinates[1].getX()+coordinates[2].getX()+coordinates[3].getX())/4;
        double y=(coordinates[0].getY()+coordinates[1].getY()+coordinates[2].getY()+coordinates[3].getY())/4;
        double z=(coordinates[0].getZ()+coordinates[1].getZ()+coordinates[2].getZ()+coordinates[3].getZ())/4;
        return new Coordinate(x,y,z);
    }

    /**
     * Compute a random point for the kernel
     * @param coordinates the coordinates to calculate random point
     * @return the random coordinate
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
        this.svgString="<svg width=\""+viewBox.getWidth()+"px\" height=\""+viewBox.getHeight()+"px\" viewBox=\""+viewBox.getX1()+" "+viewBox.getY1()+" "+viewBox.getX2()+" "+viewBox.getY2()+"\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                        "\t<g stroke=\""+stroke.getStroke_color()+"\" stroke-width=\""+stroke.getStroke_width()+"\">\n";
    }

    /**
     * SVG Footer
     */
    private void addFooter(){
        this.svgString+="\t </g>\n"+"</svg>";
    }

    /**
     * Shift the Neural Network
     * @param node the node to be moved
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
                length=l+ shift.getShiftLayers();
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
     * @param cube_actual the actual cube
     * @param kernel kernel cube
     */
    private void moveKernel(Cube cube_actual,Cube kernel){
        MatrixController matrixController=new MatrixController();
        double dif=(cube_actual.getCoordinates()[3].getY()-kernel.getCoordinates()[3].getY());

        double x_random=-dif + (Math.random() * ((dif +dif) + 1)); //[-x,x]
        double y_random=-dif + (Math.random() * ((dif +dif) + 1)); //[-y,y]

        matrixController.move("x",kernel.getCoordinates(),x_random);
        matrixController.move("y",kernel.getCoordinates(),y_random);
    }

    private double calculateAverageZ(Coordinate[] coordinates) {
        double total=coordinates.length;
        double sum=0;
        for(int i=0;i<total;i++){
            double coord=coordinates[i].getZ();
            sum+=coord;
        }
        return sum/total;
    }
}
