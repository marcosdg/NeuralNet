package test;

import java.util.ArrayList;
import java.util.List;
import core.InputNode;
import core.Layer;
import core.NeuralNetwork;
import core.Neuron;
import core.activation.Sigmoid;
import core.data.Benchmark;
import core.data.ProbenFileParser;
import core.data.Sample;
import core.learning.Backpropagation;
import core.learning.LearningRule;
import core.learning.stop.EarlyStop;
import core.learning.stop.MaxEpochsStop;
import core.learning.stop.StopCriteria;
import core.propagation.WeightedSum;

public class TestForwardPropagate {


	public static void main(String[] args) {


// LOAD FILE.

		// Data file.

		String proben_dir = "example";
		String proben_file_name = "example1.dt";

		// Parsing.

		ProbenFileParser proben_parser = new ProbenFileParser(proben_dir,
                                                              proben_file_name);
		proben_parser.parse();

		Benchmark benchmark = proben_parser.getBenchmark();

		benchmark.setDesiredOutputValuesRange(0.0, 1.0);

System.out.println("========= FILE =========");

		System.out.println(" Data file parsed: " + benchmark.getLabel());
		System.out.println(" Num. training samples: " +
				benchmark.getNumberOfTrainingSamples());
		System.out.println(" Num. validation samples: " +
				benchmark.getNumberOfValidationSamples());
		System.out.println(" Num. test samples: " +
				benchmark.getNumberOfTestSamples());

		List<Sample> samples = benchmark.getSamples();
		Sample sample = null;
/*
		System.out.println("\t" + "SAMPLES PARSED");

		for (int i = 0; i < samples.size(); i += 1) {
			sample = samples.get(i);
			System.out.println("----- SAMPLE " + i + " -----");

			System.out.println("  * Inputs *");
			for (Double input: sample.getInputVector()) {
				System.out.print(input + " ");
			}
			System.out.println();

			System.out.println("  * Desired Outputs *");
			for (Double desired: sample.getDesiredOutputVector()) {
				System.out.print(desired + " ");
			}
			System.out.println();
		}
		System.out.println();
*/


// LOAD NEURAL NETWORK.


		// Randomizer

		//Random generator = new Random();


		// Synaptic functions.


			// Propagation.

		WeightedSum weightedSum = new WeightedSum();

			// Activation.

		Sigmoid sigmoid = new Sigmoid();


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
		//input_node1.setInputData(2);
		//input_node2.setInputData(2);

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

		Neuron neuron1 = new Neuron(weightedSum, sigmoid, initial_layer,
                                    "Neuron 1");
		Neuron neuron2 = new Neuron(weightedSum, sigmoid, initial_layer,
                                    "Neuron 2");
		Neuron neuron3 = new Neuron(weightedSum, sigmoid, output_layer,
                                    "Neuron 3");

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

		Backpropagation backProp = new Backpropagation(0.5, 0.1); // Not tested now.
		NeuralNetwork neural_network = new NeuralNetwork(layers,
                                                         backProp,
                                                         "Neural Network");

			// Connections.

		neural_network.createConnection(input_node1, neuron1, 1,
                                        "Connection: InputNode1 -> Neuron1");
		neural_network.createConnection(input_node1, neuron2, 1,
                                        "Connection: InputNode1 -> Neuron2");

		neural_network.createConnection(input_node2, neuron1, 1,
                                        "Connection: InputNode2 -> Neuron1");
		neural_network.createConnection(input_node2, neuron2, 1,
                                        "Connection: InputNode2 -> Neuron2");

		neural_network.createConnection(bias1, neuron1, -1,
                                        "Connection: Bias1 -> Neuron1");
		neural_network.createConnection(bias2, neuron2, -1,
                                        "Connection: Bias2 -> Neuron2");
		neural_network.createConnection(bias3, neuron3, -1,
                                        "Connection: Bias3 -> Neuron3");

		neural_network.createConnection(neuron1, neuron3, 0.5,
                                        "Connection: Neuron1 -> Neuron3");
		neural_network.createConnection(neuron2, neuron3, 2,
                                        "Connection: Neuron2 -> Neuron3");
		// set neural_network label

		// References.

			// backProp -----> benchmark.

		backProp.setBenchmark(benchmark);

			// backProp -----> neural_network.

		backProp.setNeuralNetwork(neural_network);

			// neural_network ----> backProp.

		neural_network.setLearningRule(backProp);

		// Stop

		StopCriteria epochs_stop = new MaxEpochsStop(backProp, 1);
		StopCriteria early_stop = new EarlyStop(backProp, 1, 5.0, 0.1);

		backProp.addStopCriteria(epochs_stop);
		backProp.addStopCriteria(early_stop);


System.out.println("========= BACKPROP CONFIG =========");

		System.out.println("Current Epoch: " + backProp.getCurrentEpoch());
		System.out.println("Learning rate: " + backProp.getLearningRate());
		System.out.println("Momentum: " + backProp.getMomemtum());
		System.out.println("Max Epochs: " + backProp.getMaxEpochsStop().getMaxEpochs());
		System.out.println("Strip length: " + backProp.getEarlyStop().getStripLength());
		System.out.println("GL: " + backProp.getEarlyStop().getMaxGeneralizationLoss());
		System.out.println("Pk: " + backProp.getEarlyStop().getMinTrainingProgress());
		System.out.println("O_MIN: " + backProp.getBenchmark().getMinDesiredOutputValue());
		System.out.println("O_MAX: " + backProp.getBenchmark().getMaxDesiredOutputValue());
		System.out.println();

// RUN

		neural_network.learn();
/*
		System.out.println("Neuron 1 error: " + neuron1.getError());
		System.out.println("Neuron 2 error: " + neuron2.getError());
		System.out.println("Neuron 3 error: " + neuron3.getError());
		System.out.println("Training output vectors size: " + backProp.getTrainingOutputVectors().size());
		System.out.println("Number of Layers: " + neural_network.getNumberOfLayers());


/*
System.out.println("===== NEURAL NETWORK STATE AFTER FORWARD-PROPAGATION =====");
System.out.println();

		System.out.println("Neuron 1 [netinput]: " + neuron1.getNetInput());
		System.out.println("Neuron 1 [output]: " + neuron1.getOutput());

		System.out.println("Neuron 2 [netinput]: " + neuron2.getNetInput());
		System.out.println("Neuron 2 [output]: " + neuron2.getOutput());

		System.out.println("Neuron 3 [netinput]: " + neuron3.getNetInput());
		System.out.println("Neuron 3 [OUTPUT]: " + neuron3.getOutput());
*/
	}
}