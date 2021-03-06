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
	public Neuron copy() {
		Neuron copy = new Neuron(this.propagationFunction,
                                  this.activationFunction,
                                  getParentLayer(),
                                  getLabel());
		List<Connection> inputs_copy = new ArrayList<Connection>(),
                         outputs_copy = new ArrayList<Connection>();
		inputs_copy.addAll(this.inputs);
		outputs_copy.addAll(this.outputs);

		copy.setInputs(inputs_copy);
		copy.setOutputs(outputs_copy);
		copy.setNetInput(this.netInput);
		copy.setOutput(this.output);
		copy.setError(this.error);
		return copy;
	}

	public void reset() {
		this.netInput = 0.0;
		this.output = 0.0;
		this.error = 0.0;

		for (Connection input_connection: this.inputs) {
			input_connection.resetWeight();
		}
		for (Connection output_connection: this.outputs) {
			output_connection.resetWeight();
		}
	}


// Processing.


	// Combines the Neuron's inputs.

	public void computeInput() {
		if (this.inputs.isEmpty()) {
			throw new IllegalStateException("Neuron " + getLabel() +
                                             " has no inputs set." );
		} else {
			this.netInput = this.propagationFunction.getOutput(this.inputs);
		}
	}

	// Neuron's response to the inputs.

	public void computeOutput() {
		this.output = this.activationFunction.getOutput(this.netInput);
	}

// Connections.

	public List<Connection> getConnections() {
		List<Connection> connections = new ArrayList<Connection>(this.inputs);
		connections.addAll(this.outputs);

		return connections;
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
	public void setInputs(List<Connection> input_connections) {
		this.inputs = input_connections;
	}

	// A Neuron may have another Neuron or InputNode as input source.

	public boolean hasInputFrom(Node node) {
		boolean has = false;

		for (Connection input: this.inputs) {

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
	public void setOutput(double output) {
		this.output = output;
	}
	public double getOutputDerived() {
		return activationFunction.getOutputDerived(this.netInput);
	}
	public List<Connection> getOutputs() {
		return this.outputs;
	}
	public void setOutputs(List<Connection> output_connections) {
		this.outputs = output_connections;
	}

	// A Neuron does not have outputs to InputNodes

	public boolean hasOutputTo(Neuron neuron) {
		boolean has = false;

		for (Connection output: this.outputs) {

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

		if (output_connection != null) {

			// Right type of connection ?

			if (output_connection.isNeuronToNeuron()) {

				Neuron source_neuron = (Neuron) source_node;
				Neuron target_neuron = (Neuron) target_node;

				// It is pointing from this Neuron ?

				if (source_neuron == this) {

					// New ?

					if (!(this.hasOutputTo(target_neuron))) {

						this.outputs.add(output_connection);

						// remind add it as input to target_neuron.
					}
				}
			}
		}
	}


// Error configuration.


	public double getError() {
		return this.error;
	}
	public void setError(double error) {
		this.error = error;
	}
}