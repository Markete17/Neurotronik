package testing;

import console.NeuralNetworkConsole;
import data.KernelSize;
import data.PoolSize;
import data.Strides;
import drawing.SvgController;
import exceptions.*;
import layers.*;
import models.Model;
import org.junit.Assert;
import org.junit.Test;
import settings.DrawSettings;
import tree.Node;

public class NeuralNetworkTest {

    @Test
    public void testBasicCNN() throws MatrixException, LayersException, ConsoleException, SettingsException, TreeException {
        NeuralNetworkConsole neuralNetworkConsole = new NeuralNetworkConsole();
        try {
            DrawSettings drawSettings = new DrawSettings();
            Model model = new Model();
            LayerController layerController = new LayerController(drawSettings);

            //------------------EDIT YOUR NEURAL NETWORK-------------------
            Node n1 = new Node(layerController);

            n1.add(new Input(48, 32, 10));
            n1.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            n1.add(new MaxPooling2D(new PoolSize(2, 2)));
            n1.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));
            n1.add(new MaxPooling2D(new PoolSize(2, 2)));
            n1.add(new Conv2D(72, new KernelSize(10, 10), new Strides(1, 1), "same"));
            n1.add(new Dense(200));
            n1.add(new Dense(300));
            model.add(n1);

            //---------------------------------------------------------------

