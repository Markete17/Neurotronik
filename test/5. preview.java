        Node x1a = new Node(layerController);
        Node x1b = new Node(layerController);
        Node x1 = new Node(layerController);
        Node x2 = new Node(layerController);
        Node x3 = new Node(layerController);
        Node x4 = new Node(layerController);
        Node xp1 = new Node(layerController);
        Node xp2 = new Node(layerController);
        Node xp3 = new Node(layerController);

        x1a.add(new Input(32,32,20));
        x1a.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));
        x1a.add(new MaxPooling2D(new PoolSize(2,2)));
        x1a.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

        x1b.add(new Input(32,32,20));
        x1b.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));
        x1b.add(new MaxPooling2D(new PoolSize(2,2)));
        x1b.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

        x2.add(new Input(32,32,20));
        x2.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));
        x2.add(new MaxPooling2D(new PoolSize(2,2)));
        x2.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));
        x2.add(new MaxPooling2D(new PoolSize(2,2)));
        x2.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

        x3.add(new Input(32,32,20));
        x3.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));
        x3.add(new MaxPooling2D(new PoolSize(2,2)));
        x3.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));
        x3.add(new MaxPooling2D(new PoolSize(2,2)));
        x3.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

        x4.add(new Input(32,32,20));
        x4.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));
        x4.add(new MaxPooling2D(new PoolSize(2,2)));
        x4.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));
        x4.add(new MaxPooling2D(new PoolSize(2,2)));
        x4.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

        x1.add(new Concatenate(x1a,x1b));
        x1.add(new MaxPooling2D(new PoolSize(2,2)));
        x1.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

        xp1.add(new Concatenate(x1, x2));
        xp1.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

        xp2.add(new Concatenate(x2, x3));
        xp2.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));

        xp3.add(new Dense(200));
        xp3.add(new Dense(200));
        xp3.add(new Dense(200));

        //Create Tree
        model.add(xp3);
        model.add(x1a, x1);
        model.add(x1b, x1);
        model.add(x1, xp1);
        model.add(x2, xp1);
        model.add(x3, xp2);
        model.add(x4, xp2);
        model.add(xp1, xp3);
        model.add(xp2, xp3);

        //Jumps
        model.addJump(x1b,xp1);
        model.addJump(x1b,xp3);
        model.addJump(x2,xp3);
