package console;


import data.*;
import exceptions.*;
import models.*;
import layers.*;
import drawing.*;
import settings.DrawSettings;
import tree.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*****************************
 *****NEUROTRONIK CONSOLE*****
 ****************************/

public class NeuralNetworkConsole {

    public void run() throws LayersException, MatrixException, SettingsException, TreeException, ConsoleException {

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
            writeFile(svgController.draw(model));

        } catch (Exception e) {
            handleError(e);
        }
    }

    public void handleError(Exception e) throws LayersException, MatrixException, SettingsException, TreeException, ConsoleException {
        if (e instanceof LayersException) {
            throw new LayersException("Error in layers module: " + e.getMessage());
        } else if (e instanceof MatrixException) {
            throw new MatrixException("Error in matrices module: " + e.getMessage());
        } else if (e instanceof SettingsException) {
            throw new SettingsException("Error in settings module: " + e.getMessage());
        } else if (e instanceof TreeException) {
            throw new TreeException("Error in tree module: " + e.getMessage());
        } else {
            throw new ConsoleException("Error in settings module: " + e.getMessage());
        }
    }

    private void writeFile(String svg) throws ConsoleException, IOException {
        File file = new File(getURL());
        BufferedWriter bw;
        bw = new BufferedWriter(new FileWriter(file));
        try {
            bw.write(svg);
        } catch (IOException e) {
            throw new ConsoleException(e.getMessage());
        } finally {
            bw.close();
        }
    }

    private String getURL() {
        return System.getProperty("user.dir") + "\\src\\" + "\\neurotronik.svg\\";
    }
}
