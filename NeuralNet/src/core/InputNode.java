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

    // To distinguish Input-Data nodes from Bias nodes

    private String kind;
    private static final String INPUT_DATA_NODE = "Input Data Node",
                                   BIAS_NODE = "Bias Node";


// Creation.


	public InputNode(Layer parentLayer, String label, String kind) {

		super(parentLayer, label); // Node properties.

		this.input_data = 0.0;
		this.outputs = new ArrayList<Connection>();
		this.kind = kind;
	}
	public InputNode copy() {
		InputNode copy = new InputNode(getParentLayer(),
                                        getLabel(),
                                        this.kind);
		List<Connection> outputs_copy = new ArrayList<Connection>();
		outputs_copy.addAll(this.outputs);

		copy.setInputData(this.input_data);
		copy.setOutputConnections(outputs_copy);

		return copy;
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
	public void setOutputConnections(List<Connection> outputs) {
		this.outputs = outputs;
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


// Kind configuration.


	public String getKind() {
		return this.kind;
	}
	public void setKind(String kind) {
		if (kind == null) {
			throw new IllegalArgumentException("InputNode: kind must be not null");
		}
	}

	public static String INPUT_DATA_NODE() {
		return InputNode.INPUT_DATA_NODE;
	}
	public static String BIAS_NODE() {
		return InputNode.BIAS_NODE;
	}

	public boolean isInputDataNode() {
		return this.kind == InputNode.INPUT_DATA_NODE;
	}
	public boolean isBiasNode() {
		return this.kind == InputNode.BIAS_NODE;
	}
}