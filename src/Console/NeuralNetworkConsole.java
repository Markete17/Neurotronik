package Console;

import Data.*;
import Models.*;
import Layers.*;
import Drawer.*;
import Tree.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*****************************
 *****NEUROTRONIK CONSOLE*****
 ****************************/

public class NeuralNetworkConsole {

    public void run() {

        //Input
        try {
            DrawSettings drawSettings = new DrawSettings();
            Model model = new Models().Sequential();
            LayerController layerController = new LayerController(drawSettings);

            /**************************/
            /*EDIT YOUR NEURAL NETWORK*/
            /**************************/
            Node n1=new Node(layerController);

            n1.add(new Input(64,64,10));
            n1.add(new Conv2D(24,new Tuple(5,5),new Tuple(2,2),"same"));
            n1.add(new Conv2D(32,new Tuple(5,5),new Tuple(2,2),"same"));
            n1.add(new Conv2D(48,new Tuple(5,5),new Tuple(2,2),"same"));
            n1.add(new Deconv2D(48,new Tuple(5,5),new Tuple(2,2),"same"));
            n1.add(new Deconv2D(56,new Tuple(5,5),new Tuple(2,2),"same"));
            n1.add(new Deconv2D(66,new Tuple(5,5),new Tuple(2,2),"same"));
            n1.add(new Dense(200));
            n1.add(new Dense(300));
            model.add(n1);

            /**************************/

            SvgController svg = new SvgController(drawSettings);
            writeFile(svg.draw(model.getModelTree()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void writeFile(String draw) throws IOException {
        File file = new File("C:\\Users\\Marcos\\Desktop\\URJC\\Neurotronik\\src\\neurotronik.svg");
        BufferedWriter bw;
        bw = new BufferedWriter(new FileWriter(file));
        bw.write(draw);
        bw.close();
    }
}
