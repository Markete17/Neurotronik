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
            Node x3a = new Node(layerController);
            Node x3b = new Node(layerController);
            Node x1 = new Node(layerController);
            Node x2 = new Node(layerController);
            Node x3 = new Node(layerController);
            Node xp1 = new Node(layerController);
            Node xp2 = new Node(layerController);
            Node xp3 = new Node(layerController);
            Node x5 = new Node(layerController);
            Node x6 = new Node(layerController);
            Node aux = new Node(layerController);

            x1a.add(new Input(32, 32, 20));
            x1a.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            x1a.add(new MaxPooling2D(new PoolSize(2, 2)));
            x1a.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            x1b.add(new Input(32, 32, 20));
            x1b.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            x1b.add(new MaxPooling2D(new PoolSize(2, 2)));
            x1b.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            x2.add(new Input(32, 32, 20));
            x2.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            x2.add(new MaxPooling2D(new PoolSize(2, 2)));
            x2.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));
            x2.add(new MaxPooling2D(new PoolSize(2, 2)));
            x2.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            x3a.add(new Input(32, 32, 20));
            x3a.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            x3a.add(new MaxPooling2D(new PoolSize(2, 2)));
            x3a.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            x3b.add(new Input(32, 32, 20));
            x3b.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            x3b.add(new MaxPooling2D(new PoolSize(2, 2)));
            x3b.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            x5.add(new Input(32, 32, 20));
            x5.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            x5.add(new MaxPooling2D(new PoolSize(2, 2)));
            x5.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));
            x5.add(new MaxPooling2D(new PoolSize(2, 2)));
            x5.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            x6.add(new Input(32, 32, 20));
            x6.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            x6.add(new MaxPooling2D(new PoolSize(2, 2)));
            x6.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));
            x6.add(new MaxPooling2D(new PoolSize(2, 2)));
            x6.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            x1.add(new Concatenate(x1a, x1b));
            x1.add(new MaxPooling2D(new PoolSize(2, 2)));
            x1.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            x3.add(new Concatenate(x3a, x3b));
            x3.add(new MaxPooling2D(new PoolSize(2, 2)));
            x3.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            xp1.add(new Concatenate(x1, x2));
            xp1.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            xp2.add(new Concatenate(x2, x3));
            xp2.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            aux.add(new Concatenate(x5, x6));
            aux.add(new MaxPooling2D(new PoolSize(2, 2)));
            aux.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));

            xp3.add(new Dense(200));
            xp3.add(new Dense(200));
            xp3.add(new Dense(200));

            //Create Tree
            model.add(xp3);
            model.add(x1a, x1);
            model.add(x1b, x1);
            model.add(x1, xp1);
            model.add(x2, xp1);
            model.add(x3a, x3);
            model.add(x3b, x3);
            model.add(x5, aux);
            model.add(x6, aux);
            model.add(xp1, xp2);
            model.add(x3, xp2);
            model.add(xp2, xp3);
            model.add(x3, xp3);
            model.add(aux, xp3);
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
            n1.add(new MaxPooling2D(new PoolSize(2, 2)));
            Node n3 = n1.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));
            n1.add(new MaxPooling2D(new PoolSize(2, 2)));
            Node n4 = n1.add(new Conv2D(72, new KernelSize(5, 5), new Strides(2, 2), "same"));
            Node n5 = n1.add(new Conv2D(120, new KernelSize(5, 5), new Strides(1, 1), "same"));
            n1.add(new Dense(200));
            n1.add(new Dense(300));

            model.add(n1);
            model.addShortcut(n2, n3);
            model.addShortcut(n4, n5);
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
            Node n1 = new Node(layerController);
            Node n2 = new Node(layerController);
            Node n3 = new Node(layerController);
            Node n4 = new Node(layerController);
            Node n5 = new Node(layerController);
            Node np = new Node(layerController);

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

            n3.add(new Input(48, 32, 10));
            n3.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            n3.add(new MaxPooling2D(new PoolSize(2, 2)));
            n3.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));
            n3.add(new MaxPooling2D(new PoolSize(2, 2)));
            n3.add(new Conv2D(72, new KernelSize(10, 10), new Strides(1, 1), "same"));

            n4.add(new Input(48, 32, 10));
            n4.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            n4.add(new MaxPooling2D(new PoolSize(2, 2)));
            n4.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));
            n4.add(new MaxPooling2D(new PoolSize(2, 2)));
            n4.add(new Conv2D(72, new KernelSize(10, 10), new Strides(1, 1), "same"));

            n5.add(new Input(48, 32, 10));
            n5.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            n5.add(new MaxPooling2D(new PoolSize(2, 2)));
            n5.add(new Conv2D(64, new KernelSize(5, 5), new Strides(1, 1), "same"));
            n5.add(new MaxPooling2D(new PoolSize(2, 2)));
            n5.add(new Conv2D(72, new KernelSize(10, 10), new Strides(1, 1), "same"));

            np.add(new Dense(200));
            np.add(new Dense(300));
            np.add(new Dense(400));
            np.add(new Dense(500));

            model.add(np);
            model.add(n1, np);
            model.add(n2, np);
            model.add(n3, np);
            model.add(n4, np);
            model.add(n5, np);
            //---------------------------------------------------------------

            SvgController svgController = new SvgController(drawSettings);
            String output = svgController.draw(model);
            Assert.assertEquals(output, output);

        } catch (Exception e) {
            neuralNetworkConsole.handleError(e);
        }
    }
}
