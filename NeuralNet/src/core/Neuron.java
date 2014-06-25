package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import core.activation.ActivationFunction;
import core.propagation.PropagationFunction;

/*
 * A Neuron is the essential unit of computation in a Neural Network.
 * 
 * It will combine set of inputs and if a certain threshold is reached
 * the Neuron will fire, producing an output (spike).
 */

public class Neuron extends Node {

	private List<Connection> inputs,
                              outputs;

	private double          netInput, // Total input from other Neurons.
                              output,
                              error; // Local error. Used in supervised learning.

	private PropagationFunction propagationFunction;
	private ActivationFunction activationFunction;


// Creation.

	public Neuron(PropagationFunction propagationFunction, ActivationFunction activationFunction,
                   Layer parentLayer, String label) {
		
		super(parentLayer, label); // Node properties.
		
		if (propagationFunction != null && activationFunction != null) {
			
			// Connections.

			this.inputs = new ArrayList<Connection>();
			this.outputs = new ArrayList<Connection>();

			// Input/Output values.

			this.netInput = 0.0;
			this.output = 0.0;
			this.error = 0.0;

			// Synaptic functions.

			this.propagationFunction = propagationFunction;
			this.activationFunction = activationFunction;
		}
	}

	
// Processing.


	// Combines the inputs to the Neuron.
	
	public void computeInput() {
		if (!this.inputs.isEmpty()) {
			
			this.netInput = this.propagationFunction.getOutput(this.inputs);
		}
	}
	
	// Neuron response to the inputs.
	
	public void computeOutput() {
		
		this.output = this.activationFunction.getOutput(this.netInput);
	}

	
// Inputs configuration.	

	
	public double getNetInput() {
		return this.netInput;
	}
	public void setNetInput(double netInput) {
			this.netInput = netInput;		
	}
	public List<Connection> getInputs() {
		return this.inputs;
	}
	
	// A Neuron may have another Neuron or InputNode as input source.
	
	public boolean hasInputFrom(Node node) {
		boolean has = false;
		
		// Look for node
		
		for (Connection input: this.inputs) {
			
			// Found ?
			
			if (input.getSource() == node) {
				has = true;
				break;
			}
		}
		return has;
	}
	public void addInputConnection(Connection input) {
		Node source_node = input.getSource(); 

		if (input != null) {
			
			// It is pointing to this node ?
			
			if (input.getTarget() == this) {
				
				// New ?
				
				if (!(this.hasInputFrom(source_node))) {
					
					// Add it if new.
					
					this.inputs.add(input); 
					
					// remind add it as output to source_neuron.
				}	
			}
		}
	}
	
	// Randomize input-connections' weights.
	
	public void randomizeWeights(double min, double max, Random generator) {
		if (!this.inputs.isEmpty()) {
		
			for (Connection input: inputs) {
				input.getWeight().randomize(min, max, generator);
			}
		}
	}

	
// Outputs configuration.	

	
	public double getOutput() {
		return this.output;
	}
	public double getOutputDerived() {
		return activationFunction.getOutputDerived(this.netInput);
	}
	public List<Connection> getOutputConnections() {
		return this.outputs;
	}
	
	// A Neuron does not have outputs to InputNodes
	
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
	public void addOutputConnection(Connection output_connection) {
		Node source_node = output_connection.getSource();
		Node target_node = output_connection.getTarget();
		Neuron source_neuron = null;
		Neuron target_neuron = null;
		
		if (output_connection != null) {

			// right type of connection ?
			
			if (isNeuronToNeuron(output_connection)) {

				source_neuron = (Neuron) source_node;
				target_neuron = (Neuron) target_node;

				// It is pointing from this Neuron ?

				if (source_neuron == this) {

					// New ?

					if (!(this.hasOutputTo(target_neuron))) {

						// Add it if new.

						this.outputs.add(output_connection);

						// remind add it as input to target_neuron.
					}
				}
			}
		}
	}	
	public boolean isNeuronToNeuron(Connection connection) {
		return (connection.getSource() instanceof Neuron && 
				 connection.getTarget() instanceof Neuron);
	}
	
	
// Error configuration.
	
	
	public double getError() {
		return this.error;
	}
	public void setError(double error) {
		this.error = error;
	}
}