package console;

import data.*;
import exceptions.ConsoleException;
import models.*;
import layers.*;
import drawing.*;
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

        //Input
        try {
            DrawSettings drawSettings = new DrawSettings();
            Model model = new Model();
            LayerController layerController = new LayerController(drawSettings);

            /**************************/
            /*EDIT YOUR NEURAL NETWORK*/
            /**************************/
            Node n1=new Node(layerController);

            n1.add(new Input(48,32,10));
            n1.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
            n1.add(new MaxPooling2D(new Tuple(2,2)));
            n1.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
            n1.add(new MaxPooling2D(new Tuple(2,2)));
            n1.add(new Conv2D(72,new Tuple(10,10),new Tuple(1,1),"same"));
            n1.add(new Dense(200));
            n1.add(new Dense(300));
            model.add(n1);
            /**************************/

            SvgController svg = new SvgController(drawSettings);
            writeFile(svg.draw(model.getModelTree()));
        } catch (Exception e) {
            throw new ConsoleException("Error: " + e.getMessage());
        }
    }

    private void writeFile(String draw) throws ConsoleException, IOException {
        File file = new File(getURL());
        BufferedWriter bw;
        bw = new BufferedWriter(new FileWriter(file));
        try {
            bw.write(draw);
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
