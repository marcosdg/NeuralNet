package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
 * Represents a collection of Neurons.
 */

public class Layer {

	private List<Node> nodes;
	private String kind;

	// Kinds:

	public static final String
		INPUT_DATA_LAYER = "Input Data",
		INITIAL_LAYER = "Initial",
		HIDDEN_LAYER = "Hidden",
        OUTPUT_LAYER = "Output";


// Creation.


	public Layer(String kind) {
		if (kind != null) {
			this.nodes = new ArrayList<Node>();
			this.kind = kind;
		}
	}
	public Layer copy() {
		Layer copy = new Layer(this.kind);
		List<Node> nodes_copy = new ArrayList<Node>();

		for (Node node: this.nodes) {

			// copy input nodes ?

			if (this.isInputDataLayer()) {
				nodes_copy.add(((InputNode) node).copy());

			// copy neurons ?

			} else {
				nodes_copy.add(((Neuron) node).copy());
			}
		}
		copy.setNodes(nodes_copy);
		return copy;
	}

	public void resetNeurons() {
		for (Neuron neuron : this.getNeurons()) {
			neuron.reset();
		}
	}


// Processing (only layers with neurons).


	public void computeOutput() {
		if (!this.isInputDataLayer()) {
			if(!this.nodes.isEmpty()) {
				for (Node node: this.nodes) {
					((Neuron) node).computeInput();
					((Neuron) node).computeOutput();
				}
			}
		}
	}


// Nodes configuration.


	public List<Node> getNodes() {
		return this.nodes;
	}
	public void setNodes(List<Node> those) {
		this.nodes = those;
	}
	public int numberOfNodes() {
		return this.nodes.size();
	}
	public void addNode(Node node) {
		if (!(this.nodes.contains(node))) {

			this.nodes.add(node);
		}
	}
	public void addNodeAt(int at, Node node) {
		if (!this.nodes.contains(node)) {

			this.nodes.add(at, node);
		}
	}
	public boolean removeNodeAtPosition(int at) {
		boolean removed = false;

		if (this.nodes.get(at) != null) {

			this.nodes.remove(at);
			removed = true;
		}
		return removed;
	}
	public boolean removeNode(Node node) {
		boolean removed = false;

		if (this.nodes.contains(node)) {

			this.nodes.remove(node);
			removed = true;
		}
		return removed;
	}
	public boolean hasNode(Node node) {
		return this.nodes.contains(node);
	}

	// InputNodes.

	public List<InputNode> getInputDataNodes() {
		InputNode input_node = null;
		List<InputNode> input_data_nodes = new ArrayList<InputNode>();

		if (!this.isInputDataLayer()) {
			throw new IllegalStateException("Input data nodes can only be" +
                                             " accessed in input data layers");
		} else if (this.nodes.isEmpty()) {
			throw new IllegalStateException("Empty input data layer");
		} else {

			for (Node node: this.nodes) {
				input_node = (InputNode) node;
				if (input_node.isInputDataNode()) {
					input_data_nodes.add(input_node);
				}
			}
		}
		return input_data_nodes;
	}
	public List<InputNode> getBiasNodes() {
		InputNode input_node = null;
		List<InputNode> biases = new ArrayList<InputNode>();

		if (!this.isInputDataLayer()) {
			throw new IllegalStateException("Bias nodes can only be" +
                                             " accessed in input data layers");
		} else if (this.nodes.isEmpty()) {
			throw new IllegalStateException("Empty input data layer");
		} else {

			for (Node node: this.nodes) {
				input_node = (InputNode) node;
				if (input_node.isBiasNode()) {
					biases.add(input_node);
				}
			}
		}
		return biases;
	}

	// Neurons.

	public List<Neuron> getNeurons() {
		List<Neuron> neurons = new ArrayList<Neuron>();

		if (this.isInputDataLayer()) {
			throw new IllegalStateException("Not applicable to input data layer");
		} else if (this.nodes.isEmpty()) {
			throw new IllegalStateException("Empty layer");
		} else {

			for (Node node: this.nodes) {
				neurons.add((Neuron) node);
			}
		}
		return neurons;
	}

	public List<Double> getOutputsDerived() {
		List<Double> outputs_derived = new ArrayList<Double>();

		for (Neuron neuron : this.getNeurons()) {
			outputs_derived.add(neuron.getOutputDerived());
		}
		return outputs_derived;
	}


// Kind configuration.


	public String getKind() {
		return this.kind;
	}
	public void setKind(String label) {
		this.kind = label;
	}

	public boolean isInputDataLayer() {
		return this.kind == Layer.INPUT_DATA_LAYER;
	}
	public boolean isInitialLayer() {
		return this.kind == Layer.INITIAL_LAYER;
	}
	public boolean isHiddenLayer() {
		return this.kind == Layer.HIDDEN_LAYER;
	}
	public boolean isOutputLayer() {
		return this.kind == Layer.OUTPUT_LAYER;
	}


// Randomization (only layers with neurons).


	public void randomizeWeights(double min, double max, Random generator) {
		if (!this.isInputDataLayer()) {
			for (Neuron neuron : this.getNeurons()) {
				neuron.randomizeWeights(min, max, generator);
			}
		}
	}
}