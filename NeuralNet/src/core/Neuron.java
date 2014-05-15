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
	// TODO: complete.
}
