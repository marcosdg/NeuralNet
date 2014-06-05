package core.input;

import java.util.List;

import core.Connection;
import core.ConnectionBias;

// Propagation function of the Neuron.
public class WeightedSum {
	
// Combines all the weighted inputs.

	public double getOutput(List<Connection> inputs, ConnectionBias bias) {
		double output = 0.0;
		
		for (Connection input: inputs) {
			output += input.getWeightedInput();
		}
		
		if (bias != null) {
			output += bias.getWeightedInput();
		}
		
		return output;
	}
}