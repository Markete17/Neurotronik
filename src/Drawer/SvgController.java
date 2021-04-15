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

    private final DrawSettings drawSettings;
    private final MatrixController matrixController;

    //GLOBAL VARIABLES
    private double depth = 0;
    private double depthAux = 0;
    private double length = 0;
    private double lengthAux = 0;

    private boolean activate = false;
    private String aux;
    private double z;

    private Coordinate imageCenter;
    private double x_max = -Double.MAX_VALUE;
    private double y_max = -Double.MAX_VALUE;
    private double z_max = -Double.MAX_VALUE;
    private double x_min = Double.MAX_VALUE;
    private double y_min = Double.MAX_VALUE;
    private double z_min = Double.MAX_VALUE;

    //FINAL SVG FILE
    private final List<SortNode> drawOrderList = new ArrayList<>();
    private String svgString = "";

    private static class SortNode implements Comparable<SortNode> {
        private final String svgString;
        private final double z;

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
            return Double.compare(sn.getZ(), this.z);
        }
    }

    public SvgController(DrawSettings settings) {
        this.drawSettings = settings;
        this.matrixController = new MatrixController(this.drawSettings.getAlfa().getAlfaX(), this.drawSettings.getAlfa().getAlfaY(), this.drawSettings.getAlfa().getAlfaZ());
    }

    /**
     * Draws the Neural Network in a SVG file
     *
     * @param modelTree tree of neural network
     * @return svg file string
     */
    public String draw(NeuralNetworkTree modelTree) {
        shiftTree(modelTree);
        calculateImageCenter();
        for (int i = 0; i < modelTree.getNodes().length; i++) {
            for (int j = 0; j < modelTree.getNodes()[i].size(); j++) {
                Node node = modelTree.getNodes()[i].get(j);
                drawNode(node);
            }
        }
        drawUnions(modelTree);
        drawJumps(modelTree.getJumps());
        Collections.sort(drawOrderList);
        this.addHeader();
        for (SortNode n : drawOrderList) {
            svgString += n.getSvgString();
        }
        this.addFooter();
        return svgString;
    }

    /**
     * Shift all the tree
     *
     * @param modelTree neural network tree
     */
    private void shiftTree(NeuralNetworkTree modelTree) {
        modelTree.initializeNodes();
        for (int i = 0; i < modelTree.getNodes().length; i++) {
            //First Level
            if (i == 0) {
                for (int j = 0; j < modelTree.getNodes()[i].size(); j++) {
                    Node node = modelTree.getNodes()[i].get(j);
                    if (j != 0) {
                        double l;
                        if (node.getCubeList().get(0).getX() > modelTree.getNodes()[i].get(j - 1).getCubeList().get(0).getX()) {

                            l = Math.abs(node.getCubeList().get(0).getCoordinates()[0].getX() - lengthAux);
                        } else {
                            l = Math.abs(lengthAux - node.getCubeList().get(0).getCoordinates()[0].getX());
                        }
                        length += l + drawSettings.getShift().getShiftNodes();

                    }
                    lengthAux = node.getCubeList().get(0).getCoordinates()[1].getX();
                    this.shiftNode(node);
                    depth = 0;
                    depthAux = 0;
                }
            }
            //Other Levels
            else {
                for (int j = 0; j < modelTree.getNodes()[i].size(); j++) {
                    Node node = modelTree.getNodes()[i].get(j);
                    Node firstChild = modelTree.findFirstChild(node.getChildren());
                    Node lastChild = modelTree.findLastChild(node.getChildren());
                    Coordinate centerChild1 = calculateCenter(firstChild.getCubeList().get(0).getCoordinates());
                    Coordinate centerChild2 = calculateCenter(lastChild.getCubeList().get(0).getCoordinates());
                    length = (Math.abs(centerChild1.getX() + centerChild2.getX()) / 2);
                    double depth = modelTree.greaterDepthChild(node.getChildren());
                    double depthCube = node.getCubeList().get(0).getCoordinates()[1].getZ();
                    double l = Math.abs(depth + depthCube);
                    this.depth = l + drawSettings.getShift().getShiftParent();
                    this.shiftNode(node);
                }
                depth = 0;
                depthAux = 0;
            }
            length = 0;
        }
    }

    /**
     * Draw a node with SVG
     *
     * @param node the node of the tree to be drawn.
     */
    private void drawNode(Node node) {
        List<Cube> modelQueue = node.getCubeList();
        for (int i = 0; i < modelQueue.size(); i++) {
            Cube cube = modelQueue.get(i);
            doTransformations(cube.getCoordinates());
            if (activate) {

                Cube kernelCube = modelQueue.get(i - 1);
                Coordinate vertex = cube.getCoordinates()[8];

                Pyramid pyramid = new Pyramid(Arrays.copyOf(kernelCube.getCoordinates(), 4), new Coordinate(vertex.getX(), vertex.getY(), vertex.getZ()));
                drawPyramid(pyramid,kernelCube);
                activate = false;
            }
            //The cube has not kernel (last cube or dense layers)
            if (i != modelQueue.size() - 1) {
                if (modelQueue.get(i + 1).isDenseLayer()) {
                    drawSingleCube(cube);
                }
            }
            if (i == modelQueue.size() - 1 || cube.isDenseLayer()) {
                drawSingleCube(cube);
            }
            //The cube has kernel
            else {
                drawCube(cube);
            }

            if (cube.isDenseLayer()) {
                try {
                    Cube lastCube = modelQueue.get(i - 1);
                    lineTo(lastCube, cube);
                } catch (Exception e) {
                    //continue
                }
            }

            if (cube.isKernel()) {
                activate = true;
            }
            updateMaxMin(cube.getCoordinates());
        }
    }

    private void drawSingleCube(Cube cube) {
        //Cube Path
        String color = selectColor(cube);
        double opacity = selectOpacity(cube);
        String svg = "";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + "\"/>" + "\n";

        if (drawSettings.isActivateLayerDimensions()) {
            svg += drawText(cube);
        }
        double z = calculateAverageZ(cube.getCoordinates());
        SortNode sn = new SortNode(svg, z);
        this.drawOrderList.add(sn);
    }

    /**
     * Draw the junction between the parent and child nodes.
     *
     * @param modelTree neural network tree
     */

    private void drawUnions(NeuralNetworkTree modelTree) {
        for (int i = 0; i < modelTree.getNodes().length; i++) {
            for (int j = 0; j < modelTree.getNodes()[i].size(); j++) {
                Node parent = modelTree.getNodes()[i].get(j);
                for (Node child : parent.getChildren()) {
                    Cube lastCube = child.getLastCube();
                    lineTo(lastCube, parent.getCubeList().get(0));
                }
            }
        }
    }

    /**
     * Draw a cube
     *
     * @param cube to draw
     */
    private void drawCube(Cube cube) {
        //Cube Path
        String color = selectColor(cube);
        double opacity = selectOpacity(cube);
        String svg = "";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + opacity + "\" fill=\"" + color + "\" d=\"" + "M" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + "\"/>" + "\n";

        if (cube.isKernel()) {
            aux = svg + aux;
            SortNode sn = new SortNode(aux, z);
            this.drawOrderList.add(sn);
        } else {
            if (drawSettings.isActivateLayerDimensions()) {
                svg += drawText(cube);
            }
            aux = svg;
            z = calculateAverageZ(cube.getCoordinates());
        }
    }

    private String drawText(Cube cube) {
        String svg = "";
        svg += "\t\t<text style=\"fill:" + drawSettings.getFont().getFont_color() + ";font-family:" + drawSettings.getFont().getFont_family() + ";font-size:" + drawSettings.getFont().getFont_size() + "\" " + "x=\"" + ((cube.getCoordinates()[4].getX() + cube.getCoordinates()[6].getX()) / 2) + "\" y=\"" + (cube.getCoordinates()[4].getY() + cube.getCoordinates()[6].getY()) / 2 + "\" " + ">" + (int) (cube.getY()) + "</text>\n";
        svg += "\t\t<text style=\"fill:" + drawSettings.getFont().getFont_color() + ";font-family:" + drawSettings.getFont().getFont_family() + ";font-size:" + drawSettings.getFont().getFont_size() + "\" " + "x=\"" + ((cube.getCoordinates()[6].getX() + cube.getCoordinates()[7].getX()) / 2) + "\" y=\"" + (cube.getCoordinates()[6].getY() + cube.getCoordinates()[7].getY()) / 2 + "\" " + ">" + (int) (cube.getX()) + "</text>\n";
        svg += "\t\t<text style=\"fill:" + drawSettings.getFont().getFont_color() + ";font-family:" + drawSettings.getFont().getFont_family() + ";font-size:" + drawSettings.getFont().getFont_size() + "\" " + "x=\"" + ((cube.getCoordinates()[4].getX() + cube.getCoordinates()[0].getX()) / 2) + "\" y=\"" + (cube.getCoordinates()[0].getY() + cube.getCoordinates()[4].getY()) / 2 + "\" " + ">" + (int) (cube.getZ()) + "</text>\n\n";
        return svg;
    }

    private String drawText(Pyramid pyramid,Cube kernel) {
        return "\t\t<text style=\"fill:" + drawSettings.getFont().getFont_color() + ";font-family:" + drawSettings.getFont().getFont_family() + ";font-size:" + drawSettings.getFont().getFont_size() + "\" " + "x=\"" + ((pyramid.getCoordinates()[0].getX()+pyramid.getCoordinates()[1].getX()+ pyramid.getVertex().getX()) / 3) + "\" y=\"" + (pyramid.getCoordinates()[0].getY() + (pyramid.getVertex().getY()-9)) / 2 + "\" " + ">" + "["+(int) (kernel.getX())+","+(int)(kernel.getY())+"]" + "</text>\n";
    }

    /**
     * Draw a pyramid
     *
     * @param pyramid to draw
     */
    private void drawPyramid(Pyramid pyramid,Cube kernel) {
        String svg = "";
        svg += "\t\t<path opacity=\"" + drawSettings.getColor().getConvOpacity() + "\" fill=\"" + drawSettings.getColor().getPyramidColor() + "\" d=\"" + "M" + pyramid.getCoordinates()[0].getX() + " " + pyramid.getCoordinates()[0].getY() + " L" + pyramid.getCoordinates()[1].getX() + " " + pyramid.getCoordinates()[1].getY() + " L" + pyramid.getVertex().getX() + " " + pyramid.getVertex().getY() + " L" + pyramid.getCoordinates()[0].getX() + " " + pyramid.getCoordinates()[0].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + drawSettings.getColor().getConvOpacity() + "\" fill=\"" + drawSettings.getColor().getPyramidColor() + "\" d=\"" + "M" + pyramid.getCoordinates()[0].getX() + " " + pyramid.getCoordinates()[0].getY() + " L" + pyramid.getCoordinates()[2].getX() + " " + pyramid.getCoordinates()[2].getY() + " L" + pyramid.getVertex().getX() + " " + pyramid.getVertex().getY() + " L" + pyramid.getCoordinates()[0].getX() + " " + pyramid.getCoordinates()[0].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + drawSettings.getColor().getConvOpacity() + "\" fill=\"" + drawSettings.getColor().getPyramidColor() + "\" d=\"" + "M" + pyramid.getCoordinates()[1].getX() + " " + pyramid.getCoordinates()[1].getY() + " L" + pyramid.getCoordinates()[3].getX() + " " + pyramid.getCoordinates()[3].getY() + " L" + pyramid.getVertex().getX() + " " + pyramid.getVertex().getY() + " L" + pyramid.getCoordinates()[1].getX() + " " + pyramid.getCoordinates()[1].getY() + "\"/>" + "\n";
        svg += "\t\t<path opacity=\"" + drawSettings.getColor().getConvOpacity() + "\" fill=\"" + drawSettings.getColor().getPyramidColor() + "\" d=\"" + "M" + pyramid.getCoordinates()[2].getX() + " " + pyramid.getCoordinates()[2].getY() + " L" + pyramid.getCoordinates()[3].getX() + " " + pyramid.getCoordinates()[3].getY() + " L" + pyramid.getVertex().getX() + " " + pyramid.getVertex().getY() + " L" + pyramid.getCoordinates()[2].getX() + " " + pyramid.getCoordinates()[2].getY() + "\"/>" + "\n\n";
        if (drawSettings.isActivateKernelDimensions()) {
            svg += drawText(pyramid,kernel);
        }
        double z = calculateAverageZ(pyramid.getCoordinates());
        SortNode sn = new SortNode(svg, z);
        this.drawOrderList.add(sn);
    }

    /**
     * Draw an arrow
     *
     * @param arrow to draw
     */
    private void drawArrow(Arrow arrow) {
        String svg = "";
        svg += "<!-- Arrow -->\n";
        svg += "\t\t<path opacity=\"" + drawSettings.getColor().getArrowOpacity() + "\" stroke=\"" + drawSettings.getColor().getArrowColor() + "\" d=\"" + "M" + arrow.getVertex1().getX() + " " + arrow.getVertex1().getY() + " L" + arrow.getVertex2().getX() + " " + arrow.getVertex2().getY() + "\"/>" + "\n";
        svg += "\t\t<circle opacity=\"" + drawSettings.getColor().getArrowOpacity() + "\" cx=\"" + arrow.getVertex2().getX() + "\" cy=\"" + arrow.getVertex2().getY() + "\" r=\"1\" fill=\"" + drawSettings.getColor().getArrowColor() + "\" />\n";
        double z = calculateAverageZ(arrow.getCoordinates());
        SortNode sn = new SortNode(svg, z);
        this.drawOrderList.add(sn);
    }

    /**
     * Draw a node jump
     *
     * @param jumps List of nodes
     */
    private void drawJumps(List<List<Node>> jumps) {
        for (List<Node> jump : jumps) {
            Cube lastCube = jump.get(0).getLastCube();
            Cube firstCube = jump.get(1).getCubeList().get(0);
            lineTo(lastCube, firstCube);
        }
    }

    /**
     * Select the color of the cube.
     *
     * @param cube the actual cube to draw
     * @return cube color.
     */

    private String selectColor(Cube cube) {
        if (cube.isKernel()) {
            return this.drawSettings.getColor().getKernelColor();
        }
        else if (cube.isDenseLayer()){
            return this.drawSettings.getColor().getDenseColor();
        }
        else if (cube.isInputLayer()){
            return this.drawSettings.getColor().getInputColor();
        }
        else {
            return this.drawSettings.getColor().getCubeColor();
        }
    }

    /**
     * Select the opacity of the cube.
     *
     * @param cube the actual cube to draw
     * @return cube opacity.
     */

    private double selectOpacity(Cube cube) {
        if (cube.isKernel()) {
            return this.drawSettings.getColor().getKernelOpacity();
        }
        else if (cube.isDenseLayer()){
            return this.drawSettings.getColor().getDenseOpacity();
        }
        else if (cube.isInputLayer()){
            return this.drawSettings.getColor().getInputOpacity();
        }
        else {
            return this.drawSettings.getColor().getLayerOpacity();
        }
    }


    /**
     * Method that rotates the cube points
     */
    private void doTransformations(Coordinate[] coordinates) {

        //move the image in the center
        this.matrixController.move("x", coordinates, -imageCenter.getX());
        this.matrixController.move("y", coordinates, -imageCenter.getY());
        this.matrixController.move("z", coordinates, -imageCenter.getZ());

        //Rotation on the X-axis AND Y-axis
        this.matrixController.rotate(coordinates);

        //return to the original position
        this.matrixController.move("x", coordinates, imageCenter.getX());
        this.matrixController.move("y", coordinates, imageCenter.getY());
        this.matrixController.move("z", coordinates, imageCenter.getZ());

    }

    /**
     * Compute the center of a square
     *
     * @param coordinates the coordinates to calculate its center
     * @return the center
     */
    private Coordinate calculateCenter(Coordinate[] coordinates) {
        double x = (coordinates[0].getX() + coordinates[1].getX() + coordinates[2].getX() + coordinates[3].getX()) / 4;
        double y = (coordinates[0].getY() + coordinates[1].getY() + coordinates[2].getY() + coordinates[3].getY()) / 4;
        double z = (coordinates[0].getZ() + coordinates[1].getZ() + coordinates[2].getZ() + coordinates[3].getZ()) / 4;
        return new Coordinate(x, y, z);
    }

    /**
     * SVG header
     */
    private void addHeader() {
        this.svgString = "<svg width=\"100%\"" +" height=\"100%\"" + " viewBox=\"" + (this.x_min) + " " + (this.y_min - this.drawSettings.getFont().getFont_size()) + " " + (x_max - x_min + this.drawSettings.getFont().getFont_size() + drawSettings.getViewBox().getZoom()) + " " + (y_max - y_min + this.drawSettings.getFont().getFont_size() + drawSettings.getViewBox().getZoom()) + "\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "\t<g stroke=\"" + drawSettings.getStroke().getStroke_color() + "\" stroke-width=\"" + drawSettings.getStroke().getStroke_width() + "\">\n\n";
    }

    /**
     * SVG Footer
     */
    private void addFooter() {
        this.svgString += "\t </g>\n" + "</svg>";
    }

    /**
     * Shift the Neural Network
     *
     * @param node the node to be moved
     */
    private void shiftNode(Node node) {
        List<Cube> modelQueue = node.getCubeList();
        for (int i = 0; i < modelQueue.size(); i++) {
            Cube cube = modelQueue.get(i);

            matrixController.move("x", cube.getCoordinates(), length);

            //Difference in length between the Z of the previous cube (it is X because it is rotated) and the Z of the current cube

            if (!cube.isKernel() && i != 0) {
                double l = Math.abs(depthAux - cube.getCoordinates()[4].getZ());
                depth = l + drawSettings.getShift().getShiftLayers();
            }
            matrixController.move("z", cube.getCoordinates(), depth);

            if (!cube.isKernel()) {
                depthAux = cube.getCoordinates()[0].getZ();
            }
            if (cube.isKernel()) {
                Cube cube_actual = modelQueue.get(i - 1);
                moveKernel(cube_actual, cube);
            }
            updateMaxMin(cube.getCoordinates());
        }
    }

    private void updateMaxMin(Coordinate[] coordinates) {
        for (Coordinate coordinate : coordinates) {
            if (coordinate.getX() > x_max) {
                x_max = coordinate.getX();
            }
            if (coordinate.getX() < x_min) {
                x_min = coordinate.getX();
            }
            if (coordinate.getY() > y_max) {
                y_max = coordinate.getY();
            }
            if (coordinate.getY() < y_min) {
                y_min = coordinate.getY();
            }
            if (coordinate.getZ() > z_max) {
                z_max = coordinate.getZ();
            }
            if (coordinate.getZ() < z_min) {
                z_min = coordinate.getZ();
            }
        }
    }

    /**
     * Moves the kernel to a random position
     *
     * @param cube_actual the actual cube
     * @param kernel      kernel cube
     */
    private void moveKernel(Cube cube_actual, Cube kernel) {
        double difY = Math.abs((cube_actual.getCoordinates()[3].getY() - kernel.getCoordinates()[3].getY()));
        double difX = Math.abs((cube_actual.getCoordinates()[3].getX() - kernel.getCoordinates()[3].getX()));

        double x_random = -difX + (Math.random() * ((difX + difX))); //[-x,x]
        double y_random = -difY + (Math.random() * ((difY + difY))); //[-y,y]

        matrixController.move("x", kernel.getCoordinates(), x_random);
        matrixController.move("y", kernel.getCoordinates(), y_random);
    }

    /**
     * Calculates the z-mean over a list of coordinates
     *
     * @param coordinates image coordinates
     * @return average Z
     */
    private double calculateAverageZ(Coordinate[] coordinates) {
        double total = coordinates.length;
        double sum = 0;
        for (Coordinate coordinate : coordinates) {
            double coord = coordinate.getZ();
            sum += coord;
        }
        return sum / total;
    }

    /**
     * Calculates the coordinates of the center of the image
     */
    private void calculateImageCenter() {
        double center_x = ((x_max - x_min) / 2) + x_min;
        double center_y = ((y_max - y_min) / 2) + y_min;
        double center_z = ((z_max - z_min) / 2) + z_min;

        imageCenter = new Coordinate(center_x, center_y, center_z);
        x_max = -Double.MAX_VALUE;
        y_max = -Double.MAX_VALUE;
        x_min = Double.MAX_VALUE;
        y_min = Double.MAX_VALUE;
    }

    private void lineTo(Cube cube1, Cube cube2) {
        Coordinate vertex1 = calculateCenter(Arrays.copyOfRange(cube1.getCoordinates(), 0, 4));
        Coordinate vertex2 = calculateCenter(Arrays.copyOfRange(cube2.getCoordinates(), 4, 8));
        drawArrow(new Arrow(vertex1, vertex2));
    }
}