        Node n1=new Node(layerController);
        Node n2=new Node(layerController);
        Node n3=new Node(layerController);

        n1.add(new Input(48,32,10));
        n1.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));
        n1.add(new MaxPooling2D(new PoolSize(2,2)));
        n1.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));
        n1.add(new MaxPooling2D(new PoolSize(2,2)));
        n1.add(new Conv2D(72,new KernelSize(10,10),new Strides(1,1),"same"));

        n2.add(new Input(48,32,10));
        n2.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));
        n2.add(new MaxPooling2D(new PoolSize(2,2)));
        n2.add(new Conv2D(64,new KernelSize(5,5),new Strides(1,1),"same"));
        n2.add(new MaxPooling2D(new PoolSize(2,2)));
        n2.add(new Conv2D(72,new KernelSize(10,10),new Strides(1,1),"same"));

        n3.add(new Dense(200));
        n3.add(new Dense(300));
        n3.add(new Dense(400));
        n3.add(new Dense(500));

        model.add(n3);
        model.add(n1,n3);
        model.add(n2,n3);
