package Console;

import Data.*;
import Models.*;
import Layers.*;
import Drawer.*;
import Shapes.*;

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
            model.add(layers.Conv2D(32, new Tuple(3, 3), new Tuple(1, 1), new Cube(new Coordinate(32, 32, 20)), "same"));
            model.add(layers.MaxPooling2D(new Tuple(2, 2)));
            model.add(layers.Conv2D(64, new Tuple(3, 3), new Tuple(1, 1), "same"));
            model.add(layers.MaxPooling2D(new Tuple(2, 2)));
            model.add(layers.Conv2D(64, new Tuple(3, 3), new Tuple(1, 1), "same"));
            model.add(layers.Dense(200));
            model.add(layers.Dense(400));

            SvgController svg = new SvgController();
            System.out.println(svg.draw(model.getModelQueue()));

            return true;
        }
        catch(Exception e){
            System.out.println("error: "+e.getMessage());
            return false;
        }
    }
}
