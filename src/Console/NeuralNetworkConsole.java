package Console;

import Data.*;
import Models.*;
import Layers.*;
import Drawer.*;
import Shapes.*;
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
            Layers layers = new Layers(drawSettings);
            Node x1a = new Node();
            Node x1b = new Node();
            Node x1 = new Node();
            Node x2 = new Node();
            Node x3 = new Node();
            Node xp1 = new Node();
            Node xp3 = new Node();

            x1a.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
            x1a.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "sam"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x1a.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            x1b.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
            x1b.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x1b.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            x2.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
            x2.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x2.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x2.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            x3.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
            x3.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x3.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x3.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            x1.add(layers.concatenate(x1a, x1b));
            layers.MaxPooling2D(new Tuple(2, 2));
            x1.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            xp1.add(layers.concatenate(x1, x2));
            xp1.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            layers.setDenseLayer(true);
            xp3.add(layers.concatenate(xp1, x1));
            xp3.add(layers.Dense(200));
            xp3.add(layers.Dense(200));
            xp3.add(layers.Dense(200));

            //Create Tree
            model.add(xp3);
            model.add(x1a, x1);
            model.add(x1b, x1);
            model.add(x1, xp1);
            model.add(x2, xp1);
            model.add(xp1, xp3);
            model.add(x3, xp3);

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
