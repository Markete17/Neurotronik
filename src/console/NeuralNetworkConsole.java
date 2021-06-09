package console;

import data.*;
import exceptions.ConsoleException;
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

    public void run() throws ConsoleException {

        try {
            DrawSettings drawSettings = new DrawSettings();
            Model model = new Model();
            LayerController layerController = new LayerController(drawSettings);

            //------------------EDIT YOUR NEURAL NETWORK-------------------

            Node n1=new Node(layerController);

            n1.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same",new Input(48,32,10)));
            n1.add(new MaxPooling2D(new PoolSize(2,2)));
            n1.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

            model.add(n1);

            //---------------------------------------------------------------

            SvgController svgController = new SvgController(drawSettings);
            writeFile(svgController.draw(model.getModelTree()));
        } catch (Exception e) {
            throw new ConsoleException("Error: " + e.getMessage());
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
