package com.company;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Model {
    Deque<Cube> modelQueue=new ArrayDeque<>();

    public void add(Cube cube){
        return;
    }

    public void add(List<Cube> cubeList){
        for(Cube cube:cubeList){
            this.modelQueue.addLast(cube);
        }
    }
}
