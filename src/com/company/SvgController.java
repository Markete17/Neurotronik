package com.company;

import java.util.Deque;
import java.util.List;

public class SvgController {
    int scaleX=2;
    int scaleY=2;
    int scaleZ=3;
    int shift=0;
    int i=0;
    String svgString="<html>\n" +
            "\t"+"<head>\n" +
            "\t\t"+"<title>Page Title</title>\n" +
            "\t\t"+"<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n" +
            "\t"+"</head>\n" +
            "\t"+"<body>\n" +
            "\t\t"+"<svg width=\"100%\" height=\"150\"> \n";

    public String draw(Deque<Cube> modelQueue) {
        for(Cube cube:modelQueue) {

                //origin (20,100)
                String x0 = String.valueOf((cube.getCubeList().get(0).getX() * scaleX) + 20 + i * shift);
                String y0 = String.valueOf((cube.getCubeList().get(0).getY() * scaleY) + 100);

                String x2 = String.valueOf((cube.getCubeList().get(2).getX() * scaleX) + 20 + i * shift);
                String y2 = String.valueOf(100 - (cube.getCubeList().get(2).getY() * scaleY));
                //-------

                String x4 = String.valueOf((cube.getCubeList().get(4).getZ() * scaleZ) + 20 + i * shift);
                String y4 = String.valueOf((cube.getCubeList().get(4).getY() * scaleY) + 100);

                String x6 = String.valueOf((cube.getCubeList().get(6).getZ() * scaleZ) + 20 + i * shift);
                String y6 = String.valueOf(100 - (cube.getCubeList().get(6).getY() * scaleY));

                //----------

                String x1 = String.valueOf((cube.getCubeList().get(1).getX() * scaleX) + 20 + i * shift);
                String y1 = String.valueOf((cube.getCubeList().get(1).getY() * scaleY) + 100 + (cube.getCubeList().get(3).getX()));

                String x3 = String.valueOf((cube.getCubeList().get(3).getX() * scaleX) + 20 + i * shift);
                String y3 = String.valueOf(100 - (cube.getCubeList().get(3).getY() * scaleY) + (cube.getCubeList().get(3).getX()));

                //---------

                String x5 = String.valueOf(Integer.parseInt(x1) + (cube.getCubeList().get(5).getZ() * scaleZ) + i * shift);
                String y5 = y1;

                String x7 = String.valueOf(Integer.parseInt(x3) + (cube.getCubeList().get(7).getZ() * scaleZ) + i * shift);
                String y7 = y3;


                svgString += "\t\t\t" + "<line x1=" + "\"" + x2 + "\"" + " y1=" + "\"" + y2 + "\"" + " x2=" + "\"" + x0 + "\"" + " y2=" + "\"" + y0 + "\"";
                svgString += " style=" + "\"" + "stroke:red;stroke-width:2;" + "\"/> \n";

                svgString += "\t\t\t" + "<line x1=" + "\"" + x1 + "\"" + " y1=" + "\"" + y1 + "\"" + " x2=" + "\"" + x3 + "\"" + " y2=" + "\"" + y3 + "\"";
                svgString += " style=" + "\"" + "stroke:red;stroke-width:2;" + "\"/> \n";

                svgString += "\t\t\t" + "<line x1=" + "\"" + x6 + "\"" + " y1=" + "\"" + y6 + "\"" + " x2=" + "\"" + x4 + "\"" + " y2=" + "\"" + y4 + "\"";
                svgString += " stroke-dasharray=\"1 1\" style=" + "\"stroke:red; stroke-width:2;" + "\"/> \n";

                svgString += "\t\t\t" + "<line x1=" + "\"" + x7 + "\"" + " y1=" + "\"" + y7 + "\"" + " x2=" + "\"" + x5 + "\"" + " y2=" + "\"" + y5 + "\"";
                svgString += " style=" + "\"" + "stroke:red;stroke-width:2;" + "\"/> \n";

                svgString += "\t\t\t" + "<line x1=" + "\"" + x0 + "\"" + " y1=" + "\"" + y0 + "\"" + " x2=" + "\"" + x1 + "\"" + " y2=" + "\"" + y1 + "\"";
                svgString += " style=" + "\"" + "stroke:red;stroke-width:2;" + "\"/> \n";

                svgString += "\t\t\t" + "<line x1=" + "\"" + x2 + "\"" + " y1=" + "\"" + y2 + "\"" + " x2=" + "\"" + x3 + "\"" + " y2=" + "\"" + y3 + "\"";
                svgString += " style=" + "\"" + "stroke:red;stroke-width:2;" + "\"/> \n";

                svgString += "\t\t\t" + "<line x1=" + "\"" + x2 + "\"" + " y1=" + "\"" + y2 + "\"" + " x2=" + "\"" + x6 + "\"" + " y2=" + "\"" + y6 + "\"";
                svgString += " style=" + "\"" + "stroke:red;stroke-width:2;" + "\"/> \n";

                svgString += "\t\t\t" + "<line x1=" + "\"" + x3 + "\"" + " y1=" + "\"" + y3 + "\"" + " x2=" + "\"" + x7 + "\"" + " y2=" + "\"" + y7 + "\"";
                svgString += " style=" + "\"" + "stroke:red;stroke-width:2;" + "\"/> \n";

                svgString += "\t\t\t" + "<line x1=" + "\"" + x0 + "\"" + " y1=" + "\"" + y0 + "\"" + " x2=" + "\"" + x4 + "\"" + " y2=" + "\"" + y4 + "\"";
                svgString += " stroke-dasharray=\"1 1\" style=" + "\" stroke:red; stroke-width:2;" + "\"/> \n";

                svgString += "\t\t\t" + "<line x1=" + "\"" + x1 + "\"" + " y1=" + "\"" + y1 + "\"" + " x2=" + "\"" + x5 + "\"" + " y2=" + "\"" + y5 + "\"";
                svgString += " style=" + "\"" + "stroke:red;stroke-width:2;" + "\"/> \n";

                svgString += "\t\t\t" + "<line x1=" + "\"" + x4 + "\"" + " y1=" + "\"" + y4 + "\"" + " x2=" + "\"" + x5 + "\"" + " y2=" + "\"" + y5 + "\"";
                svgString += " stroke-dasharray=\"1 1\" style=" + "\"stroke:red; stroke-width:2;" + "\"/> \n";

                svgString += "\t\t\t" + "<line x1=" + "\"" + x6 + "\"" + " y1=" + "\"" + y6 + "\"" + " x2=" + "\"" + x7 + "\"" + " y2=" + "\"" + y7 + "\"";
                svgString += " style=" + "\"" + "stroke:red;stroke-width:2;" + "\"/> \n";

        }
        svgString+="\t\t"+"</svg> \n" +"\t"+"</body>\n"+"</html>";
        return this.svgString;
    }
}
