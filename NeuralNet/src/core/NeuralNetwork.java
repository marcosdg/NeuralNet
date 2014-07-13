package core;

import java.util.List;
import java.util.Random;

import core.data.DataSet;
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

	public void resetLayers() {
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


	public void computeOutput() {

		for (Layer layer: this.layers) {
			layer.computeOutput();
		}
	}

	// TODO: COMPLETE 'LEARN' METHOD
	public void learn(DataSet data){
		this.learningRule.apply(data);
	}


// Layers configuration.


	public List<Layer> getLayers() {
		return this.layers;
	}
	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}
	public int numberOfLayers() {
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
	public Layer getInitialLayer() {
		Layer initial_layer = null;

		// Look in layers.

		for (Layer layer: this.layers) {

			// Found ?

			if (layer.isInitialLayer()) {
				initial_layer = layer;
				break;
			}
		}
		return initial_layer;
	}
	public Layer getOutputLayer() {
		Layer output_layer = null;

		// Look in layers.

		for (Layer layer: this.layers) {

			// Found ?

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