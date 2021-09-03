import data.*;
import models.*;
import layers.*;
import drawing.*;
import settings.*;
import tree.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ResNet {

    public static void main(String[] args) throws Exception {
        DrawSettings drawSettings = new DrawSettings();
        Model model = new Model();
        LayerController layerController = new LayerController(drawSettings);

        //-------EDIT YOUR NEURAL NETWORK-------

		Node n1 = new Node(layerController);

		n1.add(new Input(72, 72, 30));
		Node n2 = n1.add(new Conv2D(48, new KernelSize(10, 10), new Strides(1, 1), "same"));
		Node n3 = n1.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));
		Node n4 = n1.add(new Conv2D(72, new KernelSize(5, 5), new Strides(2, 2), "same"));
		Node n5 = n1.add(new Conv2D(86, new KernelSize(5, 5), new Strides(1, 1), "same"));
		n1.add(new Dense(150));

		model.add(n1);
		model.addShortcut(n2, n4);
		model.addShortcut(n3, n5);
        //----------------------------------------

        SvgController svgController = new SvgController(drawSettings);

        BufferedWriter bw = new BufferedWriter(new FileWriter("output.svg"));
        bw.write(svgController.draw(model));
        bw.close();
    }
}