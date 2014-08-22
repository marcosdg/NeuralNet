package dev_tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Connection;
import core.InputNode;
import core.Layer;
import core.NeuralNetwork;
import core.Neuron;
import core.Node;
import core.Weight;
import core.activation.Linear;
import core.data.Benchmark;
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

		 // Not tested now (empty benchmark).
		Backpropagation backprop = new Backpropagation(0.5, 0.2, new Benchmark());

		NeuralNetwork neural_network = new NeuralNetwork(layers, backprop, "Neural Network");

		// Connections.

		neural_network.createConnection(input_node1, neuron1, 1, "Connection: InputNode1 -> Neuron1");
		neural_network.createConnection(input_node2, neuron2, 1, "Connection: InputNode2 -> Neuron1");

		neural_network.createConnection(bias1, neuron1, -1, "Connection: Bias1 -> Neuron1");
		neural_network.createConnection(bias2, neuron2, -1, "Connection: Bias2 -> Neuron2");
		neural_network.createConnection(bias3, neuron3, -1, "Connection: Bias3 -> Neuron3");

		neural_network.createConnection(neuron1, neuron3, 1, "Connection: Neuron1 -> Neuron3");
		neural_network.createConnection(neuron2, neuron3, 100, "Connection: Neuron2 -> Neuron3");

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
System.out.println("=============[ TEST 4: CLONING ]==================");
System.out.println();
/*
		System.out.println("-------- WEIGHTS ---------");

		Weight w23 = neural_network.getNeuroWeight(neuron2, neuron3);
		Weight w23_copy = w23.copy();

		w23_copy.setValue(50);
		w23_copy.setCorrection(0.15);

		System.out.println("w23 value: " + w23.getValue());
		System.out.println("w23 correction: " + w23.getCorrection());
		System.out.println("w23_copy value: " + w23_copy.getValue());
		System.out.println("w23_copy correction: " + w23_copy.getCorrection());

		System.out.println("-------- INPUT NODES ---------");

		InputNode input_node1_copy = input_node1.copy();
		input_node1_copy.setInputData(99.0);
		System.out.println("input_node1 input: " + input_node1.getInputData());
		System.out.println("input_node1_copy input: " + input_node1_copy.getInputData());

		System.out.println("-------- NEURONS ---------");

		Neuron neuron1_copy = neuron1.copy();
		neuron1.setNetInput(2.0);
		neuron1_copy.setNetInput(4.0);

		System.out.println("Neuron 1 netInput: " + neuron1.getNetInput());
		System.out.println("Neuron 1 copy netInput: " + neuron1_copy.getNetInput());

		System.out.println("-------- LAYERS ---------");

		Layer output_layer_copy = output_layer.copy();

		Neuron neuron4 = new Neuron(weightedSum, linear, output_layer_copy, "Neuron 4");

		output_layer_copy.addNode(neuron4);

		System.out.println("output_layer num. nodes: " + output_layer.numberOfNodes());
		System.out.println("output_layer_copy num. nodes: " + output_layer_copy.numberOfNodes());

		System.out.println("-------- BACKPROP ---------");

		Backpropagation backprop_copy = backprop.copy();

		backprop_copy.setCurrentEpoch(backprop_copy.getCurrentEpoch() + 1);
		backprop_copy.setLearninRate(0.9);
		backprop_copy.setMomentum(0.3);

		backprop_copy.saveEva(0.6);
		backprop_copy.saveEva(0.7);

		List<Double> training_output_vector = new ArrayList<Double>();
		training_output_vector.add(1.0);
		training_output_vector.add(2.0);
		backprop_copy.saveTrainingOutputVector(training_output_vector);


		System.out.println("backprop epoch: " + backprop.getCurrentEpoch());
		System.out.println("backprop_copy epoch: " + backprop_copy.getCurrentEpoch());
		System.out.println("backprop learning rate: " + backprop.getLearningRate());
		System.out.println("backprop_copy learning rate: " + backprop_copy.getLearningRate());
		System.out.println("backprop momentum: " + backprop.getMomemtum());
		System.out.println("backprop_copy momentum: " + backprop_copy.getMomemtum());

		System.out.println("backprop evas record size: " + backprop.getEvasRecord().size());
		System.out.println("backprop_copy evas record size: " + backprop_copy.getEvasRecord().size());

		System.out.println("backprop training output vectors size: " + backprop.getTrainingOutputVectors().size());
		System.out.println("backprop_copy training output vectors size: " + backprop_copy.getTrainingOutputVectors().size());
*/

		System.out.println("-------- TIME TRAVEL ---------");

		NeuralNetwork net_copy = neural_network.copy();
		net_copy.setLabel("Neural Network copy");

		System.out.println("net1: " + neural_network);
		System.out.println("net1.copy: " + net_copy);

		Backpropagation original_backprop = (Backpropagation) neural_network.getLearningRule();
		Backpropagation copy_backprop = (Backpropagation) net_copy.getLearningRule();

		original_backprop.setBestNeuralNetwork(net_copy);
		System.out.println("net1.bestNet: " + neural_network.getLearningRule().getBestNeuralNetwork());

		original_backprop.setNeuralNetwork(neural_network.getLearningRule().getBestNeuralNetwork());

		System.out.println("neural_network reference from LearningRule (changed to best net): " + original_backprop.getNeuralNetwork());
		System.out.println("The original net1 won't change: " + neural_network);

