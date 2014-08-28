package dev_tests;

import java.util.ArrayList;
import java.util.List;

import core.Connection;
import core.InputNode;
import core.Layer;
import core.NeuralNetwork;
import core.Neuron;
import core.activation.Sigmoid;
import core.learning.Backpropagation;
import core.learning.LearningRule;
import core.learning.error.SquaredError;
import core.learning.stop.EarlyStop;
import core.learning.stop.MaxEpochsStop;
import core.learning.stop.StopCriteria;
import core.propagation.WeightedSum;
import experiment.Statistics;
import experiment.data.Benchmark;
import experiment.data.ProbenFileParser;
import experiment.data.Sample;

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

		System.out.println(" Data file parsed: " + benchmark.getPath());
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

		Backpropagation backProp = new Backpropagation(0.5, 0.1, benchmark);

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

		// Stop

		StopCriteria epochs_stop = new MaxEpochsStop(backProp, 4);
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

		System.out.println("========= TESTING EXPERIMENT RUNS =========");

		System.out.println("trained ? " + backProp.getNeuralNetwork().isTrained());
		System.out.println("loading benchmarks ...");
		proben_parser.setProbenFile("example2.dt");
		proben_parser.parse();
		Benchmark benchmark2 = proben_parser.getBenchmark();
		benchmark2.setDesiredOutputValuesRange(0.0, 1.0);

		proben_parser.setProbenFile("example3.dt");
		proben_parser.parse();
		Benchmark benchmark3 = proben_parser.getBenchmark();
		benchmark3.setDesiredOutputValuesRange(0.0, 1.0);

		System.out.println("learning...");


		System.out.println("### " + neural_network);

			neural_network.getLearningRule().setBenchmark(benchmark);
			neural_network.learn();

			neural_network.getLearningRule().setBenchmark(benchmark2);
			neural_network.learn();

			neural_network.getLearningRule().setBenchmark(benchmark3);
			neural_network.learn();

			neural_network.getLearningRule().setBenchmark(benchmark);
			neural_network.learn();

			neural_network.getLearningRule().setBenchmark(benchmark2);
			neural_network.learn();

			neural_network.getLearningRule().setBenchmark(benchmark3);
			neural_network.learn();

System.out.println("=========================================================");

