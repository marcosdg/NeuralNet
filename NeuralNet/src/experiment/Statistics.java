package experiment;

import java.util.ArrayList;
import java.util.List;

import core.NeuralNetwork;
import core.learning.Backpropagation;
import core.learning.error.SquaredError;
import experiment.data.Benchmark;
import experiment.data.Sample;

public class Statistics {

	private Backpropagation done_backprop;
	private NeuralNetwork trained_net;

	private Double ete; // error test set.
	private List<List<Double>> test_output_vectors;


// Creation.


	public Statistics(NeuralNetwork net) {
		if (!net.isTrained()) {
			throw new IllegalArgumentException("Must train the net first");
		} else {
			this.trained_net = net;
			this.done_backprop = (Backpropagation) net.getLearningRule();
			this.ete = 0.0;
			this.test_output_vectors = new ArrayList<List<Double>>();
		}
	}


// Neural Network configuration.


	public NeuralNetwork getNeuralNetwork() {
		return this.trained_net;
	}
	public void setTrainedNet(NeuralNetwork net) {
		if (net == null || !net.isTrained()) {
			throw new IllegalArgumentException("Empty or not a trained net");
		} else {
			this.trained_net = net;
		}
	}


// Data to work on.


	// Errors on training set.

	public List<Double> getEtrs() {
		return this.done_backprop.getEtrsRecord();
	}

	// Errors on validation set.

	public List<Double> getEvas() {
		return this.done_backprop.getEvasRecord();
	}

	// Errors on test set.

	public Double getEte() {
		return this.ete;
	}
	public void computeEte(Benchmark bench) {
		List<Sample> test_samples = bench.getTestSamples();
		List<List<Double>> output_vectors = this
                                            .computeTestOutputVectors(test_samples);
		this.ete = this
                   .done_backprop
                   .getEarlyStop()
                   .getAverageErrorPerTestSample(output_vectors);
	}

	// Generalization losses.

	public List<Double> getGLs() {
		return this.done_backprop.getGLsRecord();
	}

	// Training progresses.

	public List<Double> getPKs() {
		return this.done_backprop.getPKsRecord();
	}


// Stats.


	// General.

	public static Double sumAllDoubles(List<Double> values) {
		Double total = 0.0;

		for (Double value: values) {
			total += value;
		}
		return total;
	}
	public static Integer sumAllIntegers(List<Integer> values) {
		Integer total = 0;

		for (Integer value: values) {
			total += value;
		}
		return total;
	}
	public static Double getMinDouble(List<Double> values) {
		Double min = values.get(0);

		for (Double value: values) {
			min = (value < min) ? value : min;
		}
		return min;
	}
	public static Integer getMinInteger(List<Integer> values) {
		Integer min = values.get(0);

		for (Integer value: values) {
			min = (value < min) ? value : min;
		}
		return min;
	}
	public static Double getMaxDouble(List<Double> values) {
		Double max = values.get(0);

		for (Double value: values) {
			max = (value > max) ? value : max;
		}
		return max;
	}
	public static Integer getMaxInteger(List<Integer> values) {
		Integer max = values.get(0);

		for (Integer value: values) {
			max = (value > max) ? value : max;
		}
		return max;
	}
	public static Double getDoubleAverage(List<Double> values) {
		return (Statistics.sumAllDoubles(values) / values.size());
	}
	public static Integer getIntegerAverage(List<Integer> values) {
		return (Statistics.sumAllIntegers(values) / values.size());
	}
	public static List<Double> getDoubleSquareDifferencesFromAvg(List<Double> values) {
		Double avg = Statistics.getDoubleAverage(values);
		List<Double> sqdiffs = new ArrayList<Double>();

		for (Double value : values) {
			sqdiffs.add(Math.pow((value - avg), 2));
		}
		return sqdiffs;
	}
	public static List<Double> getIntegerSquareDifferencesFromAvg(List<Integer> values) {
		Integer avg = Statistics.getIntegerAverage(values);
		List<Double> sqdiffs = new ArrayList<Double>();

		for (Integer value : values) {
			sqdiffs.add(Math.pow((value - avg), 2));
		}
		return sqdiffs;
	}
	public static Double getDoubleStandarDeviation(List<Double> values) {
		List<Double> sqdiffs = Statistics.getDoubleSquareDifferencesFromAvg(values);

		return Math.sqrt(Statistics.getDoubleAverage(sqdiffs));
	}
	public static Double getIntegerStandarDeviation(List<Integer> values) {
		List<Double> sqdiffs = Statistics.getIntegerSquareDifferencesFromAvg(values);

		return Math.sqrt(Statistics.getDoubleAverage(sqdiffs));
	}
	/*
	 * Classification.
	 *
	 * After training the net on different PROBEN1 benchmarks (eg. horse1,
	 * horse2, horse3) we must say explicitly which one to use for test.
	 */
	public int getNumberOfClassificationMissesOnTraining(Benchmark bench) {
		List<Sample> training_samples = bench.getTrainingSamples();
		List<List<Double>> training_outputs = this
                                              .done_backprop
                                              .getTrainingOutputVectors();

		return SquaredError.getNumberOfClassificationMisses(training_samples,
                                                             training_outputs);
	}
	public int getNumberOfClassificationMissesOnValidation(Benchmark bench) {
		List<Sample> validation_samples = bench.getValidationSamples();
		List<List<Double>> validation_outputs = this
                                                .done_backprop
                                                .getValidationOutputVectors();

		return SquaredError.getNumberOfClassificationMisses(validation_samples,
                                                             validation_outputs);
	}
	public int getNumberOfClassificationMissesOnTest(Benchmark bench) {
		List<Sample> test_samples = bench.getTestSamples();
		List<List<Double>> test_outputs = this
                                          .computeTestOutputVectors(test_samples);

		return SquaredError.getNumberOfClassificationMisses(test_samples,
                                                             test_outputs);
	}


// Test output vectors.


	public List<List<Double>> getTestOutputVectors() {
		return this.test_output_vectors;
	}
	public void saveTestOutputVector(List<Double> output_vector) {
		this.test_output_vectors.add(output_vector);
	}
	/*
	 * Now, the 'trained net' will be at its minimum GL, or equivalently,
	 * at its minimum validation error.
	 */
	public List<List<Double>> computeTestOutputVectors(List<Sample> test_samples) {
		List<List<Double>> output_vectors = new ArrayList<List<Double>>();
		List<Double> output_vector = null;

		NeuralNetwork net_copy = this.trained_net.copy();

		for (Sample test_sample: test_samples) {
			output_vector = net_copy.computeOutput(test_sample);
			output_vectors.add(output_vector);
		}
		return output_vectors;
	}


// Epochs.


	public int getNumberOfTrainingEpochs() {
		return this.done_backprop.getCurrentEpoch();
	}
	public int getNumberOfRelevantEpochs() {
		return this.done_backprop.getNumberOfRelevantEpochs();
	}
}