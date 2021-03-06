package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.learning.Backpropagation;
import core.learning.LearningRule;
import experiment.data.Sample;

/*
 * Represents a collection of Layers.
 */

public class NeuralNetwork {

	private List<Layer> layers;
	private LearningRule learningRule;
	private boolean trained;
	private String label;

// Creation.


	public NeuralNetwork(List<Layer> layers, LearningRule learningRule, String label) {
		if (layers != null && learningRule != null && label != null) {
			this.layers = layers;
			this.learningRule = learningRule;
			this.trained = false;
			this.label = label;

			// Cross-references:
			// The best_net reference will change during learning due to
			// 'Early Stopping'.

			this.learningRule.setNeuralNetwork(this);
		}
	}

	// Will allow to save a copy of the best net during learning.

	public NeuralNetwork copy() {

		// Current state of learning process.

		Backpropagation backprop_copy = ((Backpropagation) this.learningRule)
                                        .copy();

		// Network structure.

		List<Layer> layers_copy = new ArrayList<Layer>();
		for (Layer layer: this.layers) {
			layers_copy.add(layer.copy());
		}

		// net_copy ----reference---> backprop.

		NeuralNetwork net_copy = new NeuralNetwork(layers_copy,
                                                    backprop_copy,
                                                    this.label);
		// net_copy <----reference--- backprop.

		backprop_copy.setNeuralNetwork(net_copy);

		// Current state of the net.

		net_copy.setTrained(this.trained);

		return net_copy;
	}

	public void reset() {
		for (Layer layer: this.layers) {
			if (!layer.isInputDataLayer()) {
				layer.resetNeurons();
			}
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

	public void learn() {

		// Best net cannot be copied until THIS is created first.

		this.learningRule.setBestNeuralNetwork(this.copy());

		this.learningRule.apply();
	}

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

		List<InputNode> input_data_nodes = input_data_layer.getInputDataNodes();

		// The values to be loaded.

		List<Double> input_vector = sample.getInputVector();

		if (input_data_nodes.size() != input_vector.size()) {
			throw new IllegalArgumentException("NeuralNetwork:" +
                                                " wrong sample to be loaded");
		}
		for (int i = 0; i < input_data_nodes.size(); i += 1) {
			input_node = input_data_nodes.get(i);
			datum = input_vector.get(i);

			input_node.setInputData(datum);
		}
	}


// Layers.


	// General.

	public List<Layer> getLayers() {
		return this.layers;
	}
	public Layer getLayerAt(int at) {
		return this.layers.get(at);
	}
	public int getNumberOfLayers() {
		return this.layers.size();
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
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

	// Kinds.

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


// Nodes.


	public boolean hasNode(Node node) {
		boolean has = false;

		for (Layer layer : this.layers) {
			if (layer.hasNode(node)) {
				has = true;
				break;
			}
		}
		return has;
	}
	public List<Neuron> getNeurons() {
		List<Neuron> neurons = new ArrayList<Neuron>();

		for (Layer layer: this.layers) {
			if (!layer.isInputDataLayer()) {

				for (Node node: layer.getNodes()) {
					neurons.add(((Neuron) node));
				}
			}
		}
		return neurons;
	}


// Connections.


	public Connection findSynapseWithin(Node from,
                                           Node to,
                                           List<Connection> synapses) {
		Connection lost = null;

		for (Connection synapse: synapses) {
			if (synapse.getSource().getLabel().equals(from.getLabel()) &&
				synapse.getTarget().getLabel().equals(to.getLabel())) {

				lost = synapse;
				break;
			}
		}
		return lost;
	}

	// InputDataNode - Neuron.

	public List<Connection> getInputDataSynapses() {
		List<Connection> data_synapses = new ArrayList<Connection>();

		for (InputNode data_node: this.getInputDataLayer().getInputDataNodes()) {
			data_synapses.addAll(data_node.getOutputConnections());
		}
		return data_synapses;
	}
	public Connection findInputDataSynapse(InputNode from, Neuron to) {
		return this.findSynapseWithin(from, to, this.getInputDataSynapses());
	}

	// Bias - Neuron.

	public List<Connection> getBiasSynapses() {
		List<Connection> bias_synapses = new ArrayList<Connection>();

		for (InputNode bias: this.getInputDataLayer().getBiasNodes()) {
			bias_synapses.addAll(bias.getOutputConnections());
		}
		return bias_synapses;
	}
	public Connection findBiasSynapse(InputNode from, Neuron to) {
		return this.findSynapseWithin(from, to, this.getBiasSynapses());
	}

	// Neuron - Neuron.

	public List<Connection> getNeuroSynapses() {
		List<Connection> synapses = new ArrayList<Connection>();

		for (Layer layer: this.layers) {

			if (!layer.isInputDataLayer()) {

				for (Node node: layer.getNodes()) {
					synapses.addAll(((Neuron) node).getConnections());
				}
			}
		}
		return synapses;
	}
	public Connection findNeuroSynapse(Neuron from, Neuron to) {
		return this.findSynapseWithin(from, to, this.getNeuroSynapses());
	}


// Weights.


	public Weight getInputDataWeight(InputNode from, Neuron to) {
		Weight data_weight = null;
		Connection input_data_synapse = this.findInputDataSynapse(from, to);

		if (input_data_synapse == null) {
			StringBuilder message = new StringBuilder();
			throw new IllegalArgumentException(message
                                                .append("Data synapse: ")
                                                .append(from.getLabel())
                                                .append(", ")
                                                .append(to.getLabel())
                                                .append(" does not exists")
                                                .toString());
		} else {
			data_weight = input_data_synapse.getWeight();
		}
		return data_weight;
	}
	public Weight getBiasWeight(InputNode from, Neuron to) {
		Weight bias_weight = null;
		Connection bias_synapse = this.findBiasSynapse(from, to);

		if (bias_synapse == null) {
			StringBuilder message = new StringBuilder();
			throw new IllegalArgumentException(message
                                                .append("Bias synapse: ")
                                                .append(from.getLabel())
                                                .append(", ")
                                                .append(to.getLabel())
                                                .append(" does not exists")
                                                .toString());
		} else {
			bias_weight = bias_synapse.getWeight();
		}
		return bias_weight;
	}

	public Weight getNeuroWeight(Neuron from, Neuron to) {
		Weight weight = null;
		Connection neuro_synapse = this.findNeuroSynapse(from, to);

		if (neuro_synapse == null) {
			StringBuilder message = new StringBuilder();
			throw new IllegalArgumentException(message
                                                .append("Neuro synapse: ")
                                                .append(from.getLabel())
                                                .append(", ")
                                                .append(to.getLabel())
                                                .append(" does not exists")
                                                .toString());
		} else {
			weight = neuro_synapse.getWeight();
		}
		return weight;
	}

	public List<Weight> getAllWeights() {
		List<Connection> data_synapses = this.getInputDataSynapses(),
                         bias_synapses = this.getBiasSynapses(),
                         neuro_synapses = this.getNeuroSynapses(),
                         all_synapses = new ArrayList<Connection>();

		all_synapses.addAll(data_synapses);
		all_synapses.addAll(bias_synapses);
		all_synapses.addAll(neuro_synapses);

		List<Weight> all_weights = new ArrayList<Weight>();
		for (Connection synapse: all_synapses) {
			all_weights.add(synapse.getWeight());
		}
		return all_weights;
	}

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
	public boolean isTrained() {
		return this.trained;
	}
	public void setTrained(boolean is) {
		this.trained = is;
	}


// Label configuration.


	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}