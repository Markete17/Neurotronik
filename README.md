# Neurotronik - Marcos Ruiz Muñoz 📄

## Index ✒️
1. [Description 🚀](#description)
2. [Setting-Up ⚙️](#setup)
3. [Settings & Functions 📌](#functions)
4. [Export image 📁](#image)

<a name="description"></a>
## 1. Description 🚀

<p align="justify">
  Neurotronik is a Java library for creating representations of neural networks of all kinds. It has a user interface with which you can edit and configure everything. The rendering image is exported into an SVG image file. Practice, create and edit the representation of your neural network!
</p>

<hr>

<a name="setup"></a>
## 2. Setting-Up ⚙️

### Developtment Enviorement

<p align="justify">
  To use this application, you need a development environment that can open and run Java projects. Recommended programming environments are: <a href="https://www.jetbrains.com/es-es/idea/download/#section=windows" title="Download IntelliJ">IntelliJ</a>, <a href="https://netbeans.apache.org/download/nb122/nb122.html" title="Download NetBeans">NetBeans</a>, or <a href="https://www.eclipse.org/downloads/" title="Install Eclipse">Eclipse</a>. If you already have an IDE, you can skip this step.
</p>

### Clone the repository

<p align="justify">
  To have the application, you need to clone this GitHub repository. To do this, you must open the console of your device, go to the folder where you want to have the project and execute the following Git command:

<pre><code><p align="center">$ git clone https://github.com/Markete17/Neurotronik</p></code></pre>
<hr>
Also, without using Git, you can download the project through the option offered by GitHub.
</p>

### Use the library and generate an SVG file through the command line
<p align="justify">
This section will detail all the steps to be able to generate the representation of a simple neural architecture through the command line.
</p>
- <b>Step 1:</b> Create the file "MinimalExample.java" (There are other examples in the Examples folder of the repository):
    <pre><code>
import data.*;
import models.*;
import layers.*;
import drawing.*;
import settings.DrawSettings;
import tree.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class MinimalExample {

    public static void main(String[] args) throws Exception {
        DrawSettings drawSettings = new DrawSettings();
        Model model = new Model();
        LayerController layerController = new LayerController(drawSettings);

        //-------EDIT YOUR NEURAL NETWORK-------
		Node n1 = new Node(layerController);

		n1.add(new Input(48, 32, 10));
		n1.add(new Conv2D(32, new KernelSize(10, 10), new Strides(1, 1), "same"));
		n1.add(new MaxPooling2D(new PoolSize(2, 2)));
		n1.add(new Conv2D(72, new KernelSize(10, 10), new Strides(1, 1), "same"));
		n1.add(new Dense(100));
		n1.add(new Dense(200));
		model.add(n1);
        //----------------------------------------

        SvgController svgController = new SvgController(drawSettings);

        BufferedWriter bw = new BufferedWriter(new FileWriter("output.svg"));
        bw.write(svgController.draw(model));
        bw.close();
    }
}
    </code></pre>
    
 - <b>Step 2:</b> Download the library "Neurotronik.jar":
 <pre><code><p align="center">$ wget https://bit.ly/3yFwHae</p></code></pre>
 - <b>Step 3:</b> Compile the Java class by adding the library:
  <pre><code><p align="center">$ javac −cp ./Neurotronik.jar MinimalExample.java</p></code></pre>
 - <b>Step 4:</b> Run the newly compiled program:
  <pre><code><p align="center">$ java −cp ./Neurotronik.jar: MinimalExample</p></code></pre>
 - <b>Step 5:</b> An svg file is generated that can be opened with your favorite browser, for example with chrome:
   <pre><code><p align="center">$ chromium ./output.svg</p></code></pre>
 

<a name="functions"></a>
## 3. Settings & Functions 📌 
1. [Settings](#settings)
2. [Declarations](#declarations)
3. [Input](#input)
4. [Conv2D](#conv2d)
5. [Deconv2D](#deconv2d)
6. [MaxPooling2D](#maxpooling2d)
7. [Dense](#dense)
8. [Concatenate](#concatenate)
9. [AddShortcut](#shortcut)

<p align="justify">
  To edit the neuron network representation, you have to edit the NeuralNetworkConsole class in the run() method.
</p>
  
<a name="settings"></a>  

### 1. Settings

<p align="justify">
To configure the project, you must edit the Neural Network Console class. You need to initialize the Layer Controller class, the model (Model class) and the neural representation style settings(DrawSettings class). You can use the default style settings or you can modify the DrawSettings.java class and edit the style of your neural network. The classes in the DrawSettings.java class that you can modify are as follows:
</p>
<ul>
 <li><b>Color:</b> You will be able to edit the color of all the different layers of the neural network.</li>
  <li><b>Font:</b> You can change the size and font.</li>
 <li><b>Alfa:</b> You can rotate the neural network on all axes.</li>
 <li><b>Displacement:</b> You can change the distance between the nodes <b>(Nodes Distance)</b> or the distance between the different layers <b>(Layers Distance)</b> or also, the distance between the parent node and the child node <b>(Parent Distance)</b>.</li>
  <li><b>ViewBox:</b> To modify the size of the generated image and zoom.</li>
  <li><b>Stroke:</b> To change the width, color, and opacity of the image's stroke.</li>
  <li> I addition, you can show or hide the kernel and layer dimensions with the boolean variables "activateLayerDimensions" and "activateKernelDimensions" respectively. If they are initialized to "true", they are displayed and to "false", otherwise.</li>
  <li>Logarithms in width and depth dimensions to scale the image, can be enabled or disabled with the Boolean variables "activateWidhtLogs" and "activateDepthLogs" respectively. If they are set to "true", they are set and "false", otherwise.</li>
 </ul>
  <pre><code>
  DrawSettings drawSettings = new DrawSettings();
  Model model = new Model();
  LayerController layerController = new LayerController(drawSettings);
  </code></pre>

<a name="declarations"></a> 

### 2. Declarations

<p align="justify">
  Create the nodes you want with the following function.
</p>
  <pre><code>Node n1 = new Node(layerController);</code></pre>

<a name="input"></a>         
### 3. Input
<p align="justify">
  Create the input layer with the dimensions you want and add it to a node.
</p>
<pre><code>n1.add(new Input(48,32,10));</code></pre>
<p align="center">
  <a href="https://imgbb.com/"><img src="https://i.ibb.co/PxX6MRn/Captura.png" alt="Input" border="0"></a>
</p>

<a name="conv2d"></a> 
### 4. Conv2D
<p align="justify">
  Add a convolution layer to the node with the Conv2D function that has these arguments:
</p>
<ul>
<li><b>Filters:</b> the next Z dimension</li>
<li><b>Kernel Size:</b> x and y dimension of the kernel</li>
<li><b>Strides:</b> An integer or tuple / list of 2 integers, specifying the steps of the convolution along the height and width.</li>
  <li><b>Padding:</b> one of <b>"valid"</b> or <b>"same"</b> (case-insensitive). "valid" means no padding. "same" results in padding with zeros evenly to the left/right or up/down of the input such that output has the same height/width dimension as the input.</li>
  </ul>
<pre><code>n1.add(new Input(48,32,10));
n1.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same"));</code></pre>
<p align="justify">
  You can also create the input layer in this function.
</p>
<pre><code>n1.add(new Conv2D(32,new KernelSize(10,10),new Strides(1,1),"same",new Input(48,32,10)));</code></pre>
<p align="center">
  <a href="https://imgbb.com/"><img src="https://i.ibb.co/rtc34KW/Captura.png" alt="Conv2D" border="0"></a>
</p>

<a name="deconv2d"></a> 
### 5. Deconv2D
<p align="justify">
  Add a deconvolution layer to the node with the Deconv2D function that has these arguments:
</p>
<ul>
<li><b>Filters:</b> the next Z dimension</li>
<li><b>Kernel Size:</b> x and y dimension of the kernel</li>
<li><b>Strides:</b> An integer or tuple / list of 2 integers, specifying the steps of the convolution along the height and width.</li>
  <li><b>Padding:</b> one of <b>"valid"</b> or <b>"same"</b> (case-insensitive). "valid" means no padding. "same" results in padding with zeros evenly to the left/right or up/down of the input such that output has the same height/width dimension as the input.</li>
  </ul>
<pre><code>n1.add(new Input(32,32,20));
n1.add(new Deconv2D(48,new KernelSize(10,10),new Strides(2,2),"same"));</code></pre>
<p align="justify">
  You can also create the input layer in this function.
</p>
<pre><code>n1.add(new Deconv2D(48,new KernelSize(10,10),new Strides(2,2),"same",new Input(32,32,20)));</code></pre>
<p align="center">
  <a href="https://imgbb.com/"><img src="https://i.ibb.co/B27C6Lj/Captura.png" alt="Captura" border="0"></a>
</p>

<a name="maxpooling2d"></a> 
### 6. MaxPooling2D
<p align="justify">
 Operation to decrease the 2D size of the neural network node.
</p>
<pre><code>n1.add(new Input(48,32,10));
n1.add(new Conv2D(32,new KernelSize(10,10), new Strides(1,1), "same"));
n1.add(new MaxPooling2D(new PoolSize(2,2)));
n1.add(new Conv2D(64,new KernelSize(5,5), new Strides(1,1), "same"));</code></pre>
<p align="center">
  <a href="https://imgbb.com/"><img src="https://i.ibb.co/gV2zRch/Captura.png" alt="MaxPooling2D" border="0"></a>
</p>

<a name="dense"></a>  
### 6. Dense
<p align="justify">
 Create a dense layer with the following function.
</p>
<pre><code>n1.add(new Dense(100));
n1.add(new Dense(200));</code></pre>
<p align="center">
  <a href="https://imgbb.com/"><img src="https://i.ibb.co/TRjwzLW/Captura.png" alt="Dense" border="0"></a>
</p>

<a name="concatenate"></a> 
### 7. Concatenate
<p align="justify">
 Concatenate Neural Network Nodes.
</p>
<pre><code>x1a.add(new Input(32,32,20));
x1a.add(new Conv2D(32,new KernelSize(10,10), new Strides(1,1), "same"));
x1b.add(new Input(32,32,20));
x1b.add(new Conv2D(32,new KernelSize(10,10), new Strides(1,1), "same"));
x1.add(new Concatenate(x1a, x1b));
xp3.add(new Dense(100));</code></pre>

<p align="justify">
Remember that you must also define it in the model.
</p>

<pre><code>model.add(xp3);
model.add(x1a,x1);
model.add(x1b,x1);
model.add(x1,xp3);</code></pre>

<p align="center">
  <a href="https://imgbb.com/"><img src="https://i.ibb.co/VHdybJ2/Captura.png" alt="Concatenate" border="0"></a>
</p>

<a name="shortcut"></a> 
### 8. Add Shortcut
<p align="justify">
 You can join any layer of any node to each other.
</p>
<pre><code>n1.add(new Input(48,32,10));
n4=n1.add(new Conv2D(32,new KernelSize(10,10), new Strides(1,1), "same"));
n6=n1.add(new Conv2D(64,new KernelSize(5,5), new Strides(1,1), "same"));
n7=n1.add(new Conv2D(72,new KernelSize(10,10), new Strides(1,1), "same"));
n2.add(new Input(48,32,10));
n5=n2.add(new Conv2D(32,new KernelSize(10,10), new Strides(1,1), "same"));
n8=n2.add(new Conv2D(64,new KernelSize(5,5), new Strides(1,1), "same"));
n9=n2.add(new Conv2D(72,new KernelSize(10,10), new Strides(1,1), "same"));
n3.add(new Dense(150));
n3.add(new Dense(150));</code></pre>

<p align="justify">
In the model:
</p>

<pre><code>model.add(n3);
model.add(n1,n3);
model.add(n2,n3);
model.addShortcut(n4,n5);
model.addShortcut(n6,n7);
model.addShortcut(n8,n9);
</code></pre>

<p align="center">
  <a href="https://ibb.co/F6SJsBv"><img src="https://i.ibb.co/rkCMbZP/Captura.png" alt="Shortcut" border="0"></a>
</p>

<a name="image"></a>
## 4. Export image 📁
<p align="justify">
When you run the application, an image file in SVG format is generated that contains the representation you have defined. This file is found in the src directory of the project folder. Although, this path can be edited in the NeuralNetworkConsole class so that the image is exprotected in the folder you want.
</p>