/*
		NeuralNetwork best = neural_network.getLearningRule().getBestNeuralNetwork();

		System.out.println(best);
		System.out.println("-------- [ NET GENERAL INFO ] -------- ");

		System.out.println("Network Label: " + best.getLabel());
		System.out.println("Number of layers: " + best.getNumberOfLayers());
		System.out.println("LAYERS:");
		for (Layer layer : best.getLayers()) {
			System.out.println("\t kind:" + layer.getKind());
			System.out.println("\t num. nodes: " + layer.getNodes().size());
			System.out.println("\t ------");
		}
		System.out.println("Num. neurons: " + best.getNeurons().size());
		System.out.println("NEURONS:");
		for (Neuron neuron : best.getNeurons()) {
			System.out.println("\t" + neuron.getLabel());
			System.out.println("\t" + neuron.getParentLayer().getKind());
			System.out.println("\t ------");
		}
		System.out.println("Num. input data nodes: "
				+ best.getInputDataLayer().getInputDataNodes().size());
		System.out.println("Num. bias nodes: "
				+ best.getInputDataLayer().getBiasNodes().size());

		 // System.out.println("Num. bias synapses: " + net .getBiasSynapses()
		 // .size()); System.out.println("Num. input data synapses: " + net
		 // .getInputDataSynapses() .size());
		 // System.out.println("Num. neuro synapses: " + net .getNeuroSynapses()
		 // .size());

		System.out.println();
		System.out.println("-------- [ INPUT DATA NODES] -------- ");
		System.out.println();
		System.out.println("SYNAPSES: ");

		List<Connection> input_data_synapses = best.getInputDataSynapses();

		for (Connection input_data_synapse : input_data_synapses) {
			System.out.println("\t Label: " + input_data_synapse.getLabel());
			System.out.println("\t Source: "
					+ input_data_synapse.getSource().getLabel());
			System.out.println("\t Target: "
					+ input_data_synapse.getTarget().getLabel());
			System.out.println("\t Weight: "
					+ input_data_synapse.getWeight().getValue());
			System.out.println("\t input node to neuron? "
					+ input_data_synapse.isInputNodeToNeuron());
			System.out.println("\t neuron to neuron? "
					+ input_data_synapse.isNeuronToNeuron());
			System.out.println("\t ------");
		}

		System.out.println();
		System.out.println("-------- [ BIAS NODES ] -------- ");
		System.out.println();
		System.out.println("SYNAPSES: ");

		List<Connection> bias_synapses = best.getBiasSynapses();

		for (Connection bias_synapse : bias_synapses) {
			System.out.println("\t Label: " + bias_synapse.getLabel());
			System.out.println("\t Source: "
					+ bias_synapse.getSource().getLabel());
			System.out.println("\t Target: "
					+ bias_synapse.getTarget().getLabel());
			System.out.println("\t Weight: "
					+ bias_synapse.getWeight().getValue());
			System.out.println("\t input node to neuron? "
					+ bias_synapse.isInputNodeToNeuron());
			System.out.println("\t neuron to neuron? "
					+ bias_synapse.isNeuronToNeuron());
			System.out.println("\t ------");
		}

		System.out.println();
		System.out.println("-------- [ NEURONS] -------- ");
		System.out.println();
		System.out.println("SYNAPSES: ");

		List<Neuron> neurons = best.getNeurons();

		for (Neuron neuron : neurons) {
			System.out.println(" ==============");
			System.out.println("\t Label: " + neuron.getLabel());
			System.out
					.println("\t Layer: " + neuron.getParentLayer().getKind());
			System.out.println("\t Num. input synapses: "
					+ neuron.getInputs().size());
			System.out.println("\t Num. output synapses: "
					+ neuron.getOutputs().size());

			System.out.println("  INPUT SYNAPSES");
			for (Connection input_synapse : neuron.getInputs()) {

				System.out.println("\t  ------");
				System.out.println("\t Source: "
						+ input_synapse.getSource().getLabel());
				System.out.println("\t Target: "
						+ input_synapse.getTarget().getLabel());
				System.out.println("\t Weight: "
						+ input_synapse.getWeight().getValue());
				System.out.println("\t  ------");
			}
			System.out.println("  OUTPUT SYNAPSES");
			for (Connection output_synapse : neuron.getOutputs()) {

				System.out.println("\t  ------");
				System.out.println("\t Source: "
						+ output_synapse.getSource().getLabel());
				System.out.println("\t Target: "
						+ output_synapse.getTarget().getLabel());
				System.out.println("\t Weight: "
						+ output_synapse.getWeight().getValue());
				System.out.println("\t  ------");
			}
		}




		/*

		System.out.println("trained ? " + backProp.getNeuralNetwork().isTrained());
		System.out.println("original trained ? " + neural_network.isTrained());

		System.out.println("original net: " + neural_network);
		System.out.println("backprop net: " + backProp.getNeuralNetwork());
		System.out.println("backprop best net: " + backProp.getBestNeuralNetwork());

System.out.println("========= STATS =========");

		Statistics stats = new Statistics(backProp.getNeuralNetwork());

		System.out.println("etrs size: " + stats.getEtrs().size());
		System.out.println("evas size: " + stats.getEvas().size());
		System.out.println("etts size: " + stats.getEtes().size());
		System.out.println("gls size: " + stats.getGLs().size());
		System.out.println("pks size: " + stats.getPKs().size());

		int training_fails = stats
                             .getNumberOfClassificationMissesOnTraining(benchmark),
            validation_fails = stats
                               .getNumberOfClassificationMissesOnValidation(benchmark),
            test_fails = stats.getNumberOfClassificationMissesOnTest(benchmark);

		System.out.println("training failures: " + training_fails);
		System.out.println("validation failures: " + validation_fails);
		System.out.println("test failures: " + test_fails);

		*/

	}
}