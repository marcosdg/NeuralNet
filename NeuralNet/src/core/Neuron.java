package core;

import java.util.ArrayList;
import java.util.List;

import core.activation.ActivationFunction;
import core.input.InputFunction;


public class Neuron {

	private Layer            parentLayer;
	private List<Connection> inputs,
                              outputs;
	
	private double          netInput, // Total input from other Neurons.
                              output,
                              error;    // Local error. Used by supervised learning rules.
	
	private InputFunction      inputFunction;
	private ActivationFunction activationFunction;
	
	private String label;
	
	
	// Creates a Neuron. NO LAYER specified yet.
	
	public Neuron(InputFunction inputFunction, ActivationFunction activationFunction, String label) {
		if (inputFunction != null & activationFunction != null) {
			
			// Connections.
			
			this.inputs = new ArrayList<Connection>();
			this.outputs = new ArrayList<Connection>();
			
			// Default values.
			
			this.netInput = 0;
			this.output = 0;
			this.error = 0;
			
			// Internal functions.
			
			this.inputFunction = inputFunction;
			this.activationFunction = activationFunction;
			
			// Label.
			
			this.label = label;
		}
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
					
					this.inputs.add(input); 
					
					// source_neuron MUST have it as output
				}	
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
}
