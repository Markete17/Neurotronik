Node n1=new Node();
Node n2=new Node();
Node n3=new Node();

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

n3.add(layers.concatenate(0,n1,n2));
n3.add(layers.Dense(200));
n3.add(layers.Dense(300));
n3.add(layers.Dense(400));
n3.add(layers.Dense(500));

model.add(n3);
model.add(n1,n3);
model.add(n2,n3);
