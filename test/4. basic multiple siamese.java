Node n1=new Node();
Node n2=new Node();
Node n3=new Node();
Node n4=new Node();
Node n5=new Node();
Node np=new Node();

n1.add(layers.Input(new Cube(new Coordinate(48,32,10),drawSettings)));
n1.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
layers.MaxPooling2D(new Tuple(2,2));
n1.add(layers.Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
layers.MaxPooling2D(new Tuple(2,2));
n1.add(layers.Conv2D(72,new Tuple(10,10),new Tuple(1,1),"same"));

n2.add(layers.Input(new Cube(new Coordinate(48,32,10),drawSettings)));
n2.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
layers.MaxPooling2D(new Tuple(2,2));
n2.add(layers.Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
layers.MaxPooling2D(new Tuple(2,2));
n2.add(layers.Conv2D(72,new Tuple(10,10),new Tuple(1,1),"same"));

n3.add(layers.Input(new Cube(new Coordinate(48,32,10),drawSettings)));
n3.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
layers.MaxPooling2D(new Tuple(2,2));
n3.add(layers.Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
layers.MaxPooling2D(new Tuple(2,2));
n3.add(layers.Conv2D(72,new Tuple(10,10),new Tuple(1,1),"same"));

n4.add(layers.Input(new Cube(new Coordinate(48,32,10),drawSettings)));
n4.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
layers.MaxPooling2D(new Tuple(2,2));
n4.add(layers.Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
layers.MaxPooling2D(new Tuple(2,2));
n4.add(layers.Conv2D(72,new Tuple(10,10),new Tuple(1,1),"same"));

n5.add(layers.Input(new Cube(new Coordinate(48,32,10),drawSettings)));
n5.add(layers.Conv2D(32, new Tuple(10, 10), new Tuple(1, 1), "same"));
layers.MaxPooling2D(new Tuple(2,2));
n5.add(layers.Conv2D(64,new Tuple(5,5),new Tuple(1,1),"same"));
layers.MaxPooling2D(new Tuple(2,2));
n5.add(layers.Conv2D(72,new Tuple(10,10),new Tuple(1,1),"same"));

layers.setDenseLayer(true);
np.add(layers.concatenate(np,n1,n2,n3,n4,n5));
np.add(layers.Dense(200));
np.add(layers.Dense(300));
np.add(layers.Dense(400));
np.add(layers.Dense(500));

model.add(np);
model.add(n1,np);
model.add(n2,np);
model.add(n3,np);
model.add(n4,np);
model.add(n5,np);