            SvgController svgController = new SvgController(drawSettings);
            String output = svgController.draw(model);
            Assert.assertEquals(output, output);

        } catch (Exception e) {
            neuralNetworkConsole.handleError(e);
        }
    }

    @Test
    public void testSiamese() throws MatrixException, LayersException, ConsoleException, SettingsException, TreeException {
        NeuralNetworkConsole neuralNetworkConsole = new NeuralNetworkConsole();
        try {
            DrawSettings drawSettings = new DrawSettings();
            Model model = new Model();
            LayerController layerController = new LayerController(drawSettings);

            //------------------EDIT YOUR NEURAL NETWORK-------------------
            Node n1 = new Node(layerController);
            Node n2 = new Node(layerController);
            Node n3 = new Node(layerController);

            n1.add(new Input(48, 32, 10));
            n1.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            n1.add(new MaxPooling2D(new PoolSize(2, 2)));
            n1.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));
            n1.add(new MaxPooling2D(new PoolSize(2, 2)));
            n1.add(new Conv2D(72, new KernelSize(10, 10), new Strides(1, 1), "same"));

            n2.add(new Input(48, 32, 10));
            n2.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            n2.add(new MaxPooling2D(new PoolSize(2, 2)));
            n2.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));
            n2.add(new MaxPooling2D(new PoolSize(2, 2)));
            n2.add(new Conv2D(72, new KernelSize(10, 10), new Strides(1, 1), "same"));

            n3.add(new Dense(200));
            n3.add(new Dense(300));
            n3.add(new Dense(400));
            n3.add(new Dense(500));

            model.add(n3);
            model.add(n1, n3);
            model.add(n2, n3);
            //---------------------------------------------------------------

            SvgController svgController = new SvgController(drawSettings);
            String output = svgController.draw(model);
            Assert.assertEquals(output, output);

        } catch (Exception e) {
            neuralNetworkConsole.handleError(e);
        }
    }

    @Test
    public void testComplexSiamese() throws MatrixException, LayersException, ConsoleException, SettingsException, TreeException {
        NeuralNetworkConsole neuralNetworkConsole = new NeuralNetworkConsole();
        try {
            DrawSettings drawSettings = new DrawSettings();
            Model model = new Model();
            LayerController layerController = new LayerController(drawSettings);

            //------------------EDIT YOUR NEURAL NETWORK-------------------
            Node x1a = new Node(layerController);
            Node x1b = new Node(layerController);
            Node x1 = new Node(layerController);
            Node x2 = new Node(layerController);
            Node xp1 = new Node(layerController);
            Node xp3 = new Node(layerController);

            x1a.add(new Input(20,20,10));
            x1a.add(new Conv2D(24,new KernelSize(10,10),new Strides(1,1),"same"));

            x1b.add(new Input(20,20,10));
            x1b.add(new Conv2D(24,new KernelSize(10,10),new Strides(1,1),"same"));

            x2.add(new Input(20,20,10));
            x2.add(new Conv2D(24,new KernelSize(10,10),new Strides(1,1),"same"));
            x2.add(new MaxPooling2D(new PoolSize(2,2)));
            x2.add(new Conv2D(30,new KernelSize(5,5),new Strides(1,1),"same"));

            x1.add(new Concatenate(x1a,x1b));
            x1.add(new MaxPooling2D(new PoolSize(2,2)));
            x1.add(new Conv2D(30,new KernelSize(5,5),new Strides(1,1),"same"));

            xp1.add(new Concatenate(x1, x2));
            xp1.add(new Conv2D(30,new KernelSize(5,5),new Strides(1,1),"same"));

            xp3.add(new Dense(100));

            //Create Tree
            model.add(xp3);
            model.add(x1a, x1);
            model.add(x1b, x1);
            model.add(x1, xp1);
            model.add(x2, xp1);
            model.add(xp1,xp3);
            model.add(x2,xp3);
            //---------------------------------------------------------------

            SvgController svgController = new SvgController(drawSettings);
            String output = svgController.draw(model);
            Assert.assertEquals(output, output);

        } catch (Exception e) {
            neuralNetworkConsole.handleError(e);
        }
    }

    @Test
    public void testEncoderDecoder() throws MatrixException, LayersException, ConsoleException, SettingsException, TreeException {
        NeuralNetworkConsole neuralNetworkConsole = new NeuralNetworkConsole();
        try {
            DrawSettings drawSettings = new DrawSettings();
            Model model = new Model();
            LayerController layerController = new LayerController(drawSettings);

            //------------------EDIT YOUR NEURAL NETWORK-------------------
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
            //---------------------------------------------------------------

            SvgController svgController = new SvgController(drawSettings);
            String output = svgController.draw(model);
            Assert.assertEquals(output, output);

        } catch (Exception e) {
            neuralNetworkConsole.handleError(e);
        }
    }

    @Test
    public void testRestNet() throws MatrixException, LayersException, ConsoleException, SettingsException, TreeException {
        NeuralNetworkConsole neuralNetworkConsole = new NeuralNetworkConsole();
        try {
            DrawSettings drawSettings = new DrawSettings();
            Model model = new Model();
            LayerController layerController = new LayerController(drawSettings);

            //------------------EDIT YOUR NEURAL NETWORK-------------------
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
            //---------------------------------------------------------------

            SvgController svgController = new SvgController(drawSettings);
            String output = svgController.draw(model);
            Assert.assertEquals(output, output);

        } catch (Exception e) {
            neuralNetworkConsole.handleError(e);
        }
    }

    @Test
    public void testInception() throws MatrixException, LayersException, ConsoleException, SettingsException, TreeException {
        NeuralNetworkConsole neuralNetworkConsole = new NeuralNetworkConsole();
        try {
            DrawSettings drawSettings = new DrawSettings();
            Model model = new Model();
            LayerController layerController = new LayerController(drawSettings);

            //------------------EDIT YOUR NEURAL NETWORK-------------------
            Node x1a = new Node(layerController);
            Node x1b = new Node(layerController);
            Node x3a = new Node(layerController);
            Node x3b = new Node(layerController);
            Node x1 = new Node(layerController);
            Node x2 = new Node(layerController);
            Node x3 = new Node(layerController);

            x1a.add(new Input(32,32,20));

            x1b.add(new Input(32,32,20));
            x1b.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));
            x1b.add(new MaxPooling2D(new PoolSize(2,2)));
            x1b.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

            x2.add(new Input(32,32,20));
            x2.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));
            x2.add(new MaxPooling2D(new PoolSize(2,2)));
            x2.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));
            x2.add(new MaxPooling2D(new PoolSize(2,2)));
            x2.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

            x3a.add(new Input(32,32,20));
            x3a.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));
            x3a.add(new MaxPooling2D(new PoolSize(2,2)));
            x3a.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

            x3b.add(new Input(32,32,20));
            x3b.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));
            x3b.add(new MaxPooling2D(new PoolSize(2,2)));
            x3b.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

            x3.add(new Dense(200));

            model.add(x3);
            model.add(x1a,x1b);
            model.add(x1a,x2);
            model.add(x1a,x3a);
            model.add(x1a,x3b);

            model.add(x1b,x3);
            model.add(x2,x3);
            model.add(x3a,x3);
            model.add(x3b,x3);
            //---------------------------------------------------------------

            SvgController svgController = new SvgController(drawSettings);
            String output = svgController.draw(model);
            Assert.assertEquals(output, output);

        } catch (Exception e) {
            neuralNetworkConsole.handleError(e);
        }
    }
}
