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

            Node x1az = new Node();
            Node x1bz = new Node();
            Node x1z = new Node();
            Node x2z = new Node();
            Node x3z = new Node();
            Node x4z = new Node();
            Node xp1z = new Node();
            Node xp2z = new Node();
            Node xp3z = new Node();
            Node x5z = new Node();
            Node x6z = new Node();
            Node auxz = new Node();
            Node xp4z = new Node();
            Node xp5z = new Node();

            Node parent = new Node();

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

            x1.add(layers.concatenate(x1, x1a, x1b));
            layers.MaxPooling2D(new Tuple(2, 2));
            x1.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            xp1.add(layers.concatenate(xp1, x1, x2));
            xp1.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            xp1.add(layers.Dense(100));
            xp1.add(layers.Dense(200));

            xp2.add(layers.concatenate(xp2, x3, x4));
            xp2.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            xp2.add(layers.Dense(100));
            xp2.add(layers.Dense(200));

            xp4.add(layers.concatenate(xp4, x2, x3));
            xp4.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            xp4.add(layers.Dense(100));
            xp4.add(layers.Dense(200));

            xp5.add(layers.concatenate(xp5, x4, x5));
            xp5.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            xp5.add(layers.Dense(100));
            xp5.add(layers.Dense(200));

            aux.add(layers.concatenate(aux, x5, x6));
            aux.add(layers.Dense(100));
            aux.add(layers.Dense(200));

            layers.setDenseLayer(true);
            xp3.add(layers.concatenate(xp3, xp1, xp2, aux, xp4, xp5));
            xp3.add(layers.Dense(200));
            xp3.add(layers.Dense(200));

            layers.setDenseLayer(false);

            x1az.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
            x1az.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x1az.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            x1bz.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
            x1bz.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x1bz.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            x2z.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
            x2z.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x2z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x2z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            x3z.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
            x3z.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x3z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x3z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            x4z.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
            x4z.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x4z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x4z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            x5z.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
            x5z.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x5z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x5z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            x6z.add(layers.Input(new Cube(new Coordinate(32, 32, 20), drawSettings)));
            x6z.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x6z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            layers.MaxPooling2D(new Tuple(2, 2));
            x6z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            x1z.add(layers.concatenate(x1z, x1az, x1bz));
            layers.MaxPooling2D(new Tuple(2, 2));
            x1z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));

            xp1z.add(layers.concatenate(xp1z, x1z, x2z));
            xp1z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            xp1z.add(layers.Dense(100));
            xp1z.add(layers.Dense(200));

            xp2z.add(layers.concatenate(xp2z, x3z, x4z));
            xp2z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            xp2z.add(layers.Dense(100));
            xp2z.add(layers.Dense(200));

            xp4z.add(layers.concatenate(xp4z, x2z, x3z));
            xp4z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            xp4z.add(layers.Dense(100));
            xp4z.add(layers.Dense(200));

            xp5z.add(layers.concatenate(xp5z, x4z, x5z));
            xp5z.add(layers.Conv2D(64, new Tuple(5, 5), new Tuple(1, 1), "same"));
            xp5z.add(layers.Dense(100));
            xp5z.add(layers.Dense(200));

            auxz.add(layers.concatenate(auxz, x5z, x6z));
            auxz.add(layers.Dense(100));
            auxz.add(layers.Dense(200));

            layers.setDenseLayer(true);
            xp3z.add(layers.concatenate(xp3z, xp1z, xp2z, auxz, xp4z, xp5z));
            xp3z.add(layers.Dense(200));
            xp3z.add(layers.Dense(200));

            parent.add(layers.Dense(200));
            parent.add(layers.Dense(400));
            parent.add(layers.Dense(600));
            parent.add(layers.Dense(800));


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

            model.add(xp3, parent);
            model.add(aux, parent);
            model.add(xp1z, parent);
            model.add(xp4, parent);
            model.add(xp5, parent);
            model.add(xp2z, parent);
            model.add(xp3z, parent);