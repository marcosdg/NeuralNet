package core;

import java.util.Random;

/*
 * A 'Connection' links a 'Source Neuron' to a 'Target Neuron' so that
 * a electric signal can go through it. Such signal can be intensified by
 * means of a 'weight' (synaptic strength).
 */

public class Connection {

	private Neuron source,
                    target;
	private Weight weight;
	
	
// Creation.
	
	
	// Connection with random weight within interval [min,max].
	
	public Connection(Neuron source, Neuron target, double min, double max, Random generator) {
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
	
	public Connection(Neuron source, Neuron target, Weight weight) {
		if (source != null && target != null && weight != null) {
			this.source = source;
			this.target = target;
			this.weight = weight;
		} else {
			throw new IllegalArgumentException("Bad parameters to create"+
                                                " Connection");
		}
	}
	
	// Input/Output Connection
	
	public Connection(Neuron source, Neuron target) {
		this.source = source;
		this.target = target;
	}

	
// Processing.	
	
	
	// Gets the output signal obtained from the Source Neuron.
	
	public double getInput() {
		return this.source.getOutput();
	}
	
	// Intensified by weight.
	
	public double getWeightedInput() {
		return this.getInput() * this.weight.getValue();
	}

	
// Configuration.
	
	
	public Neuron getSource() {
		return this.source;
	}
	public Neuron getTarget() {
		return this.target;
	}
	public Weight getWeight() {
		return this.weight;
	}
	public void setSource(Neuron source) {
		if (source != null) {
			this.source = source;
		} else {
			throw new IllegalArgumentException("Bad parameter to set"+
                                                " source Neuron");
		}
	}
	public void setTarget(Neuron target) {
		if (target != null) {
			this.target = target;
		} else {
			throw new IllegalArgumentException("Bad parameter to set"+
                                                " target Neuron");
		}
	}
	public void setWeight(Weight weight) {
		if (weight!= null) {
			this.weight = weight;
		} else {
			throw new IllegalArgumentException("Bad parameter to set"+
                                                " Neuron's weight");
		}
	}
}