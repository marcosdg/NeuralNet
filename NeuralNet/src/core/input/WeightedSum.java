package core.input;

import java.util.List;

import core.Connection;

// Propagation function of the Neuron.
public class WeightedSum {
	
// Combines all the weighted inputs.

	public double getOutput(List<Connection> inputs) {
		double output = 0.0;
		
		for (Connection input: inputs) {
			output += input.getWeightedInput();
		}
		return output;
	}
}