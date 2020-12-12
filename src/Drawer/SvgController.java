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

    DrawSettings drawSettings;

    //GLOBAL VARIABLES
    double length=0;
    double lengthAux=0;
    boolean activate=false;
    double length2=0;
    double length2Aux=0;

    //FINAL SVG FILE
    List<SortNode> drawOrderList=new ArrayList<>();
    String svgString="";
    String aux;
    double z;

    private class SortNode implements Comparable<SortNode>{
        private String svgString;
        private double z;
        DrawSettings drawSettings;

        public String getSvgString() {
            return svgString;
        }

        public double getZ() {
            return z;
        }

        public SortNode(String svgString, double z,DrawSettings drawSettings) {
            this.svgString = svgString;
            this.z = z;
            this.drawSettings=drawSettings;
        }

        // Compare method
        @Override
        public int compareTo(SortNode sn) {
            if(drawSettings.getAlfa().getAlfaX()<0){
                if(this.z<sn.getZ()){
                    return -1;
                }
                else if(this.z==sn.getZ()){
                    return 0;
                }
                else{
                    return 1;
                }

            }
            else if(drawSettings.getAlfa().getAlfaX()>0){
                if(this.z<sn.getZ()){
                    return 1;
                }
                else if(this.z==sn.getZ()){
                    return 0;
                }
                else{
                    return -1;
                }
            }
            else{
                if(this.z<sn.getZ()){
                    return 1;
                }
                else if(this.z==sn.getZ()){
                    return 0;
                }
                else{
                    return -1;
                }
            }

        }
    }

    public SvgController(DrawSettings settings) {
        this.drawSettings=settings;
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

        for(int i = 0;i<drawOrderList.size();i++){
            SortNode n=drawOrderList.get(i);
            svgString+=n.getSvgString();
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
                        length2+=l+ drawSettings.getShift().getShiftNodes();

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
                    length=l+drawSettings.getShift().getShiftParent();
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
                if(i==modelQueue.size()-1 || cube.isDenseLayer()){
                    drawSingleCube(cube);
                }
                else {
                    drawCube(cube);
                }
                if(activate){

                    Cube kernelCube=modelQueue.get(i-1);
                    Coordinate vertex=cube.getCoordinates()[8];

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
                        //continue
                    }
                }

                if(cube.isKernel()){
                    activate=true;
                }
            }
    }

    private void drawSingleCube(Cube cube) {
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

            if(drawSettings.isActivateLayerDimensions()){
                svg+=drawText(cube);
            }
            double z = calculateAverageZ(cube.getCoordinates());
            SortNode sn=new SortNode(svg,z,drawSettings);
            this.drawOrderList.add(sn);
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

       if(cube.isKernel()){
           if(drawSettings.isActivateKernelDimensions()){
               svg+=drawText(cube);
           }
           aux=svg+aux;
           SortNode sn = new SortNode(aux, z,drawSettings);
           this.drawOrderList.add(sn);
           aux="";
       }
       else {
           if(drawSettings.isActivateLayerDimensions()){
               svg+=drawText(cube);
           }
           aux=svg;
           z = calculateAverageZ(cube.getCoordinates());
       }
    }

    private String drawText(Cube cube){
        String svg="";
        svg += "\t\t<text x=\"" + ((cube.getCoordinates()[4].getX() + cube.getCoordinates()[6].getX()) / 2) + "\" y=\"" + (cube.getCoordinates()[4].getY() + cube.getCoordinates()[6].getY()) / 2  + "\" font-family=\""+drawSettings.getFont().getFont_family()+"\" font-size=\""+drawSettings.getFont().getFont_size()+"\">" + (int) (cube.getY()) + "</text>\n";
        svg += "\t\t<text x=\"" + ((cube.getCoordinates()[6].getX() + cube.getCoordinates()[7].getX()) / 2) + "\" y=\"" + (cube.getCoordinates()[6].getY() + cube.getCoordinates()[7].getY()) / 2 + "\" font-family=\""+drawSettings.getFont().getFont_family()+"\" font-size=\""+drawSettings.getFont().getFont_size()+"\">" + (int) (cube.getX()) + "</text>\n";
        svg += "\t\t<text x=\"" + ((cube.getCoordinates()[4].getX() + cube.getCoordinates()[0].getX()) / 2) + "\" y=\"" + (cube.getCoordinates()[0].getY() + cube.getCoordinates()[4].getY()) / 2 + "\" font-family=\""+drawSettings.getFont().getFont_family()+"\" font-size=\""+drawSettings.getFont().getFont_size()+"\">" + (int) (cube.getZ()) + "</text>\n\n";
        return svg;
    }

    /**
     * Draw a pyramid
     * @param pyramid to draw
     */
    private void drawPyramid(Pyramid pyramid) {
        String svg="";
        svg += "\t\t<path opacity=\""+drawSettings.getColor().getConvOpacity()+"\" fill=\"" + drawSettings.getColor().getPyramidColor() + "\" d=\""+"M"+ pyramid.getCoordinates()[0].getX() +" "+ pyramid.getCoordinates()[0].getY()  +" L"+ pyramid.getCoordinates()[1].getX()  +" "+ pyramid.getCoordinates()[1].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[0].getX() +" "+pyramid.getCoordinates()[0].getY()+"\"/>" + "\n";
        svg += "\t\t<path opacity=\""+drawSettings.getColor().getConvOpacity()+"\" fill=\"" + drawSettings.getColor().getPyramidColor() + "\" d=\""+"M"+ pyramid.getCoordinates()[0].getX() +" "+ pyramid.getCoordinates()[0].getY()  +" L"+ pyramid.getCoordinates()[2].getX()  +" "+ pyramid.getCoordinates()[2].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[0].getX() +" "+pyramid.getCoordinates()[0].getY()+"\"/>" + "\n";
        svg += "\t\t<path opacity=\""+drawSettings.getColor().getConvOpacity()+"\" fill=\"" + drawSettings.getColor().getPyramidColor() + "\" d=\""+"M"+ pyramid.getCoordinates()[1].getX() +" "+ pyramid.getCoordinates()[1].getY()  +" L"+ pyramid.getCoordinates()[3].getX()  +" "+ pyramid.getCoordinates()[3].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[1].getX() +" "+pyramid.getCoordinates()[1].getY()+"\"/>" + "\n";
        svg += "\t\t<path opacity=\""+drawSettings.getColor().getConvOpacity()+"\" fill=\"" + drawSettings.getColor().getPyramidColor() + "\" d=\""+"M"+ pyramid.getCoordinates()[2].getX() +" "+ pyramid.getCoordinates()[2].getY()  +" L"+ pyramid.getCoordinates()[3].getX()  +" "+ pyramid.getCoordinates()[3].getY()  +" L"+ pyramid.getVertex().getX() +" "+ pyramid.getVertex().getY()+" L"+ pyramid.getCoordinates()[2].getX() +" "+pyramid.getCoordinates()[2].getY()+"\"/>" + "\n\n";
        double z=calculateAverageZ(pyramid.getCoordinates());
        SortNode sn=new SortNode(svg,z,drawSettings);
        this.drawOrderList.add(sn);
    }

    /**
     * Draw an arrow
     * @param arrow to draw
     */
    private void drawArrow(Arrow arrow) {
        String svg="";
        svg +="<!-- Arrow -->\n";
        svg += "\t\t<path opacity=\""+drawSettings.getColor().getArrowOpacity()+"\" stroke=\""+ drawSettings.getColor().getArrowColor() + "\" d=\""+"M"+ arrow.getVertex1().getX()+" "+ arrow.getVertex1().getY()  +" L"+ arrow.getVertex2().getX() +" "+ arrow.getVertex2().getY() +"\"/>" + "\n";
        svg += "\t\t<circle opacity=\""+drawSettings.getColor().getArrowOpacity()+"\" cx=\""+arrow.getVertex2().getX()+"\" cy=\""+arrow.getVertex2().getY()+"\" r=\"1\" fill=\""+drawSettings.getColor().getArrowColor()+"\" />\n";
        double z=calculateAverageZ(arrow.getCoordinates());
        SortNode sn=new SortNode(svg,z,drawSettings);
        this.drawOrderList.add(sn);
    }

    /**
     * Select the color of the cube.
     * @param cube the actual cube to draw
     * @return cube color.
     */

    private String selectColor(Cube cube) {
        if (cube.isKernel()) {
            return drawSettings.getColor().getKernelColor();
        }
        else if(!cube.isKernel() && !cube.isDenseLayer()){
            return drawSettings.getColor().getCubeColor();
        }
        else{
            return drawSettings.getColor().getDenseColor();
        }
    }

    /**
     * Select the opacity of the cube.
     * @param cube the actual cube to draw
     * @return cube opacity.
     */

    private double selectOpacity(Cube cube) {
        if (cube.isKernel()) {
            return drawSettings.getColor().getKernelOpacity();
        }
        else if(!cube.isKernel() && !cube.isDenseLayer()){
            return drawSettings.getColor().getLayerOpacity();
        }
        else{
            return drawSettings.getColor().getDenseOpacity();
        }
    }


    /**
     * Method that rotates the cube points
     */
    private void doTransformations(Coordinate[] coordinates) {

        MatrixController matrixController=new MatrixController();

        //Rotation on the Y-axis
        matrixController.rotate("y",coordinates,drawSettings.getAlfa().getAlfaY());

        //Rotation on the X-axis
        matrixController.rotate("x",coordinates,drawSettings.getAlfa().getAlfaX());

        //Rotation on the Z-axis
        matrixController.rotate("z",coordinates,drawSettings.getAlfa().getAlfaZ());

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
     * SVG header
     */
    private void addHeader(){
        this.svgString="<svg width=\""+drawSettings.getViewBox().getWidth()+"px\" height=\""+drawSettings.getViewBox().getHeight()+"px\" viewBox=\""+drawSettings.getViewBox().getX1()+" "+drawSettings.getViewBox().getY1()+" "+drawSettings.getViewBox().getX2()+" "+drawSettings.getViewBox().getY2()+"\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "\t<g stroke=\""+drawSettings.getStroke().getStroke_color()+"\" stroke-width=\""+drawSettings.getStroke().getStroke_width()+"\">\n";
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
                length=l+ drawSettings.getShift().getShiftLayers();
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
        double difY=Math.abs((cube_actual.getCoordinates()[3].getY()-kernel.getCoordinates()[3].getY()));
        double difX=Math.abs((cube_actual.getCoordinates()[3].getX()-kernel.getCoordinates()[3].getX()));

        double x_random=-difX + (Math.random() * ((difX +difX))); //[-x,x]
        double y_random=-difY + (Math.random() * ((difY +difY))); //[-y,y]

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
