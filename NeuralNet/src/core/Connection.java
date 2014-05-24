package core;

import java.util.Random;

public class Connection {

	private Neuron source,
                    target;
	private Weight weight;
	
// Creates a Connection with random weight within interval [min,max].
	
	public Connection(Neuron source, Neuron target, double min, double max, Random generator) {
		if (source != null && target != null) {
			this.source = source;
			this.target = target;
			this.weight = new Weight(min, max, generator);
		} else {
			throw new IllegalArgumentException("Bad parameters to create"+
                                                " Connection");
		}
	}
	
// Creates a Connection with the specified weight.
	
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
	
// Returns the output signal obtained from the Source Neuron.
	
	public double getInput() {
		return this.source.getOutput();
	}
	
// Intensified by weight.
	
	public double getWeightedInput() {
		return this.getInput() * this.weight.getValue();
	}
	
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
			throw new IllegalArgumentException("Bad parameter"+
                                                " to set target Neuron");
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
