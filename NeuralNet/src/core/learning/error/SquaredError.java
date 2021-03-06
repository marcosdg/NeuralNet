package core.learning.error;

import java.util.ArrayList;
import java.util.List;

import experiment.data.Benchmark;
import experiment.data.Sample;

public class SquaredError {


	// For classification problems.

	public static List<Double> roundAll(List<Double> values) {
		List<Double> rounded = new ArrayList<Double>();

		for (Double value: values) {
			rounded.add(Math.rint(value));
		}
		return rounded;
	}

	// SUM{i,N}: (desired{i} - output{i}) ^ 2

	public static Double getSquaredError(List<Double> desired_output_vector,
                                           List<Double> output_vector,
                                           int num_output_nodes,
                                           boolean classification) {
		List<Double> routputs = SquaredError.roundAll(output_vector);
		Double squared_error = 0.0,
               desired_output,
               output,
               routput;
		int i,
             N = num_output_nodes;

		if (areAdequate(desired_output_vector, output_vector)) {

			for (i = 0; i < N; i += 1) {
				desired_output = desired_output_vector.get(i);
				output = output_vector.get(i);
				routput = routputs.get(i);

				squared_error += (classification) ?
                                   Math.pow((desired_output - routput), 2)
                                 : Math.pow((desired_output - output), 2);
			}
		}
		return squared_error;
	}
	public static boolean areAdequate(List<Double> desired_output_vector,
                                         List<Double> output_vector) {
		boolean are = false;

		// empty ?

		if (desired_output_vector.isEmpty() || output_vector.isEmpty()) {

			throw new IllegalArgumentException("Eempty vectors.");

		// different sizes ?

		} else if (desired_output_vector.size() != output_vector.size()) {

			throw new IllegalArgumentException("Vectors of different sizes");

		} else {
			are = true;
		}
		return are;
	}

	// Normalized Average Squared Error of all the samples.

	public static Double getSquaredErrorPercentage(List<Sample> samples,
                                                List<List<Double>> output_vectors,
                                                int num_output_nodes,
                                                boolean classification) {
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
                                            N,
                                            classification);
			total_squared_error += squared_error;
		}
		return (norm_factor * total_squared_error);
	}

	public static boolean isClassificationMiss(List<Double> desired_output_vector,
                                                    List<Double> output_vector) {
		Double desired_output = null,
               output = null;
		boolean missed = false;

		if (areAdequate(desired_output_vector, output_vector)) {

			for (int i = 0; i < output_vector.size(); i += 1) {
				desired_output = desired_output_vector.get(i);
				output = output_vector.get(i);

				if (!desired_output.equals(output)) {
					missed = true;
					break;
				}
			}
		}
		return missed;
	}
	public static int getNumberOfClassificationMisses(List<Sample> samples,
                                             List<List<Double>> output_vectors) {
		List<Double> rdesired_output_vector = null,
                     routput_vector = null;
        int misses = 0;

		if (samples.size() != output_vectors.size()) {
			throw new IllegalArgumentException("Same number of samples as" +
                                                " number of output vectors" +
                                                 " is required.");
		} else {
			for (int i = 0; i < samples.size(); i += 1) {
				rdesired_output_vector = SquaredError
                                         .roundAll(samples
                                                   .get(i)
                                                   .getDesiredOutputVector());
				routput_vector = SquaredError.roundAll(output_vectors.get(i));

				if (isClassificationMiss(rdesired_output_vector, routput_vector)) {
					misses += 1;
				}
			}
		}
		return misses;
	}
}