package core;

import java.util.Random;

public class ConnectionBias
{
	private BiasNeuron source;
	private Neuron target;
	private Weight weight;

	public ConnectionBias(BiasNeuron source, Neuron target, Weight weight) {
		if (source != null && target != null && weight != null) {
			this.source = source;
			this.target = target;
			this.weight = weight;
		} else {
			throw new IllegalArgumentException("Bad parameters to create"+
                                                " Connection");
		}
	}

	public Neuron getTarget() {
		return target;
	}

	public void setTarget(Neuron target) {
		this.target = target;
	}

	public Weight getWeight() {
		return weight;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}

	public BiasNeuron getSource() {
		return source;
	}

	public void setSource(BiasNeuron source) {
		this.source = source;
	}
	
	public double getInput()
	{
		return this.source.getNetInput();
	}
	
	public double getWeightedInput()
	{
		return this.getInput() * this.weight.getValue();
	}
	
}
