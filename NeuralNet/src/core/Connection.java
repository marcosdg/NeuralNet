package core;

import java.util.Random;


/*
 * A 'Connection' links a 'Source Neuron' to a 'Target Neuron' so that
 * a electric signal can go through it. Such signal can be intensified by
 * means of a 'weight' (synaptic strength).
 */

public class Connection {

	private Node   source,
                    target;

	private Weight weight;

	private String label;


// Creation.


	// Connection with random weight within interval [min,max].

	public Connection(Node source, Node target, double min, double max, Random generator, String label) {
		if (source == null || target == null ||
            generator == null || label == null) {

			throw new IllegalArgumentException("Bad parameters to create"+
                    " Connection");
		} else {
			this.source = source;
			this.target = target;
			this.weight = new Weight(min, max, generator);
			this.label = label;
		}
	}

	// Connection with the specified weight.

	public Connection(Node source, Node target, Weight weight, String label) {
		if (source == null || target == null ||
            weight == null || label == null) {

			throw new IllegalArgumentException("Bad parameters to create"+
                    " Connection");
		} else {
			this.source = source;
			this.target = target;
			this.weight = weight;
			this.label = label;
		}
	}

	public void resetWeight() {
		this.weight.reset();
	}


// Processing.


	// Gets the output signal obtained from the Source Node.

	public double getInput() {
		Node source_node = this.source;
		double input = 0.0;

		// Neuron or InputNode ?

		if (source_node instanceof Neuron) {

			input = ((Neuron) source_node).getOutput();

		} else if (source_node instanceof InputNode) {

			input = ((InputNode) source_node).getOutput();
		}
		return input;
	}

	// Intensified by weight (same for Neuron and InputNode).

	public double getWeightedInput() {
		return this.getInput() * this.weight.getValue();
	}


// Nodes configuration.


	public Node getSource() {
		return this.source;
	}
	public void setSource(Node source) {
		if (source == null) {
			throw new IllegalArgumentException("Bad parameter to set"+
                                                " source Node");
		} else {
			this.source = source;
		}
	}

	public Node getTarget() {
		return this.target;
	}
	public void setTarget(Node target) {
		if (target == null) {
			throw new IllegalArgumentException("Bad parameter to set"+
                                                " target Node");
		} else {
			this.target = target;
		}
	}


// Weight configuration.


	// Current.

	public Weight getWeight() {
		return this.weight;
	}
	public void setWeight(Weight weight) {
		if (weight == null) {
			throw new IllegalArgumentException("Bad parameter to set"+
                                                " Connection's weight");
		} else {
			this.weight = weight;
		}
	}


// Label configuration.


	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}


// Kind.


	public boolean isInputNodeToNeuron() {
		return (this.source instanceof InputNode &&
				 this.target instanceof Neuron);
	}
	public boolean isNeuronToNeuron() {
		return (this.source instanceof Neuron &&
				 this.target instanceof Neuron);
	}


// Format.


	public String toString() {
		StringBuilder builder = new StringBuilder();

		return builder
                .append("Connection")
                .append("[").append(this.label).append("]")
                .append(":")
                .append(this.source.getLabel())
                .append(", ")
                .append(this.target.getLabel())
                .toString();
	}
}