package drawing;

import data.Coordinate;
import exceptions.MatrixException;
import exceptions.TreeException;
import matrices.*;
import shapes.Arrow;
import shapes.Cube;
import shapes.Pyramid;
import tree.NeuralNetworkTree;
import tree.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SvgController {

    private static final String PATH ="\" d=\"";
    private static final String PATH_OPACITY ="\t\t<path opacity=\"";
    private static final String STROKE_LINECAP_SQUARE ="\" stroke-linecap=\"square\"/>";
    private static final String SVG_FILL ="\" fill=\"";
    private static final String SVG_TEXT ="\t\t<text style=\"fill:";
    private static final String CSS_FONT_SIZE =";font-size:";
    private static final String SVG_END_TEXT ="</text>\n";
    private static final String CSS_FONT_FAMILY =";font-family:";
    private static final String SVG_COORDINATE ="\" y=\"";

    private final DrawSettings drawSettings;
    private final MatrixController matrixController;

    //GLOBAL VARIABLES
    private double depth = 0;
    private double depthAux = 0;
    private double length = 0;
    private double lengthAux = 0;

    private boolean activate = false;
    private String aux;
    private double zAuxDepth;

    private Coordinate imageCenter;
    private double xMax = -Double.MAX_VALUE;
    private double yMax = -Double.MAX_VALUE;
    private double zMax = -Double.MAX_VALUE;
    private double xMin = Double.MAX_VALUE;
    private double yMin = Double.MAX_VALUE;
    private double zMin = Double.MAX_VALUE;

    //FINAL SVG FILE
    private final List<SortNode> drawOrderList = new ArrayList<>();
    private final StringBuilder svgString = new StringBuilder();

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

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof SortNode){
                SortNode sortNode = (SortNode) obj;
                return this.getSvgString().equals(sortNode.getSvgString()) && (this.getZ() == sortNode.getZ());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.hashCode();
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
    public String draw(NeuralNetworkTree modelTree) throws TreeException, MatrixException {
        moveTree(modelTree);
        calculateImageCenter();
        for (int i = 0; i < modelTree.getNodes().length; i++) {
            for (int j = 0; j < modelTree.getNodes()[i].size(); j++) {
                Node node = modelTree.getNodes()[i].get(j);
                drawNode(node);
            }
        }
        drawUnions(modelTree);
        drawJumps(modelTree.getJumps());
        drawShortcuts(modelTree.getShortcuts());
        Collections.sort(drawOrderList);
        this.addHeader();
        for (SortNode n : drawOrderList) {
            svgString.append(n.getSvgString());
        }
        this.addFooter();
        return svgString.toString();
    }

    /**
     * Move all the tree
     *
     * @param modelTree neural network tree
     */
    private void moveTree(NeuralNetworkTree modelTree) throws TreeException, MatrixException {
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
                        length += l + drawSettings.getDisplacement().getNodesDisplacement();

                    }
                    lengthAux = node.getCubeList().get(0).getCoordinates()[1].getX();
                    this.moveNode(node);
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
                    double greaterDepthChild = modelTree.greaterDepthChild(node.getChildren());
                    double depthCube = node.getCubeList().get(0).getCoordinates()[1].getZ();
                    double l = Math.abs(greaterDepthChild + depthCube);
                    this.depth = l + drawSettings.getDisplacement().getParentDisplacement();
                    this.moveNode(node);
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
    private void drawNode(Node node) throws MatrixException {
        List<Cube> modelQueue = node.getCubeList();
        for (int i = 0; i < modelQueue.size(); i++) {
            Cube cube = modelQueue.get(i);
            doTransformations(cube.getCoordinates());
            if (activate) {

                Cube kernelCube = modelQueue.get(i - 1);
                Coordinate vertex = cube.getCoordinates()[8];

                Pyramid pyramid = new Pyramid(Arrays.copyOf(kernelCube.getCoordinates(), 4), new Coordinate(vertex.getX(), vertex.getY(), vertex.getZ()));
                drawPyramid(pyramid, kernelCube);
                activate = false;
            }
            //The cube has not kernel (last cube or dense layers)
            if ( (i != modelQueue.size() - 1 && modelQueue.get(i + 1).isDenseLayer()) || (i == modelQueue.size() - 1 || cube.isDenseLayer())) {
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
     * Draws a cube
     *
     * @param cube to draw
     */
    private void drawCube(Cube cube) {
        String svg = drawSvgCube(cube);

        if (cube.isKernel()) {
            aux = svg + aux;
            SortNode sn = new SortNode(aux, zAuxDepth);
            this.drawOrderList.add(sn);
        } else {
            if (drawSettings.isActivateLayerDimensions()) {
                svg += drawText(cube);
            }
            aux = svg;
            zAuxDepth = calculateAverageZ(cube.getCoordinates());
        }
    }

    /**
     * Draw single cube without kernel cube.
     *
     * @param cube to draw
     */
    private void drawSingleCube(Cube cube) {
        String svg = drawSvgCube(cube);
        if (drawSettings.isActivateLayerDimensions()) {
            svg += drawText(cube);
        }
        double z = calculateAverageZ(cube.getCoordinates());
        SortNode sn = new SortNode(svg, z);
        this.drawOrderList.add(sn);
    }

    private String drawSvgCube(Cube cube) {
        //Cube Path
        String color = selectColor(cube);
        double opacity = selectOpacity(cube);
        String svg = "";
        svg += PATH_OPACITY + opacity + SVG_FILL + color + PATH + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + STROKE_LINECAP_SQUARE + "\n";
        svg += PATH_OPACITY + opacity + SVG_FILL + color + PATH + "M" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + STROKE_LINECAP_SQUARE + "\n";
        svg += PATH_OPACITY + opacity + SVG_FILL + color + PATH + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + STROKE_LINECAP_SQUARE + "\n";
        svg += PATH_OPACITY + opacity + SVG_FILL + color + PATH + "M" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + STROKE_LINECAP_SQUARE + "\n";
        svg += PATH_OPACITY + opacity + SVG_FILL + color + PATH + "M" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + " L" + cube.getCoordinates()[1].getX() + " " + cube.getCoordinates()[1].getY() + " L" + cube.getCoordinates()[5].getX() + " " + cube.getCoordinates()[5].getY() + " L" + cube.getCoordinates()[4].getX() + " " + cube.getCoordinates()[4].getY() + " L" + cube.getCoordinates()[0].getX() + " " + cube.getCoordinates()[0].getY() + STROKE_LINECAP_SQUARE + "\n";
        svg += PATH_OPACITY + opacity + SVG_FILL + color + PATH + "M" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + " L" + cube.getCoordinates()[3].getX() + " " + cube.getCoordinates()[3].getY() + " L" + cube.getCoordinates()[7].getX() + " " + cube.getCoordinates()[7].getY() + " L" + cube.getCoordinates()[6].getX() + " " + cube.getCoordinates()[6].getY() + " L" + cube.getCoordinates()[2].getX() + " " + cube.getCoordinates()[2].getY() + STROKE_LINECAP_SQUARE + "\n";
        return svg;
    }

    /**
     * Enters the text of the dimensions of the cubes in the svg image.
     *
     * @param cube the cube that has the dimensions to be displayed.
     * @return svg string
     */
    private String drawText(Cube cube) {
        String svg = "";
        svg += SVG_TEXT + drawSettings.getFont().getFontColor() + CSS_FONT_FAMILY + drawSettings.getFont().getFontFamily() + CSS_FONT_SIZE + drawSettings.getFont().getFontSize() + "\" " + "x=\"" + ((cube.getCoordinates()[4].getX() + cube.getCoordinates()[6].getX()) / 2) + SVG_COORDINATE + (cube.getCoordinates()[4].getY() + cube.getCoordinates()[6].getY()) / 2 + "\" " + ">" + (int) (cube.getY()) + SVG_END_TEXT;
        svg += SVG_TEXT + drawSettings.getFont().getFontColor() + CSS_FONT_FAMILY + drawSettings.getFont().getFontFamily() + CSS_FONT_SIZE + drawSettings.getFont().getFontSize() + "\" " + "x=\"" + ((cube.getCoordinates()[6].getX() + cube.getCoordinates()[7].getX()) / 2) + SVG_COORDINATE + (cube.getCoordinates()[6].getY() + cube.getCoordinates()[7].getY()) / 2 + "\" " + ">" + (int) (cube.getX()) + SVG_END_TEXT;
        svg += SVG_TEXT + drawSettings.getFont().getFontColor() + CSS_FONT_FAMILY + drawSettings.getFont().getFontFamily() + CSS_FONT_SIZE + drawSettings.getFont().getFontSize() + "\" " + "x=\"" + ((cube.getCoordinates()[4].getX() + cube.getCoordinates()[0].getX()) / 2) + SVG_COORDINATE + (cube.getCoordinates()[0].getY() + cube.getCoordinates()[4].getY()) / 2 + "\" " + ">" + (int) (cube.getZ()) + "</text>\n\n";
        return svg;
    }

    /**
     * Enters the text of the dimensions of the kernels in the svg image.
     *
     * @param pyramid convolution
     * @param kernel  the kernel that has the dimensions to be displayed.
     * @return svg string
     */
    private String drawText(Pyramid pyramid, Cube kernel) {
        return SVG_TEXT + drawSettings.getFont().getFontColor() + CSS_FONT_FAMILY + drawSettings.getFont().getFontFamily() + CSS_FONT_SIZE + drawSettings.getFont().getFontSize() + "\" " + "x=\"" + ((pyramid.getCoordinates()[0].getX() + pyramid.getCoordinates()[1].getX() + pyramid.getVertex().getX()) / 3) + SVG_COORDINATE + (pyramid.getCoordinates()[0].getY() + (pyramid.getVertex().getY() - 9)) / 2 + "\" " + ">" + "[" + (int) (kernel.getX()) + "," + (int) (kernel.getY()) + "]" + SVG_END_TEXT;
    }

    /**
     * Draw a pyramid
     *
     * @param pyramid to draw
     */
    private void drawPyramid(Pyramid pyramid, Cube kernel) {
        String svg = "";
        svg += PATH_OPACITY + drawSettings.getColor().getConvOpacity() + SVG_FILL + drawSettings.getColor().getPyramidColor() + PATH + "M" + pyramid.getCoordinates()[0].getX() + " " + pyramid.getCoordinates()[0].getY() + " L" + pyramid.getCoordinates()[1].getX() + " " + pyramid.getCoordinates()[1].getY() + " L" + pyramid.getVertex().getX() + " " + pyramid.getVertex().getY() + " L" + pyramid.getCoordinates()[0].getX() + " " + pyramid.getCoordinates()[0].getY() + "\"/>" + "\n";
        svg += PATH_OPACITY + drawSettings.getColor().getConvOpacity() + SVG_FILL + drawSettings.getColor().getPyramidColor() + PATH + "M" + pyramid.getCoordinates()[0].getX() + " " + pyramid.getCoordinates()[0].getY() + " L" + pyramid.getCoordinates()[2].getX() + " " + pyramid.getCoordinates()[2].getY() + " L" + pyramid.getVertex().getX() + " " + pyramid.getVertex().getY() + " L" + pyramid.getCoordinates()[0].getX() + " " + pyramid.getCoordinates()[0].getY() + "\"/>" + "\n";
        svg += PATH_OPACITY + drawSettings.getColor().getConvOpacity() + SVG_FILL + drawSettings.getColor().getPyramidColor() + PATH + "M" + pyramid.getCoordinates()[1].getX() + " " + pyramid.getCoordinates()[1].getY() + " L" + pyramid.getCoordinates()[3].getX() + " " + pyramid.getCoordinates()[3].getY() + " L" + pyramid.getVertex().getX() + " " + pyramid.getVertex().getY() + " L" + pyramid.getCoordinates()[1].getX() + " " + pyramid.getCoordinates()[1].getY() + "\"/>" + "\n";
        svg += PATH_OPACITY + drawSettings.getColor().getConvOpacity() + SVG_FILL + drawSettings.getColor().getPyramidColor() + PATH + "M" + pyramid.getCoordinates()[2].getX() + " " + pyramid.getCoordinates()[2].getY() + " L" + pyramid.getCoordinates()[3].getX() + " " + pyramid.getCoordinates()[3].getY() + " L" + pyramid.getVertex().getX() + " " + pyramid.getVertex().getY() + " L" + pyramid.getCoordinates()[2].getX() + " " + pyramid.getCoordinates()[2].getY() + "\"/>" + "\n\n";
        if (drawSettings.isActivateKernelDimensions()) {
            svg += drawText(pyramid, kernel);
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
        svg += PATH_OPACITY + drawSettings.getColor().getArrowOpacity() + "\" stroke=\"" + drawSettings.getColor().getArrowColor() + PATH + "M" + arrow.getVertex1().getX() + " " + arrow.getVertex1().getY() + " L" + arrow.getVertex2().getX() + " " + arrow.getVertex2().getY() + STROKE_LINECAP_SQUARE + "\n";
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
     * Draw shortcuts on a CNN
     *
     * @param shortcuts list of nodes
     */
    private void drawShortcuts(List<List<Node>> shortcuts) {
        for (List<Node> shortcut : shortcuts) {
            Cube cube1 = shortcut.get(0).getLastCube();
            Cube cube2 = shortcut.get(1).getLastCube();

            Coordinate vertex1 = cube1.getCoordinates()[10];
            Coordinate vertex2 = cube1.getCoordinates()[9];
            Coordinate vertex3 = cube2.getCoordinates()[9];
            Coordinate vertex4 = cube2.getCoordinates()[10];

            drawArrow(new Arrow(vertex1, vertex2));
            drawArrow(new Arrow(vertex2, vertex3));
            drawArrow(new Arrow(vertex4, vertex3));
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
        } else if (cube.isDenseLayer()) {
            return this.drawSettings.getColor().getDenseColor();
        } else if (cube.isInputLayer()) {
            return this.drawSettings.getColor().getInputColor();
        } else {
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
        } else if (cube.isDenseLayer()) {
            return this.drawSettings.getColor().getDenseOpacity();
        } else if (cube.isInputLayer()) {
            return this.drawSettings.getColor().getInputOpacity();
        } else {
            return this.drawSettings.getColor().getLayerOpacity();
        }
    }


    /**
     * Method that rotates the cube points
     */
    private void doTransformations(Coordinate[] coordinates) throws MatrixException {

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
     * Computes the center of a square
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
        this.svgString.append("<svg width=\"100%\"" + " height=\"100%\"" + " viewBox=\"").append(this.xMin).append(" ").append(this.yMin - this.drawSettings.getFont().getFontSize()).append(" ").append(xMax - xMin + this.drawSettings.getFont().getFontSize() + drawSettings.getViewBox().getZoom()).append(" ").append(yMax - yMin + this.drawSettings.getFont().getFontSize() + drawSettings.getViewBox().getZoom()).append("\" xmlns=\"http://www.w3.org/2000/svg\">\n").append("\t<g stroke=\"").append(drawSettings.getStroke().getStrokeColor()).append("\" stroke-width=\"").append(drawSettings.getStroke().getStrokeWidth()).append("\">\n\n");
    }

    /**
     * SVG Footer
     */
    private void addFooter() {
        this.svgString.append( "\t </g>\n" + "</svg>");
    }

    /**
     * Shift the Neural Network
     *
     * @param node the node to be moved
     */
    private void moveNode(Node node) throws MatrixException {
        List<Cube> modelQueue = node.getCubeList();
        for (int i = 0; i < modelQueue.size(); i++) {
            Cube cube = modelQueue.get(i);

            matrixController.move("x", cube.getCoordinates(), length);

            //Difference in length between the Z of the previous cube (it is X because it is rotated) and the Z of the current cube

            if (!cube.isKernel() && i != 0) {
                double l = Math.abs(depthAux - cube.getCoordinates()[4].getZ());
                depth = l + drawSettings.getDisplacement().getDisplacementLayers();
            }
            matrixController.move("z", cube.getCoordinates(), depth);

            if (!cube.isKernel()) {
                depthAux = cube.getCoordinates()[0].getZ();
            }
            if (cube.isKernel()) {
                Cube actualCube = modelQueue.get(i - 1);
                moveKernel(actualCube, cube);
            }
            updateMaxMin(cube.getCoordinates());
        }
    }

    /**
     * Updates the largest and smallest value of current x, y, and z
     *
     * @param coordinates coordinates of the figures
     */
    private void updateMaxMin(Coordinate[] coordinates) {
        for (Coordinate coordinate : coordinates) {
            if (coordinate.getX() > xMax) {
                xMax = coordinate.getX();
            }
            if (coordinate.getX() < xMin) {
                xMin = coordinate.getX();
            }
            if (coordinate.getY() > yMax) {
                yMax = coordinate.getY();
            }
            if (coordinate.getY() < yMin) {
                yMin = coordinate.getY();
            }
            if (coordinate.getZ() > zMax) {
                zMax = coordinate.getZ();
            }
            if (coordinate.getZ() < zMin) {
                zMin = coordinate.getZ();
            }
        }
    }

    /**
     * Moves the kernel to a random position
     *
     * @param actualCube the actual cube
     * @param kernel      kernel cube
     */
    private void moveKernel(Cube actualCube, Cube kernel) throws MatrixException {
        double difY = Math.abs((actualCube.getCoordinates()[3].getY() - kernel.getCoordinates()[3].getY()));
        double difX = Math.abs((actualCube.getCoordinates()[3].getX() - kernel.getCoordinates()[3].getX()));

        double xRandom = -difX + (Math.random() * (difX + difX)); //[-x,x]
        double yRandom = -difY + (Math.random() * (difY + difY)); //[-y,y]

        matrixController.move("x", kernel.getCoordinates(), xRandom);
        matrixController.move("y", kernel.getCoordinates(), yRandom);
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
        double centerX = ((xMax - xMin) / 2) + xMin;
        double centerY = ((yMax - yMin) / 2) + yMin;
        double centerZ = ((zMax - zMin) / 2) + zMin;

        imageCenter = new Coordinate(centerX, centerY, centerZ);
        xMax = -Double.MAX_VALUE;
        yMax = -Double.MAX_VALUE;
        xMin = Double.MAX_VALUE;
        yMin = Double.MAX_VALUE;
    }

    /**
     * Finds the vertices of the arrow that joins two cubes.
     *
     * @param cube1 first cube
     * @param cube2 second cube
     */
    private void lineTo(Cube cube1, Cube cube2) {
        Coordinate vertex1 = calculateCenter(Arrays.copyOfRange(cube1.getCoordinates(), 0, 4));
        Coordinate vertex2 = calculateCenter(Arrays.copyOfRange(cube2.getCoordinates(), 4, 8));
        drawArrow(new Arrow(vertex1, vertex2));
    }
}