/*
		System.out.println("-------- NEURAL NETWORK ---------");
		net_copy.addLayer(new Layer(Layer.HIDDEN_LAYER)); // empty

		System.out.println("net num.layers: " + neural_network.getNumberOfLayers());
		System.out.println("net_copy num.layers: " + net_copy.getNumberOfLayers());
		//
		// Tests if learningRule's NeuralNetwork atrribute (reference)
		// is the copy and not the original one.
		//
		// net_copy ------getLearningRule()---------------> learningRule
		//          <-----getNeuralNetwork().getLabel()----
		//
		System.out.println("Learning rule's NeuralNetwork reference: " + net_copy.getLearningRule().getNeuralNetwork().getLabel());
		//
		// Lets see if modifying the state of one instance of Backprop
		// affects the other.
		//
		Backpropagation backprop_copy = (Backpropagation) net_copy.getLearningRule();
		backprop_copy.setCurrentEpoch(backprop_copy.getCurrentEpoch() + 2);
		System.out.println("backprop epoch: " + backprop.getCurrentEpoch());
		System.out.println("backprop_copy epoch: " + backprop_copy.getCurrentEpoch());

		List<Double> training_output_vector = new ArrayList<Double>();
		training_output_vector.add(1.0);
		training_output_vector.add(2.0);
		backprop_copy.saveTrainingOutputVector(training_output_vector);
		System.out.println("backprop training output vectors size: " + backprop.getTrainingOutputVectors().size());
		System.out.println("backprop_copy training output vectors size: " + backprop_copy.getTrainingOutputVectors().size());

		System.out.println(neural_network.getLearningRule().getBestNeuralNetwork().getLabel());
*/

/*
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
/*
System.out.println();
System.out.println("=============[ TEST 5: NEURAL NETWORK ]==================");

		System.out.println("-------- [ INPUT DATA WEIGHTS ] -------- ");
		System.out.println("Input Node 1 -- Neuron 1: ");
		System.out.print(neural_network
                         .getInputDataWeight(input_node1, neuron1)
                         .getValue());
		System.out.println();
		System.out.println("Input Node 2 -- Neuron 2: ");
		System.out.print(neural_network
                         .getInputDataWeight(input_node2, neuron2)
                         .getValue());
		System.out.println();
		System.out.println("-------- [ BIAS WEIGHTS ] -------- ");
		System.out.println("Bias 1 -- Neuron 1: ");
		System.out.print(neural_network
                         .getBiasWeight(bias1, neuron1)
                         .getValue());
		System.out.println();
		System.out.println("Bias 2 -- Neuron 2: ");
		System.out.print(neural_network
                         .getBiasWeight(bias2, neuron2)
                         .getValue());
		System.out.println();
		System.out.println("Bias 3 -- Neuron 3: ");
		System.out.print(neural_network
                         .getBiasWeight(bias3, neuron3)
                         .getValue());
		System.out.println();
		System.out.println("-------- [ NEURON WEIGHTS ] -------- ");
		System.out.println("Neuron 1 -- Neuron 3: ");
		System.out.print(neural_network
                         .getNeuroWeight(neuron1, neuron3)
                         .getValue());
		System.out.println();
		System.out.println("Neuron 2 -- Neuron 3: ");
		System.out.print(neural_network
                         .getNeuroWeight(neuron2, neuron3)
                         .getValue());

*/



/*                           [DEVELOPMENT STAGE]

System.out.println();
System.out.println("============= TEST 6: NEURAL NETWORK PROCESSING ==================");

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
