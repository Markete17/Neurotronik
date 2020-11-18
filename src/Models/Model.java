package Models;

import Shapes.Cube;

import java.util.ArrayList;
import java.util.List;

public class Model {
    List<Cube> modelQueue=new ArrayList<>();

    public void add(Cube cube){ //Max Pooling not add any cube
        return;
    }

    public void add(List<Cube> cubeList){
        for(Cube cube:cubeList){
            this.modelQueue.add(cube);
        }
    }

    public List<Cube> getModelQueue() {
        return modelQueue;
    }
}
