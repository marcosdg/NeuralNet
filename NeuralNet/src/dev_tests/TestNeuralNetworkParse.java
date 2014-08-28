package dev_tests;

import java.util.List;

import core.Connection;
import core.InputNode;
import core.Layer;
import core.NeuralNetwork;
import core.Neuron;
import core.learning.Backpropagation;
import experiment.data.Benchmark;
import experiment.data.NeuralNetworkParser;
import experiment.data.ProbenFileParser;
import experiment.data.Sample;

public class TestNeuralNetworkParse {
	public static void main(String[] args) {


// Data file.

		String proben_dir = "example";
		String proben_file_name = "example1.dt";

// Parsing.

		ProbenFileParser proben_parser = new ProbenFileParser(proben_dir, proben_file_name);

		proben_parser.parse();

		Benchmark benchmark = proben_parser.getBenchmark();

System.out.println("============= TEST: DATA FILE PARSING  ==================");

		System.out.println(" Data file parsed: " + benchmark.getPath());

		System.out.println(" Num. training samples: " +
				benchmark.getNumberOfTrainingSamples());

		System.out.println(" Num. validation samples: " +
				benchmark.getNumberOfValidationSamples());

		System.out.println(" Num. test samples: " +
				benchmark.getNumberOfTestSamples());

		List<Sample> samples = benchmark.getSamples();
		Sample sample = null;

		System.out.println("\t" + "FIRST 3 SAMPLES PARSED");

		for (int i = 0; i < 3; i += 1) {
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

System.out.println("============= TEST: NEURAL NETWORK PARSING  ==================");

		String config_dir = "single-layer",
               config_file_name = "net-default-test";

		NeuralNetworkParser net_parser = new NeuralNetworkParser(config_dir,
	                                                            config_file_name);
		net_parser.setBenchmark(benchmark);

		net_parser.parse();

		NeuralNetwork net = net_parser.getNeuralNetwork();

		System.out.println("-------- [ NET GENERAL INFO ] -------- ");

		System.out.println("Network Label: " + net.getLabel());
		System.out.println("Number of layers: " + net.getNumberOfLayers());
		System.out.println("LAYERS:");
		for (Layer layer: net.getLayers()) {
			System.out.println("\t kind:" + layer.getKind());
			System.out.println("\t num. nodes: " + layer.getNodes().size());
			System.out.println("\t ------");
		}
		System.out.println("Num. neurons: " + net.getNeurons().size());
		System.out.println("NEURONS:");
		for (Neuron neuron: net.getNeurons()) {
			System.out.println("\t" + neuron.getLabel());
			System.out.println("\t" + neuron.getParentLayer().getKind());
			System.out.println("\t ------");
		}
		System.out.println("Num. input data nodes: " + net
                                                       .getInputDataLayer()
                                                       .getInputDataNodes()
                                                       .size());
		System.out.println("Num. bias nodes: " + net
                                                 .getInputDataLayer()
                                                 .getBiasNodes()
                                                 .size());
		/*
		System.out.println("Num. bias synapses: " + net
                                                    .getBiasSynapses()
                                                    .size());
		System.out.println("Num. input data synapses: " + net
                                                          .getInputDataSynapses()
                                                          .size());
		System.out.println("Num. neuro synapses: " + net
                                                     .getNeuroSynapses()
                                                     .size());
        */
		System.out.println();
		System.out.println("-------- [ INPUT DATA NODES] -------- ");
		System.out.println();
		System.out.println("SYNAPSES: ");

		List<Connection> input_data_synapses = net.getInputDataSynapses();

		for (Connection input_data_synapse: input_data_synapses) {
			System.out.println("\t Label: " + input_data_synapse.getLabel());
			System.out.println("\t Source: " + input_data_synapse.getSource().getLabel());
			System.out.println("\t Target: " + input_data_synapse.getTarget().getLabel());
			System.out.println("\t Weight: " + input_data_synapse.getWeight().getValue());
			System.out.println("\t input node to neuron? " + input_data_synapse.isInputNodeToNeuron());
			System.out.println("\t neuron to neuron? " + input_data_synapse.isNeuronToNeuron());
			System.out.println("\t ------");
		}

		System.out.println();
		System.out.println("-------- [ BIAS NODES ] -------- ");
		System.out.println();
		System.out.println("SYNAPSES: ");

		List<Connection> bias_synapses = net.getBiasSynapses();

		for (Connection bias_synapse: bias_synapses) {
			System.out.println("\t Label: " + bias_synapse.getLabel());
			System.out.println("\t Source: " + bias_synapse.getSource().getLabel());
			System.out.println("\t Target: " + bias_synapse.getTarget().getLabel());
			System.out.println("\t Weight: " + bias_synapse.getWeight().getValue());
			System.out.println("\t input node to neuron? " + bias_synapse.isInputNodeToNeuron());
			System.out.println("\t neuron to neuron? " + bias_synapse.isNeuronToNeuron());
			System.out.println("\t ------");
		}

		System.out.println();
		System.out.println("-------- [ NEURONS] -------- ");
		System.out.println();
		System.out.println("SYNAPSES: ");

		List<Neuron> neurons = net.getNeurons();

		for (Neuron neuron: neurons) {
			System.out.println(" ==============");
			System.out.println("\t Label: " + neuron.getLabel());
			System.out.println("\t Layer: " + neuron.getParentLayer().getKind());
			System.out.println("\t Num. input synapses: " + neuron.getInputs().size());
			System.out.println("\t Num. output synapses: " + neuron.getOutputs().size());

			System.out.println("  INPUT SYNAPSES");
			for (Connection input_synapse: neuron.getInputs()) {

				System.out.println("\t  ------");
				System.out.println("\t Source: " + input_synapse.getSource().getLabel());
				System.out.println("\t Target: " + input_synapse.getTarget().getLabel());
				System.out.println("\t Weight: "+ input_synapse.getWeight().getValue());
				System.out.println("\t  ------");
			}
			System.out.println("  OUTPUT SYNAPSES");
			for (Connection output_synapse: neuron.getOutputs()) {

				System.out.println("\t  ------");
				System.out.println("\t Source: " + output_synapse.getSource().getLabel());
				System.out.println("\t Target: " + output_synapse.getTarget().getLabel());
				System.out.println("\t Weight: "+ output_synapse.getWeight().getValue());
				System.out.println("\t  ------");
			}
		}

		System.out.println("-------- [ LEARNING PARAMETERS ] -------- ");

		Backpropagation backprop = ((Backpropagation) net.getLearningRule());
		System.out.println("Benchmark: " + backprop.getBenchmark().getPath());
		System.out.println("O_MIN: " + backprop.getBenchmark().getMinDesiredOutputValue());
		System.out.println("O_MAX: " + backprop.getBenchmark().getMaxDesiredOutputValue());
		System.out.println("MOMENTUM: " + backprop.getMomemtum());
		System.out.println("LEARNING_RATE: " + backprop.getLearningRate());
		System.out.println("MAX_EPOCHS: " + backprop.getMaxEpochsStop().getMaxEpochs());
		System.out.println("STRIP: " + backprop.getEarlyStop().getStripLength());
		System.out.println("GL: " + backprop.getEarlyStop().getMaxGeneralizationLoss());
		System.out.println("PK: " + backprop.getEarlyStop().getMinTrainingProgress());
		System.out.println("NET_DESIGN: " + net_parser.getNetworkDesign());


		System.out.println();
		System.out.println("The following identifiers must be the same: ");
		System.out.println("  Backprop reference to best net: " + backprop.getBestNeuralNetwork());
		System.out.println("  Backprop reference to net: " + backprop.getNeuralNetwork());
		System.out.println("  net: " + net);
		System.out.println("The following identifiers must be the same: ");
		System.out.println("  Backprop reference to benchmark: " + backprop.getBenchmark());
		System.out.println("  benchmark: " + benchmark);
	}
}

