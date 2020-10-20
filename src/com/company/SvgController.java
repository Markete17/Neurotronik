package com.company;

import java.util.Deque;
import java.util.List;

public class SvgController {

    String svgString="<html>\n" +
            "    <head>\n" +
            "        <title>Page Title</title>\n" +
            "     <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <svg width=\"100%\" height=\"150\"> \n";

    public String draw(Deque<Cube> modelQueue) {
        for(Cube cube:modelQueue) {
            svgString += "<line x1=" + cube.getCubeList().get(0).getX() + 20 + " y1=" + cube.getCubeList().get(0).getY() + 100 + " x2=" + cube.getCubeList().get(1).getX() + 20 + " y2=" + cube.getCubeList().get(1).getY() + 130;
            svgString += " style=stroke:black;stroke-width:2;/> \n";

            svgString += "<line x1=" + cube.getCubeList().get(2).getX() + 20 + " y1=" + (100 - cube.getCubeList().get(2).getY()) + " x2=" + cube.getCubeList().get(0).getX() + 20 + " y2=" + cube.getCubeList().get(0).getY() + 100;
            svgString += " style=stroke:black;stroke-width:2;/> \n";

            svgString += "<line x1=" + cube.getCubeList().get(2).getX() + 20 + " y1=" + (100 - cube.getCubeList().get(2).getY()) + " x2=" + cube.getCubeList().get(3).getX() + 20 + " y2=" + cube.getCubeList().get(3).getY() + 100;
            svgString += " style=stroke:black;stroke-width:2;/> \n";

            svgString += "<line x1=" + cube.getCubeList().get(3).getX() + 40 + " y1=" + (100 - cube.getCubeList().get(2).getY() + 20) + " x2=" + cube.getCubeList().get(1).getX() + 20 + " y2=" + cube.getCubeList().get(1).getY() + 130;
            svgString += " style=stroke:black;stroke-width:2;/> \n";
        }
        return this.svgString;
    }
}
