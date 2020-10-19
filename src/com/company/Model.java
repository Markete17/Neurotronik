package com.company;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Model {
    Deque<Cube> modelStack=new ArrayDeque<>();
    Deque<Cube> convolutionsStack=new ArrayDeque<>();

    public void add(Cube cube){
        this.modelStack.addFirst(cube);
    }
    public void add(List<Cube> cubeList){
        this.modelStack.addFirst(cubeList.get(0));
        this.modelStack.addFirst(cubeList.get(1));
    }
}
