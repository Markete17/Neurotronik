import data.*;
import models.*;
import layers.*;
import drawing.*;
import settings.*;
import tree.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class EncoderDecoder {

    public static void main(String[] args) throws Exception {
        DrawSettings drawSettings = new DrawSettings();
        Model model = new Model();
        LayerController layerController = new LayerController(drawSettings);

        //-------EDIT YOUR NEURAL NETWORK-------

		Node n1 = new Node(layerController);

		n1.add(new Input(72, 72, 72));
		n1.add(new Conv2D(32, new KernelSize(5, 5), new Strides(1, 1), "same"));
		n1.add(new Conv2D(64, new KernelSize(5, 5), new Strides(2, 2), "same"));
		n1.add(new MaxPooling2D(new PoolSize(2, 2)));
		n1.add(new Conv2D(72, new KernelSize(5, 5), new Strides(2, 2), "same"));

		n1.add(new Deconv2D(72, new KernelSize(5, 5), new Strides(2, 2), "same"));
		n1.add(new Deconv2D(72, new KernelSize(5, 5), new Strides(2, 2), "same"));
		n1.add(new Deconv2D(72, new KernelSize(5, 5), new Strides(2, 2), "same"));
		n1.add(new Deconv2D(72, new KernelSize(5, 5), new Strides(2, 2), "same"));

		model.add(n1);
        //----------------------------------------

        SvgController svgController = new SvgController(drawSettings);

        BufferedWriter bw = new BufferedWriter(new FileWriter("output.svg"));
        bw.write(svgController.draw(model));
        bw.close();
    }
}