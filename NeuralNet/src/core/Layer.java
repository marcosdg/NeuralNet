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


	public Layer(String label) {
		if (label != null) {
			this.nodes = new ArrayList<Node>();
			this.kind = label;
		}
	}

	public void resetNeurons() {
		for (Node node: this.nodes) {
			if (node instanceof Neuron) {
				((Neuron) node).reset();
			}
		}
	}

// Processing (only layers with neurons).


	public void computeOutput() {

		if (!(this.isInputDataLayer())) {

			if (!(this.nodes.isEmpty())) {

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

		if (!(this.isInputDataLayer())) {

			if (!(this.nodes.isEmpty())) {

				for (Node node : this.nodes) {
					((Neuron) node).randomizeWeights(min, max, generator);
				}
			}
		}
	}
}