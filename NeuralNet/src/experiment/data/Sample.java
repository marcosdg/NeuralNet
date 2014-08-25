package experiment.data;

import java.util.ArrayList;
import java.util.List;

public class Sample {

	private List<Double> input_vector;
	private List<Double> desired_output_vector;


// Creation.


	public Sample() {
		this.input_vector = new ArrayList<Double>();
		this.desired_output_vector = new ArrayList<Double>();
	}


// Input-vector configuration.


	public List<Double> getInputVector() {
		return input_vector;
	}
	public void setInputVector(List<Double> input_vector) {
		this.input_vector = input_vector;
	}
	public void addInput(Double input) {
		this.input_vector.add(input);
	}


// Desired-output-vector configuration.


	public List<Double> getDesiredOutputVector() {
		return desired_output_vector;
	}
	public void setDesiredOutputVector(List<Double> desired_output) {
		this.desired_output_vector = desired_output;
	}
	public void addOutput(Double output) {
		this.desired_output_vector.add(output);
	}
}