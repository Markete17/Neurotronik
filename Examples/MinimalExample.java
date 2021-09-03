import data.*;
import models.*;
import layers.*;
import drawing.*;
import settings.*;
import tree.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class MinimalExample {

    public static void main(String[] args) throws Exception {
        DrawSettings drawSettings = new DrawSettings();
        Model model = new Model();
        LayerController layerController = new LayerController(drawSettings);

        //-------EDIT YOUR NEURAL NETWORK-------
		Node n1 = new Node(layerController);

		n1.add(new Input(48, 32, 10));
		n1.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
		n1.add(new MaxPooling2D(new PoolSize(2, 2)));
		n1.add(new Conv2D(72, new KernelSize(10, 10), new Strides(1, 1), "same"));
		n1.add(new Dense(100));
		n1.add(new Dense(200));
		model.add(n1);
        //----------------------------------------

        SvgController svgController = new SvgController(drawSettings);

        BufferedWriter bw = new BufferedWriter(new FileWriter("output.svg"));
        bw.write(svgController.draw(model));
        bw.close();
    }
}