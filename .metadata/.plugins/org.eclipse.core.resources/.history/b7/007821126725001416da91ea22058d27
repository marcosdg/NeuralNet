package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Connection;
import core.InputNode;
import core.Layer;
import core.NeuralNetwork;
import core.Neuron;
import core.Node;
import core.activation.Linear;
import core.learning.Backpropagation;
import core.learning.LearningRule;
import core.propagation.WeightedSum;

public class TestBasics {
	public static void main(String[] args) {

/* -------------------------------- NEURAL NETWORK SCHEME -------------------------

	1 Input Data Layer
	1 Initial Layer
	0 Hidden Layers
	1 Output Layer


   INPUT DATA LAYER                  INITIAL LAYER                 OUTPUT LAYER

        Input Node 1 ------------->   +++++++++++++
             Bias 1  ------------->   +  NEURON 1 + --------\
                                      +++++++++++++          \
                                                              \    +++++++++++++
                                                               \   +  NEURON 3 + --------> output
                                                               /   +++++++++++++
                                                              /  /
        Input Node 2 ------------->   +++++++++++++          /  /
              Bias 2 ------------->   +  NEURON 2 +  -------/  /
                                      +++++++++++++	          /
                                                             /
                                                            /
                                                           /
                                                          /
              Bias 3 ------------------------------------/

*/

// Randomizer

		Random generator = new Random();

// Synaptic functions.

		// Propagation.

		WeightedSum weightedSum = new WeightedSum();

		// Activation.

		double slope = 2;
		Linear linear = new Linear(slope);

// Empty layers.

		// Data.

		Layer input_data_layer = new Layer(Layer.INPUT_DATA_LAYER);

		// Initial.

		Layer initial_layer = new Layer(Layer.INITIAL_LAYER);

		// Output.

		Layer output_layer = new Layer(Layer.OUTPUT_LAYER);

		// Container.

		List<Layer> layers = new ArrayList<Layer>();

// Nodes.

		// Input Nodes.

		InputNode input_node1 = new InputNode(input_data_layer, "Input Node 1",
		                                      InputNode.INPUT_DATA_NODE());
		InputNode input_node2 = new InputNode(input_data_layer, "Input Node 2",
                                              InputNode.INPUT_DATA_NODE());

		input_node1.setInputData(2);
		input_node2.setInputData(2);

		InputNode bias1 = new InputNode(input_data_layer, "Bias for Neuron 1",
                                         InputNode.BIAS_NODE());
		InputNode bias2 = new InputNode(input_data_layer, "Bias for Neuron 2",
                                         InputNode.BIAS_NODE());
		InputNode bias3 = new InputNode(input_data_layer, "Bias for Neuron 3",
                                         InputNode.BIAS_NODE());
		bias1.setInputData(1);
		bias2.setInputData(1);
		bias3.setInputData(1);


		// Neurons.

		Neuron neuron1 = new Neuron(weightedSum, linear, initial_layer, "Neuron 1");
		Neuron neuron2 = new Neuron(weightedSum, linear, initial_layer, "Neuron 2");
		Neuron neuron3 = new Neuron(weightedSum, linear, output_layer, "Neuron 3");

// Layers setup.

		// Input Data layer.

		input_data_layer.addNode(input_node1);
		input_data_layer.addNode(input_node2);
		input_data_layer.addNode(bias1);
		input_data_layer.addNode(bias2);
		input_data_layer.addNode(bias3);

		// Initial layer.

		initial_layer.addNode(neuron1);
		initial_layer.addNode(neuron2);

		output_layer.addNode(neuron3);

		layers.add(input_data_layer);
		layers.add(initial_layer);
		layers.add(output_layer);


// NeuralNetwork setup.

		LearningRule learning_rule = new Backpropagation(); // Not tested now.
		NeuralNetwork neural_network = new NeuralNetwork(layers, learning_rule, "Neural Network");

		// Connections.

		neural_network.createConnection(input_node1, neuron1, 1, "Connection: InputNode1 -> Neuron1");
		neural_network.createConnection(input_node2, neuron2, 1, "Connection: InputNode2 -> Neuron1");

		neural_network.createConnection(bias1, neuron1, -1, "Connection: Bias1 -> Neuron1");
		neural_network.createConnection(bias2, neuron2, -1, "Connection: Bias2 -> Neuron2");
		neural_network.createConnection(bias3, neuron3, -1, "Connection: Bias3 -> Neuron3");

		neural_network.createConnection(neuron1, neuron3, 1, "Connection: Neuron1 -> Neuron3");
		neural_network.createConnection(neuron2, neuron3, 1, "Connection: Neuron2 -> Neuron3");

// ---------------------------------------- TEST -------------------------------------

System.out.println("=============[ TEST 1: GENERAL INFO ABOUT LAYERS ]==================");

		System.out.println("------- LAYER 1 --------");
		System.out.println(" Kind: " + input_data_layer.getKind());
		System.out.println(" Number of Nodes: " + input_data_layer.numberOfNodes());
		System.out.println(" is Input Data Layer? " + input_data_layer.isInputDataLayer());
		System.out.println(" is Initial Layer? " + input_data_layer.isInitialLayer());
		System.out.println(" is Hidden Layer? " + input_data_layer.isHiddenLayer());
		System.out.println(" is Output Layer? " + input_data_layer.isOutputLayer());

		System.out.println(" Nodes: ");
		for (Node node: input_data_layer.getNodes()) {
			System.out.println("\t" + node.getLabel());
		}

		System.out.println();
		System.out.println("------- LAYER 2 --------");
		System.out.println(" Kind: " + initial_layer.getKind());
		System.out.println(" Number of Nodes: " + initial_layer.numberOfNodes());
		System.out.println(" is Input Data Layer? " + initial_layer.isInputDataLayer());
		System.out.println(" is Initial Layer? " + initial_layer.isInitialLayer());
		System.out.println(" is Hidden Layer? " + initial_layer.isHiddenLayer());
		System.out.println(" is Output Layer? " + initial_layer.isOutputLayer());

		System.out.println(" Nodes: ");
		for (Node node: initial_layer.getNodes()) {
			System.out.println("\t" + node.getLabel());
		}

		System.out.println();
		System.out.println("------- LAYER 3 --------");
		System.out.println(" Kind: " + output_layer.getKind());
		System.out.println(" Number of Nodes: " + output_layer.numberOfNodes());
		System.out.println(" is Input Data Layer? " + output_layer.isInputDataLayer());
		System.out.println(" is Initial Layer? " + output_layer.isInitialLayer());
		System.out.println(" is Hidden Layer? " + output_layer.isHiddenLayer());
		System.out.println(" is Output Layer? " + output_layer.isOutputLayer());

		System.out.println(" Nodes: ");
		for (Node node: output_layer.getNodes()) {
			System.out.println("\t" + node.getLabel());
		}

System.out.println();
System.out.println("=============[ TEST 2: INPUT DATA/BIASES ]==================");

		System.out.println("------- INPUT NODE 1 --------");
		System.out.println(" Parent Layer: " + input_node1.getParentLayer().getKind());
		System.out.println(" Input Data: " + input_node1.getInputData());
		System.out.println(" Output: " + input_node1.getOutput());
		System.out.println(" has output to Neuron 1? " + input_node1.hasOutputTo(neuron1));
		System.out.println(" has output to Neuron 2? " + input_node1.hasOutputTo(neuron2));
		System.out.println(" has output to Neuron 3? " + input_node1.hasOutputTo(neuron3));

		System.out.println(" Output Connections: ");
		for (Connection output_connection: input_node1.getOutputConnections()) {
			System.out.println("\t" + output_connection.getLabel());
		}

		System.out.println();
		System.out.println("------- INPUT NODE 2 --------");
		System.out.println(" Parent Layer: " + input_node2.getParentLayer().getKind());
		System.out.println(" Input Data: " + input_node2.getInputData());
		System.out.println(" Output: " + input_node2.getOutput());
		System.out.println(" has output to Neuron 1? " + input_node2.hasOutputTo(neuron1));
		System.out.println(" has output to Neuron 2? " + input_node2.hasOutputTo(neuron2));
		System.out.println(" has output to Neuron 3? " + input_node2.hasOutputTo(neuron3));

		System.out.println(" Output Connections: ");
		for (Connection output_connection: input_node2.getOutputConnections()) {
			System.out.println("\t" + output_connection.getLabel());
		}

		System.out.println();
		System.out.println("-------- BIAS 1 ---------");
		System.out.println(" Parent Layer: " + bias1.getParentLayer().getKind());
		System.out.println(" Input Data: " + bias1.getInputData());
		System.out.println(" Output: " + bias1.getOutput());
		System.out.println(" has output to Neuron 1? " + bias1.hasOutputTo(neuron1));
		System.out.println(" has output to Neuron 2? " + bias1.hasOutputTo(neuron2));
		System.out.println(" has output to Neuron 3? " + bias1.hasOutputTo(neuron3));

		System.out.println(" Output Connections: ");
		for (Connection output_connection: bias1.getOutputConnections()) {
			System.out.println("\t" + output_connection.getLabel());
		}

		System.out.println();
		System.out.println("-------- BIAS 2 ---------");
		System.out.println(" Parent Layer: " + bias2.getParentLayer().getKind());
		System.out.println(" Input Data: " + bias2.getInputData());
		System.out.println(" Output: " + bias2.getOutput());
		System.out.println(" has output to Neuron 1? " + bias2.hasOutputTo(neuron1));
		System.out.println(" has output to Neuron 2? " + bias2.hasOutputTo(neuron2));
		System.out.println(" has output to Neuron 3? " + bias2.hasOutputTo(neuron3));

		System.out.println(" Output Connections: ");
		for (Connection output_connection: bias2.getOutputConnections()) {
			System.out.println("\t" + output_connection.getLabel());
		}

		System.out.println();
		System.out.println("-------- BIAS 3 ---------");
		System.out.println(" Parent Layer: " + bias3.getParentLayer().getKind());
		System.out.println(" Input Data: " + bias3.getInputData());
		System.out.println(" Output: " + bias3.getOutput());
		System.out.println(" has output to Neuron 1? " + bias3.hasOutputTo(neuron1));
		System.out.println(" has output to Neuron 2? " + bias3.hasOutputTo(neuron2));
		System.out.println(" has output to Neuron 3? " + bias3.hasOutputTo(neuron3));

		System.out.println(" Output Connections: ");
		for (Connection output_connection: bias3.getOutputConnections()) {
			System.out.println("\t" + output_connection.getLabel());
		}

System.out.println();
System.out.println("=============[ TEST 3: NEURONS CONFIG ]==================");

	System.out.println();
		System.out.println("-------- NEURON 1 ---------");
		System.out.println(" Parent Layer: " + neuron1.getParentLayer().getKind());
		System.out.println(" has input from Input Node 1? " + neuron1.hasInputFrom(input_node1));
		System.out.println(" has input from Input Node 2? " + neuron1.hasInputFrom(input_node2));
		System.out.println(" has input from Bias 1? " + neuron1.hasInputFrom(bias1));
		System.out.println(" has input from Bias 2? " + neuron1.hasInputFrom(bias2));
		System.out.println(" has input from Bias 3? " + neuron1.hasInputFrom(bias3));

		System.out.println(" Input Connections: ");
		for (Connection input_connection: neuron1.getInputs()) {
			System.out.println("\t" + input_connection.getLabel());
		}

		System.out.println(" Output Connections: ");
		for (Connection output_connection: neuron1.getOutputs()) {
			System.out.println("\t" + output_connection.getLabel());
		}

		System.out.println();
		System.out.println("-------- NEURON 2 ---------");
		System.out.println(" Parent Layer: " + neuron2.getParentLayer().getKind());
		System.out.println(" has input from Input Node 1? " + neuron2.hasInputFrom(input_node1));
		System.out.println(" has input from Input Node 2? " + neuron2.hasInputFrom(input_node2));
		System.out.println(" has input from Bias 1? " + neuron2.hasInputFrom(bias1));
		System.out.println(" has input from Bias 2? " + neuron2.hasInputFrom(bias2));
		System.out.println(" has input from Bias 3? " + neuron2.hasInputFrom(bias3));

		System.out.println(" Input Connections: ");
		for (Connection input_connection: neuron2.getInputs()) {
			System.out.println("\t" + input_connection.getLabel());
		}

		System.out.println(" Output Connections: ");
		for (Connection output_connection: neuron2.getOutputs()) {
			System.out.println("\t" + output_connection.getLabel());
		}

		System.out.println();
		System.out.println("-------- NEURON 3 ---------");
		System.out.println(" Parent Layer: " + neuron3.getParentLayer().getKind());
		System.out.println(" has input from Input Node 1? " + neuron3.hasInputFrom(input_node1));
		System.out.println(" has input from Input Node 2? " + neuron3.hasInputFrom(input_node2));
		System.out.println(" has input from Bias 1? " + neuron3.hasInputFrom(bias1));
		System.out.println(" has input from Bias 2? " + neuron3.hasInputFrom(bias2));
		System.out.println(" has input from Bias 3? " + neuron3.hasInputFrom(bias3));

		System.out.println(" Input Connections: ");
		for (Connection input_connection: neuron3.getInputs()) {
			System.out.println("\t" + input_connection.getLabel());
		}

		System.out.println(" Output Connections: ");
		for (Connection output_connection: neuron3.getOutputs()) {
			System.out.println("\t" + output_connection.getLabel());
		}

System.out.println();
System.out.println("=============[ TEST 4: NEURONS PROCESSING ]==================");

		System.out.println();
		System.out.println("-------- NEURON 1 ---------");
		System.out.println(" Input Processing: ");
		System.out.println("    - Neuron netInput before: " + neuron1.getNetInput());

		neuron1.computeInput();

		System.out.println("    - Neuron netInput after: " + neuron1.getNetInput());

		System.out.println(" Output Processing: ");
		System.out.println("    - Neuron output before: " + neuron1.getOutput());

		neuron1.computeOutput();

		System.out.println("    - Neuron output after: " + neuron1.getOutput());

		System.out.println();
		System.out.println("-------- NEURON 2 ---------");
		System.out.println(" Input Processing: ");
		System.out.println("    - Neuron netInput before: " + neuron2.getNetInput());

		neuron2.computeInput();

		System.out.println("    - Neuron netInput after: " + neuron2.getNetInput());

		System.out.println(" Output Processing: ");
		System.out.println("    - Neuron output before: " + neuron2.getOutput());

		neuron2.computeOutput();

		System.out.println("    - Neuron output after: " + neuron2.getOutput());

		System.out.println();
		System.out.println("-------- NEURON 3 ---------");
		System.out.println(" Input Processing: ");
		System.out.println("    - Neuron netInput before: " + neuron3.getNetInput());

		neuron3.computeInput();

		System.out.println("    - Neuron netInput after: " + neuron3.getNetInput());

		System.out.println(" Output Processing: ");
		System.out.println("    - Neuron output before: " + neuron3.getOutput());

		neuron3.computeOutput();

		System.out.println("    - Neuron output after: " + neuron3.getOutput());
/*
		System.out.println();
		System.out.println("-------- NEURONS RESET ---------");

		initial_layer.resetNeurons();
		output_layer.resetNeurons();

		System.out.println(" Neuron 1 netInput: " + neuron1.getNetInput());
		System.out.println(" Neuron 1 output: " + neuron1.getOutput());

		System.out.println(" Neuron 2 netInput: " + neuron2.getNetInput());
		System.out.println(" Neuron 2 output: " + neuron2.getOutput());

		System.out.println(" Neuron 3 netInput: " + neuron3.getNetInput());
		System.out.println(" Neuron 3 output: " + neuron3.getOutput());
*/

System.out.println();
System.out.println("=============[ TEST 5: NEURAL NETWORK ]==================");

		System.out.println("-------- [ INPUT DATA WEIGHTS ] -------- ");
		System.out.println("Input Node 1 -- Neuron 1: ");
		System.out.print(neural_network.getInputDataWeight(input_node1, neuron1));
		System.out.println();
		System.out.println("Input Node 2 -- Neuron 2: ");
		System.out.print(neural_network.getInputDataWeight(input_node2, neuron2));

		System.out.println("-------- [ BIAS WEIGHTS ] -------- ");
		System.out.println("Bias 1 -- Neuron 1: ");
		System.out.print(neural_network.getBiasWeight(bias1, neuron1));
		System.out.println();
		System.out.println("Bias 2 -- Neuron 2: ");
		System.out.print(neural_network.getBiasWeight(bias2, neuron2));
		System.out.println();
		System.out.println("Bias 3 -- Neuron 3: ");
		System.out.print(neural_network.getBiasWeight(bias3, neuron3));

		System.out.println("-------- [ NEURON WEIGHTS ] -------- ");
		System.out.println("Neuron 1 -- Neuron 3: ");
		System.out.print(neural_network.getWeight(neuron1, neuron2));
		System.out.println();
		System.out.println("Neuron 2 -- Neuron 3: ");
		System.out.print(neural_network.getWeight(neuron1, neuron3));

/*                           [DEVELOPMENT STAGE]

System.out.println();
System.out.println("============= TEST 5: NEURAL NETWORK PROCESSING ==================");

		neural_network.computeOutput();

		System.out.println(" Neuron 1 netInput: " + neuron1.getNetInput());
		System.out.println(" Neuron 1 output: " + neuron1.getOutput());

		System.out.println(" Neuron 2 netInput: " + neuron2.getNetInput());
		System.out.println(" Neuron 2 output: " + neuron2.getOutput());

		System.out.println(" Neuron 3 netInput: " + neuron3.getNetInput());
		System.out.println(" Neuron 3 output: " + neuron3.getOutput());

		System.out.println();
		System.out.println("-------- RESET ---------");

		neural_network.reset();

		System.out.println(" Neuron 1 netInput: " + neuron1.getNetInput());
		System.out.println(" Neuron 1 output: " + neuron1.getOutput());

		System.out.println(" Neuron 2 netInput: " + neuron2.getNetInput());
		System.out.println(" Neuron 2 output: " + neuron2.getOutput());

		System.out.println(" Neuron 3 netInput: " + neuron3.getNetInput());
		System.out.println(" Neuron 3 output: " + neuron3.getOutput());
*/
/*
		System.out.println();
		System.out.println("-------- WEIGHTS RANDOM INITIALIZATION ---------");

		neural_network.randomizeAllWeights(-1, 1, generator);

		System.out.println();
		System.out.println(" Neuron 1: ");
		for (Connection input_connection: neuron1.getInputs()) {
			System.out.println("\t" + input_connection.getLabel());
			System.out.println("\t\t" + "Weight: " + input_connection.getWeight().getValue());
		}

		System.out.println();
		System.out.println(" Neuron 2: ");
		for (Connection input_connection: neuron2.getInputs()) {
			System.out.println("\t" + input_connection.getLabel());
			System.out.println("\t\t" + "Weight: " + input_connection.getWeight().getValue());
		}

		System.out.println();
		System.out.println(" Neuron 3: ");
		for (Connection input_connection: neuron3.getInputs()) {
			System.out.println("\t" + input_connection.getLabel());
			System.out.println("\t\t" + "Weight: " + input_connection.getWeight().getValue());
		}
*/
	}
}
