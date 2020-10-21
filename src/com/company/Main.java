package com.company;

public class Main {

    public static void main(String[] args) {

        // First a basic CNN in Keras  (Input)
        // https://www.tensorflow.org/tutorials/images/cnn
        // model = models.Sequential()

        //model.add(layers.Conv2D(32, (3, 3), activation='relu', input_shape=(32, 32, 3)))
        //model.add(layers.MaxPooling2D((2, 2)))
        //model.add(layers.Conv2D(64, (3, 3), activation='relu'))
        //model.add(layers.MaxPooling2D((2, 2)))
        //model.add(layers.Conv2D(64, (3, 3), activation='relu'))

        //Input
        Model model=new Models().Sequential();
        Layer layers=new Layer();
        model.add(layers.Conv2D(32,new Tuple(3,3),new Tuple(1,1),"relu",new Cube(new Triple(32,32,3)),"same"));
        model.add(layers.MaxPooling2D(new Tuple(2,2)));
        model.add(layers.Conv2D(64,new Tuple(3,3),new Tuple(1,1),"relu","same"));
        model.add(layers.MaxPooling2D(new Tuple(2,2)));
        model.add(layers.Conv2D(64,new Tuple(3,3),new Tuple(1,1),"relu","same"));

        SvgController svg=new SvgController();
        System.out.println(svg.draw(model.modelQueue));


    }
}
