        Node x1a = new Node(layerController);
        Node x1b = new Node(layerController);
        Node x1 = new Node(layerController);
        Node x2 = new Node(layerController);
        Node x3 = new Node(layerController);
        Node x4 = new Node(layerController);
        Node xp1 = new Node(layerController);
        Node xp2 = new Node(layerController);
        Node xp3 = new Node(layerController);
        Node x5 = new Node(layerController);
        Node x6 = new Node(layerController);
        Node aux = new Node(layerController);
        Node xp4 = new Node(layerController);
        Node xp5 = new Node(layerController);

        Node x1az = new Node(layerController);
        Node x1bz = new Node(layerController);
        Node x1z = new Node(layerController);
        Node x2z = new Node(layerController);
        Node x3z = new Node(layerController);
        Node x4z = new Node(layerController);
        Node xp1z = new Node(layerController);
        Node xp2z = new Node(layerController);
        Node xp3z = new Node(layerController);
        Node x5z = new Node(layerController);
        Node x6z = new Node(layerController);
        Node auxz = new Node(layerController);
        Node xp4z = new Node(layerController);
        Node xp5z = new Node(layerController);

        Node parent = new Node(layerController);


        x1a.add(new Input(32,32,20));
        x1a.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x1a.add(new MaxPooling2D(new Tuple(2,2)));
        x1a.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x1b.add(new Input(32,32,20));
        x1b.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x1b.add(new MaxPooling2D(new Tuple(2,2)));
        x1b.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x2.add(new Input(32,32,20));
        x2.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x2.add(new MaxPooling2D(new Tuple(2,2)));
        x2.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        x2.add(new MaxPooling2D(new Tuple(2,2)));
        x2.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x3.add(new Input(32,32,20));
        x3.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x3.add(new MaxPooling2D(new Tuple(2,2)));
        x3.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        x3.add(new MaxPooling2D(new Tuple(2,2)));
        x3.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x4.add(new Input(32,32,20));
        x4.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x4.add(new MaxPooling2D(new Tuple(2,2)));
        x4.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        x4.add(new MaxPooling2D(new Tuple(2,2)));
        x4.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x5.add(new Input(32,32,20));
        x5.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x5.add(new MaxPooling2D(new Tuple(2,2)));
        x5.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        x5.add(new MaxPooling2D(new Tuple(2,2)));
        x5.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x6.add(new Input(32,32,20));
        x6.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x6.add(new MaxPooling2D(new Tuple(2,2)));
        x6.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        x6.add(new MaxPooling2D(new Tuple(2,2)));
        x6.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x1.add(new Concatenate(x1a,x1b));
        x1.add(new MaxPooling2D(new Tuple(2,2)));
        x1.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        xp1.add(new Concatenate(x1, x2));
        xp1.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        xp1.add(new Dense(100));
        xp1.add(new Dense(200));

        xp2.add(new Concatenate(x2, x3));
        xp2.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        xp2.add(new Dense(100));
        xp2.add(new Dense(200));

        xp4.add(new Concatenate(x3, x4));
        xp4.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        xp4.add(new Dense(100));
        xp4.add(new Dense(200));

        xp5.add(new Concatenate(x2, x3));
        xp5.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        xp5.add(new Dense(100));
        xp5.add(new Dense(200));

        aux.add(new Concatenate(x5, x6));
        aux.add(new MaxPooling2D(new Tuple(2,2)));
        aux.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        xp3.add(new Dense(200));
        xp3.add(new Dense(400));
        xp3.add(new Dense(600));
        xp3.add(new Dense(800));

        x1az.add(new Input(32,32,20));
        x1az.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x1az.add(new MaxPooling2D(new Tuple(2,2)));
        x1az.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x1bz.add(new Input(32,32,20));
        x1bz.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x1bz.add(new MaxPooling2D(new Tuple(2,2)));
        x1bz.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x2z.add(new Input(32,32,20));
        x2z.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x2z.add(new MaxPooling2D(new Tuple(2,2)));
        x2z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        x2z.add(new MaxPooling2D(new Tuple(2,2)));
        x2z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x3z.add(new Input(32,32,20));
        x3z.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x3z.add(new MaxPooling2D(new Tuple(2,2)));
        x3z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        x3z.add(new MaxPooling2D(new Tuple(2,2)));
        x3z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x4z.add(new Input(32,32,20));
        x4z.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x4z.add(new MaxPooling2D(new Tuple(2,2)));
        x4z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        x4z.add(new MaxPooling2D(new Tuple(2,2)));
        x4z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x5z.add(new Input(32,32,20));
        x5z.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x5z.add(new MaxPooling2D(new Tuple(2,2)));
        x5z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        x5z.add(new MaxPooling2D(new Tuple(2,2)));
        x5z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x6z.add(new Input(32,32,20));
        x6z.add(new Conv2D(32,new Tuple(10,10),new Tuple(1,1),"same"));
        x6z.add(new MaxPooling2D(new Tuple(2,2)));
        x6z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        x6z.add(new MaxPooling2D(new Tuple(2,2)));
        x6z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        x1z.add(new Concatenate(x1az,x1bz));
        x1z.add(new MaxPooling2D(new Tuple(2,2)));
        x1z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        xp1z.add(new Concatenate(x1z, x2z));
        xp1z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        xp1z.add(new Dense(100));
        xp1z.add(new Dense(200));

        xp2z.add(new Concatenate(x2z, x3z));
        xp2z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        xp2z.add(new Dense(100));
        xp2z.add(new Dense(200));

        xp4z.add(new Concatenate(x3z, x4z));
        xp4z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        xp4z.add(new Dense(100));
        xp4z.add(new Dense(200));

        xp5z.add(new Concatenate(x2z, x3z));
        xp5z.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
        xp5z.add(new Dense(100));
        xp5z.add(new Dense(200));

        auxz.add(new Concatenate(x5z, x6z));
        auxz.add(new MaxPooling2D(new Tuple(2,2)));
        auxz.add(new Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));

        xp3z.add(new Dense(200));
        xp3z.add(new Dense(400));
        xp3z.add(new Dense(600));
        xp3z.add(new Dense(800));

        parent.add(new Dense(200));
        parent.add(new Dense(400));
        parent.add(new Dense(600));
        parent.add(new Dense(800));

        //Create Tree
        model.add(parent);
        model.add(x1a, x1);
        model.add(x1b, x1);
        model.add(x1, xp1);
        model.add(x2, xp1);
        model.add(x2, xp4);
        model.add(x3, xp4);
        model.add(x4, xp5);
        model.add(x5, xp5);
        model.add(x3, xp2);
        model.add(x4, xp2);
        model.add(x5, aux);
        model.add(x6, aux);
        model.add(xp1, xp3);
        model.add(xp2, xp3);
        model.add(xp4, xp3);
        model.add(aux, xp3);
        model.add(xp5, xp3);

        model.add(x1az, x1z);
        model.add(x1bz, x1z);
        model.add(x1z, xp1z);
        model.add(x2z, xp1z);
        model.add(x2z, xp4z);
        model.add(x3z, xp4z);
        model.add(x4z, xp5z);
        model.add(x5z, xp5z);
        model.add(x3z, xp2z);
        model.add(x4z, xp2z);
        model.add(x5z, auxz);
        model.add(x6z, auxz);
        model.add(xp1z, xp3z);
        model.add(xp2z, xp3z);
        model.add(xp4z, xp3z);
        model.add(auxz, xp3z);
        model.add(xp5z, xp3z);

        model.add(xp3,parent);
        model.add(xp3z,parent);