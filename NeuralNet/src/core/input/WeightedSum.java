package core.input;

import java.util.List;

import core.Connection;

/* 
 * Represents a specific propagation function of the Neuron.
 * 
 * It combines the inputs to the Neuron to calculate the total 'stimuli',
 * which will be forwarded to the activation function.
 */
public class WeightedSum {
	
	public double getOutput(List<Connection> inputs) {
		double output = 0.0;
		
		for (Connection input: inputs) {
			output += input.getWeightedInput();
		}
		return output;
	}
}