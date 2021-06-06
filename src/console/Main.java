package console;

import exceptions.ConsoleException;

public class Main {

    /*****************************
     ***NEUROTRONIK APPLICATION***
     ****************************/

    public static void main(String[] args) throws ConsoleException {
        NeuralNetworkConsole neurotronik = new NeuralNetworkConsole();
        neurotronik.run();
    }
}
