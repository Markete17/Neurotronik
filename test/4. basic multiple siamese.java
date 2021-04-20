Node n1=new Node(layerController);
        Node n2=new Node(layerController);
        Node n3=new Node(layerController);
        Node n4=new Node(layerController);
        Node n5=new Node(layerController);
        Node np=new Node(layerController);

        n1.add(new Input(48,32,10));
        n1.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        n1.add(new MaxPooling2D(new Tuple(2,2)));
        n1.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        n1.add(new MaxPooling2D(new Tuple(2,2)));
        n1.add(new Conv2D(72,new Tuple(10,10),new Tuple(1,1),"same"));

        n2.add(new Input(48,32,10));
        n2.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        n2.add(new MaxPooling2D(new Tuple(2,2)));
        n2.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        n2.add(new MaxPooling2D(new Tuple(2,2)));
        n2.add(new Conv2D(72,new Tuple(10,10),new Tuple(1,1),"same"));

        n3.add(new Input(48,32,10));
        n3.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        n3.add(new MaxPooling2D(new Tuple(2,2)));
        n3.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        n3.add(new MaxPooling2D(new Tuple(2,2)));
        n3.add(new Conv2D(72,new Tuple(10,10),new Tuple(1,1),"same"));

        n4.add(new Input(48,32,10));
        n4.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        n4.add(new MaxPooling2D(new Tuple(2,2)));
        n4.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        n4.add(new MaxPooling2D(new Tuple(2,2)));
        n4.add(new Conv2D(72,new Tuple(10,10),new Tuple(1,1),"same"));

        n5.add(new Input(48,32,10));
        n5.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        n5.add(new MaxPooling2D(new Tuple(2,2)));
        n5.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        n5.add(new MaxPooling2D(new Tuple(2,2)));
        n5.add(new Conv2D(72,new Tuple(10,10),new Tuple(1,1),"same"));

        np.add(new Dense(200));
        np.add(new Dense(300));
        np.add(new Dense(400));
        np.add(new Dense(500));

        model.add(np);
        model.add(n1,np);
        model.add(n2,np);
        model.add(n3,np);
        model.add(n4,np);
        model.add(n5,np);
