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
	
	
// Creation.
	
	
	// Connection with random weight within interval [min,max].
	
	public Connection(Node source, Node target, double min, double max, Random generator) {
		if (source != null && target != null && generator != null) {
			this.source = source;
			this.target = target;
			this.weight = new Weight(min, max, generator);
		} else {
			throw new IllegalArgumentException("Bad parameters to create"+
                                                " Connection");
		}
	}
	
	// Connection with the specified weight.
	
	public Connection(Node source, Node target, Weight weight) {
		if (source != null && target != null && weight != null) {
			this.source = source;
			this.target = target;
			this.weight = weight;
		} else {
			throw new IllegalArgumentException("Bad parameters to create"+
                                                " Connection");
		}
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

	
// Configuration.
	
	
	public Node getSource() {
		return this.source;
	}
	public Node getTarget() {
		return this.target;
	}
	public Weight getWeight() {
		return this.weight;
	}
	public void setSource(Node source) {
		if (source != null) {
			this.source = source;
		} else {
			throw new IllegalArgumentException("Bad parameter to set"+
                                                " source Node");
		}
	}
	public void setTarget(Node target) {
		if (target != null) {
			this.target = target;
		} else {
			throw new IllegalArgumentException("Bad parameter to set"+
                                                " target Node");
		}
	}
	public void setWeight(Weight weight) {
		if (weight!= null) {
			this.weight = weight;
		} else {
			throw new IllegalArgumentException("Bad parameter to set"+
                                                " Connection's weight");
		}
	}
}