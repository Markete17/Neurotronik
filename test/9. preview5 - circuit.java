                        Node x1a = new Node();
                        Node x1b = new Node();
                        Node x1 = new Node();
                        Node x2 = new Node();
                        Node x3 = new Node();
                        Node x4 = new Node();
                        Node xp1 = new Node();
                        Node xp2 = new Node();
                        Node xp3 = new Node();
                        Node x5 = new Node();
                        Node x6 = new Node();
                        Node aux = new Node();
                        Node xp4 = new Node();
                        Node xp5 = new Node();

                        x1a.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
                        x1a.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x1a.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        x1b.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
                        x1b.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x1b.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        x2.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
                        x2.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x2.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x2.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        x3.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
                        x3.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x3.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x3.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        x4.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
                        x4.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x4.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x4.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        x5.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
                        x5.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x5.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x5.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        x6.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
                        x6.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x6.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x6.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        x1.add(layers.concatenate(x1a, x1b));
                        layers.MaxPooling2D(new Tuple(2, 2));
                        x1.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        xp1.add(layers.concatenate(x1, x2));
                        xp1.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        xp2.add(layers.concatenate(x3, x4));
                        xp2.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        xp4.add(layers.concatenate(x2, x3));
                        xp4.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        xp5.add(layers.concatenate(x4, x5));
                        xp5.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

                        aux.add(layers.concatenate(x5,x6));

                        layers.setDenseLayer(true);
                        xp3.add(layers.concatenate(xp1, xp2,aux,xp4,xp5));
                        xp3.add(layers.Dense(200));
                        xp3.add(layers.Dense(200));
                        xp3.add(layers.Dense(200));

                        //Create Tree
                        model.add(xp3);
                        model.add(x1a, x1);
                        model.add(x1b, x1);
                        model.add(x1, xp1);
                        model.add(x2, xp1);
                        model.add(x2,xp4);
                        model.add(x3,xp4);
                        model.add(x4,xp5);
                        model.add(x5,xp5);
                        model.add(x3, xp2);
                        model.add(x4, xp2);
                        model.add(x5,aux);
                        model.add(x6,aux);
                        model.add(xp1, xp3);
                        model.add(xp2, xp3);
                        model.add(xp4,xp3);
                        model.add(xp5,xp3);
                        model.add(aux,xp3);