package core.learning.error;

import java.util.ArrayList;
import java.util.List;

public class SquaredError {

	private List<Double> desired_output_vector, // from sample.
                          output_vector;         // from network.


// Creation.


	public SquaredError() {
		this.desired_output_vector = new ArrayList<Double>();
		this.output_vector = new ArrayList<Double>();
	}


// SUM{i,n}: (desired{i} - output{i}) ^ 2


	public Double getSquaredError() {
		Double squared_error = 0.0,
               desired_output,
               output;
		int i, n; // sum from, to.

		if (areAdequate(this.desired_output_vector, this.output_vector)) {
			n = this.output_vector.size(); // desired_output_vector works too.

			for (i = 0; i < n; i += 1) {
				desired_output = this.desired_output_vector.get(i);
				output = this.output_vector.get(i);

				squared_error += Math.pow((desired_output - output), 2);
			}
		}
		return squared_error;
	}

	public boolean areAdequate(List<Double> desired_output_vector,
                                 List<Double> output_vector) {
		boolean are = false;

		// empty ?

		if (this.desired_output_vector.isEmpty() ||
            this.output_vector.isEmpty()) {

			throw new IllegalArgumentException("SquaredError: empty vectors.");

		// different sizes ?

		} else if (this.desired_output_vector.size() !=
                    this.output_vector.size()) {

			throw new IllegalArgumentException("SquaredError: vectors of" +
                                                " different sizes.");
		} else {
			are = true;
		}
		return are;
	}


// Desired vector configuration.


	public List<Double> getDesiredOutputVector() {
		return this.desired_output_vector;
	}
	public void setDesiredOutputVector(List<Double> desired_output_vector) {
		this.desired_output_vector = desired_output_vector;
	}


// Output vector configuration.


	public List<Double> getOutputVector() {
		return this.output_vector;
	}
	public void setOutputVector(List<Double> output_vector) {
		this.output_vector = output_vector;
	}
}