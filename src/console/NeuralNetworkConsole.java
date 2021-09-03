package console;


import data.*;
import exceptions.*;
import models.*;
import layers.*;
import drawing.*;
import settings.*;
import tree.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*****************************
 *****NEUROTRONIK CONSOLE*****
 ****************************/

public class NeuralNetworkConsole {

    public void run() throws Exception {

        try {
            DrawSettings drawSettings = new DrawSettings();
            Model model = new Model();
            LayerController layerController = new LayerController(drawSettings);

            //------------------EDIT YOUR NEURAL NETWORK-------------------
            Node n1 = new Node(layerController);

            n1.add(new Input(48, 32, 10));
            n1.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
            n1.add(new MaxPooling2D(new PoolSize(2, 2)));
            n1.add(new Conv2D(72, new KernelSize(10, 10), new Strides(1, 1), "same"));
            n1.add(new Dense(100));
            n1.add(new Dense(200));
            model.add(n1);
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
        BufferedWriter bw = new BufferedWriter(new FileWriter("output.svg"));
        try {
            bw.write(svg);
        } catch (IOException e) {
            throw new ConsoleException(e.getMessage());
        } finally {
            bw.close();
        }
    }
}
