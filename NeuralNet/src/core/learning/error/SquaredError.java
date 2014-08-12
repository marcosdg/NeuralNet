package core.learning.error;

import java.util.List;

import core.data.Benchmark;
import core.data.Sample;

public class SquaredError {


// SUM{i,N}: (desired{i} - output{i}) ^ 2


	public static Double getSquaredError(List<Double> desired_output_vector,
                                           List<Double> output_vector,
                                           int num_output_nodes) {
		Double squared_error = 0.0,
               desired_output,
               output;
		int i,
             N = num_output_nodes;

		if (areAdequate(desired_output_vector, output_vector)) {

			for (i = 0; i < N; i += 1) {
				desired_output = desired_output_vector.get(i);
				output = output_vector.get(i);

				squared_error += Math.pow((desired_output - output), 2);
			}
		}
		return squared_error;
	}
	public static boolean areAdequate(List<Double> desired_output_vector,
                                         List<Double> output_vector) {
		boolean are = false;

		// empty ?

		if (desired_output_vector.isEmpty() || output_vector.isEmpty()) {

			throw new IllegalArgumentException("SquaredError: empty vectors.");

		// different sizes ?

		} else if (desired_output_vector.size() != output_vector.size()) {

			throw new IllegalArgumentException("SquaredError: vectors of" +
                                                " different sizes.");
		} else {
			are = true;
		}
		return are;
	}


// Mean Squared Error


	public 	Double getMeanSquaredError(List<Double> desired_output_vector,
                                       List<Double> output_vector,
                                       int num_output_nodes) {

		int N = num_output_nodes;
		Double squared_error = getSquaredError(desired_output_vector,
                                               output_vector,
                                               N);
		return squared_error / N;
	}

// Normalized Average Squared Error of all the samples.


	public static Double getSquaredErrorPercentage(List<Sample> samples,
                                                     List<List<Double>> output_vectors,
                                                     int num_output_nodes) {
		int N = num_output_nodes,
            P = samples.size();

		Double squared_error = 0.0,
		       total_squared_error = 0.0,
               norm_factor = 0.0,
               output_min = Benchmark.getMinDesiredOutputValue(),
               output_max = Benchmark.getMaxDesiredOutputValue();

		List<Double> desired_output_vector = null,
                     output_vector = null;


		norm_factor = 100 * ((output_max - output_min) / (N * P));

		for (int i = 0; i < P; i += 1) {
			desired_output_vector = samples.get(i).getDesiredOutputVector();
			output_vector = output_vectors.get(i);
			squared_error = getSquaredError(desired_output_vector,
                                            output_vector,
                                            N);
			total_squared_error += squared_error;
		}
		return norm_factor * total_squared_error;
	}
}