package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.data.Sample;
import core.learning.LearningRule;

/*
 * Represents a collection of Layers.
 */

public class NeuralNetwork {

	private List<Layer> layers;
	private LearningRule learningRule;
	private String label;


// Creation.


	public NeuralNetwork(List<Layer> layers, LearningRule learningRule, String label) {
		if (layers != null && learningRule != null && label != null) {
			this.layers = layers;
			this.learningRule = learningRule;
			this.label = label;
		}
	}

	public void reset() {
		for (Layer layer: this.layers) {
			layer.resetNeurons();
		}
	}

	// Generic connection (nodes must be created first).

	public void createConnection(Node from, Neuron to, double weight_value, String label) {
		Weight weight = null;
		Connection connection = null;

		if (this.hasNode(from) && this.hasNode(to)) {

			weight = new Weight(weight_value);
			connection = new Connection(from, to, weight, label);

			// from Neuron ?

			if (from instanceof Neuron) {

				((Neuron) from).addOutputConnection(connection);

			// from InputNode ?

			} else if (from instanceof InputNode) {

				((InputNode) from).addOutputConnection(connection);
			}
			// always goes to Neuron.

			to.addInputConnection(connection);
		}
	}


// Processing.


	// Forwards input data through the net to get the output vector.

	public List<Double> computeOutput(Sample training_sample) {
		List<Double> output_vector = new ArrayList<Double>();

		this.loadTrainingSample(training_sample);

		for (Layer layer: this.layers) { // they must be ordered.
			layer.computeOutput();
		}

		for (Node node: this.getOutputLayer().getNodes()) {
			output_vector.add(((Neuron) node).getOutput());
		}
		return output_vector;
	}
	public void loadTrainingSample(Sample sample) {
		Layer input_data_layer = this.getInputDataLayer();
		InputNode input_node = null;
		Double datum = 0.0;

		// The nodes to set up.

		List<Node> input_nodes = input_data_layer.getNodes();

		// The values to be loaded.

		List<Double> input_vector = sample.getInputVector();

		if (input_nodes.size() != input_vector.size()) {
			throw new IllegalArgumentException("NeuralNetwork:" +
                                                " wrong sample to be loaded");
		}

		for (int i = 0; i < input_data_layer.numberOfNodes(); i += 1) {
			input_node = (InputNode) input_nodes.get(i);
			datum = input_vector.get(i);

			input_node.setInputData(datum);
		}
	}

	// TODO: COMPLETE 'LEARN' METHOD

	public void learn() {
		this.learningRule.apply();
	}


// Layers configuration.


	public List<Layer> getLayers() {
		return this.layers;
	}
	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}
	public int getNumberOfLayers() {
		return this.layers.size();
	}

	public boolean addLayer(Layer layer) {
		if (!this.layers.contains(layer)) {

			this.layers.add(layer);
			return true;
		}
		return false;
	}
	public boolean removeLayer(Layer layer) {
		if (this.layers.contains(layer)) {

			this.layers.remove(layer);
			return true;
		}
		return false;
	}

	public Layer getLayerAt(int at) {
		return this.layers.get(at);
	}
	public Layer getInputDataLayer() {
		Layer initial_layer = null;

		for (Layer layer: this.layers) {

			if (layer.isInputDataLayer()) {
				initial_layer = layer;
				break;
			}
		}
		return initial_layer;
	}
	public Layer getInitialLayer() {
		Layer initial_layer = null;

		for (Layer layer: this.layers) {

			if (layer.isInitialLayer()) {
				initial_layer = layer;
				break;
			}
		}
		return initial_layer;
	}
	public Layer getOutputLayer() {
		Layer output_layer = null;

		for (Layer layer: this.layers) {

			if (layer.isOutputLayer()) {
				output_layer = layer;
				break;
			}
		}
		return output_layer;
	}

	public boolean hasNode(Node node) {
		boolean has = false;

		for (Layer layer: this.layers) {
			if (layer.hasNode(node)) {
				has = true;
				break;
			}
		}
		return has;
	}

	// Randomization.

	public void randomizeAllWeights(double min, double max, Random generator) {
		if (!this.layers.isEmpty()) {

			for (Layer layer: this.layers) {
				layer.randomizeWeights(min, max, generator);
			}
		}
	}


// Learning configuration.


	public LearningRule getLearningRule() {
		return this.learningRule;
	}
	public void setLearningRule(LearningRule that) {
		this.learningRule = that;
	}


// Label configuration.


	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}