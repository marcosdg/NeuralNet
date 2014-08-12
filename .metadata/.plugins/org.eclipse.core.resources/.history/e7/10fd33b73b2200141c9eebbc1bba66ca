package core;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents the kind of nodes which only forward input data into the
 * Neurons.
 */

public class InputNode extends Node {

	private double	          input_data;
    private List<Connection> outputs;


// Creation.


	public InputNode(Layer parentLayer, String label) {

		super(parentLayer, label); // Node properties.

		this.input_data = 0.0;
		this.outputs = new ArrayList<Connection>();

	}


// Input Data configuration.


	public double getInputData() {
		return this.input_data;
	}
	public void setInputData(double data) {
		this.input_data = data;
	}


// Outputs configuration.


	public double getOutput() {
		return this.input_data;
	}
	public List<Connection> getOutputConnections() {
		return this.outputs;
	}

	// An InputNode does not have outputs to InputNodes

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
		InputNode input_node = null;
		Neuron target_neuron = null;

		if (output_connection != null) {

			// right type of connection ?

			if (output_connection.isInputNodeToNeuron()) {

				input_node = (InputNode) source_node;
				target_neuron = (Neuron) target_node;

				// It is pointing from this InputNode ?

				if (input_node == this) {

					// New ?

					if (!(this.hasOutputTo(target_neuron))) {

						this.outputs.add(output_connection);

						// remind add it as input to target_neuron.
					}
				}
			}
		}
	}
}