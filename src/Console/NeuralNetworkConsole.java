package Console;

import Data.*;
import Models.*;
import Layers.*;
import Drawer.*;
import Shapes.*;
import Tree.*;

/*****************************
             *****NEUROTRONIK CONSOLE*****
             ****************************/

public class NeuralNetworkConsole {

    public boolean run(){
        // First a basic CNN in Keras  (Input)
        // https://www.tensorflow.org/tutorials/images/cnn
        // model = models.Sequential()

        //model.add(layers.Conv2D(32, (3, 3), activation='relu', input_shape=(32, 32, 3)))
        //model.add(layers.MaxPooling2D((2, 2)))
        //model.add(layers.Conv2D(64, (3, 3), activation='relu'))
        //model.add(layers.MaxPooling2D((2, 2)))
        //model.add(layers.Conv2D(64, (3, 3), activation='relu'))
        //model.add(layers.Dense(200))
        //model.add(layers.Dense(400))

        //Input
        try {
            Model model = new Models().Sequential();
            Layers layers = new Layers();
            Node x1=new Node();
            Node x2=new Node();
            Node x3=new Node();

            x1.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), new Cube(new Coordinate(32, 32, 20)), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x1.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x1.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));


            x2.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), new Cube(new Coordinate(32, 32, 20)), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x2.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x2.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));


            x3.add(layers.concatenate(0,x1,x2));;
            x3.add(layers.Dense(200));
            x3.add(layers.Dense(400));

            //Create Tree
            model.add(x3);
            model.add(x2,x3);
            model.add(x1,x3);
            //

            SvgController svg = new SvgController();
            //System.out.println(svg.draw(model.getModelQueue()));

            return true;
        }
        catch(Exception e){
            System.out.println("error: "+e.getMessage());
            return false;
        }
    }
}
