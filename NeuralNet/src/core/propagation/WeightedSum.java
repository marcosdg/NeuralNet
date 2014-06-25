package core.propagation;

import java.util.List;

import core.Connection;

/* 
 * Represents a specific propagation function of the Neuron.
 * 
 * It combines the inputs to the Neuron to calculate the total 'stimuli',
 * which will be forwarded to the activation function.
 */
public class WeightedSum extends PropagationFunction{
	
	public WeightedSum() {
	}
	
	public double getOutput(List<Connection> connection_inputs) {
		double output = 0.0;
		
		for (Connection connection_input: connection_inputs) {
			output += connection_input.getWeightedInput();
		}
		return output;
	}
}