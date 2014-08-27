package dev_tests;

import java.util.ArrayList;
import java.util.List;

import core.Connection;
import core.Layer;
import core.NeuralNetwork;
import core.Neuron;
import core.learning.Backpropagation;

import experiment.Experiment;
import experiment.data.Benchmark;

public class TestExperiment {

	public static void main(String[] args) {

		// Data files.

		String net_dir = "single-layer",
               net_file = "net-test",
               proben_dir = "example";

		List<String> proben_files = new ArrayList<String>();
		proben_files.add("example1.dt");
		proben_files.add("example2.dt");
		proben_files.add("example3.dt");

		// Experiment.

		Experiment experiment = new Experiment(net_dir, net_file, proben_dir, proben_files);

		System.out.println("=======[ LOADING BENCHMARKS ]=======");

		List<Benchmark> benchs = experiment.loadAllBenchmarks();

		System.out.println("Number of benchs loaded: " + benchs.size());
		System.out.println("Which ones ?");
		for (Benchmark bench: benchs) {
			System.out.println("  " + bench.getLabel());
		}

		System.out.println();
		System.out.println("=======[ LOADING NEURAL NET ]=======");

		NeuralNetwork net = experiment.loadNeuralNetwork();

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
		System.out.println("Benchmark: " + backprop.getBenchmark().getLabel());
		System.out.println("O_MIN: " + backprop.getBenchmark().getMinDesiredOutputValue());
		System.out.println("O_MAX: " + backprop.getBenchmark().getMaxDesiredOutputValue());
		System.out.println("MOMENTUM: " + backprop.getMomemtum());
		System.out.println("LEARNING_RATE: " + backprop.getLearningRate());
		System.out.println("MAX_EPOCHS: " + backprop.getMaxEpochsStop().getMaxEpochs());
		System.out.println("STRIP: " + backprop.getEarlyStop().getStripLength());
		System.out.println("GL: " + backprop.getEarlyStop().getMaxGeneralizationLoss());
		System.out.println("PK: " + backprop.getEarlyStop().getMinTrainingProgress());
		System.out.println("NET_DESIGN: " + experiment.getNeuralNetworkParser().getNetworkDesign());


		System.out.println("=======[ RUNNING THE EXPERIMENT ]=======");

		experiment.setNumberOfRuns(1);
		experiment.run();

		System.out.println("Num stats saved: " + experiment.getStats().size());


	}
}