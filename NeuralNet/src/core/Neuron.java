package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.activation.ActivationFunction;
import core.activation.Sigmoid;
import core.input.WeightedSum;

public class Neuron {

	private Layer            parentLayer;
	private List<Connection> inputs,
                              outputs;
	
	private double          netInput, // Total input from other Neurons.
                              output,
                              error;    // Local error. Used by supervised learning rules.
	
	private WeightedSum      inputFunction;
	private ActivationFunction activationFunction;
	
	private String label;
	
	
// Creates a Neuron.
	
	public Neuron(WeightedSum inputFunction, ActivationFunction activationFunction, String label) {
		if (inputFunction != null & activationFunction != null) {
			
			// Connections.
			
			this.inputs = new ArrayList<Connection>();
			this.outputs = new ArrayList<Connection>();
			
			// Default values.
			
			this.netInput = 0.0;
			this.output = 0.0;
			this.error = 0.0;
			
			// Internal functions.
			
			this.inputFunction = inputFunction;
			this.activationFunction = activationFunction;
			
			// Label.
			
			this.label = label;
		}
	}
	
// Compute output.
	
	/**
	 * This functions computes the total input of the neuron
	 * ini
	 */
	public void computeNeuronInput()
	{
		if (!this.inputs.isEmpty()) {
			// Propagate inputs.
			this.netInput = this.inputFunction.getOutput(inputs);
		}
	}
	
	/**
	 * This functions computes the total output of the neuron from his netInput
	 * ai <- g(ini)
	 */
	public void computeOutput() {
		// Fire Neuron.
		this.output = this.activationFunction.getOutput(this.netInput);
	}
	
// Layer configuration.
	
	public Layer getParentLayer() {
		return this.parentLayer;
	}
	public void setParentLayer(Layer parentLayer) {
		this.parentLayer = parentLayer;
	}
	
// Inputs configuration.	
	
	public List<Connection> getInputs() {
		return this.inputs;
	}
	public boolean hasInputFrom(Neuron neuron) {
		boolean has = false;
		
		// Look for neuron
		
		for (Connection input: this.inputs) {
			
			// Found ?
			
			if (input.getSource() == neuron) {
				has = true;
				break;
			}
		}
		return has;
	}
	public void addInputConnection(Connection input) {
		Neuron source_neuron = input.getSource(); 
		
		// Null ?
		
		if (input != null) {
			
			// It is pointing to this neuron ?
			
			if (input.getTarget() == this) {
				
				// New ?
				
				if (!this.hasInputFrom(source_neuron)) {
					
					// Add it if not.
					
					this.inputs.add(input); 
					
					// source_neuron MUST have it as output
				}	
			}
		}
	}
	public void randomizeWeights(double min, double max, Random generator) {
		if (generator != null && !this.inputs.isEmpty()) {
		
			for (Connection input: inputs) {
				input.getWeight().randomize(min, max, generator);
			}
		}
	}
	
// Outputs configuration.	
	
	public List<Connection> getOutputs() {
		return this.outputs;
	}
	public boolean hasOutputTo(Neuron neuron) {
		boolean has = false;
		
		// Look for neuron
		
		for (Connection output: this.outputs) {
			
			// Found ?
			
			if (output.getTarget() == neuron) {
				has = true;
				break;
			}
		}
		return has;
	}

	public double getOutput() {
		return this.output;
	}
	
	public double getNetInput()
	{
		return this.netInput;
	}
	
	public double getDerivateOutput()
	{
		if (this.netInput != 0.0) {
			Sigmoid activationFunction = (Sigmoid) this.activationFunction;
			return activationFunction.getOutputDerivative(this.netInput);
		} else {
			throw new IllegalArgumentException("Net inputs not defined");
		}
	}
	
	//For Init Layer only
	public void setNetInput(double netInput)
	{
		this.netInput = netInput;
	}
	
// Error configuration.
	
	public double getError() {
		return this.error;
	}
	public void setError(double error) {
		this.error = error;
	}
	
	
}