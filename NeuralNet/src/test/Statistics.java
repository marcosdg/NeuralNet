package test;

import java.util.ArrayList;
import java.util.List;

import core.NeuralNetwork;
import core.data.Benchmark;
import core.data.Sample;
import core.learning.Backpropagation;
import core.learning.error.SquaredError;

public class Statistics {

	private Backpropagation done_backprop;
	private NeuralNetwork trained_net;
	private List<Double> etes; // errors test set.
	private List<List<Double>> test_output_vectors;


// Creation.


	public Statistics(NeuralNetwork net) {
		if (!net.isTrained()) {
			throw new IllegalArgumentException("Must train the net first");
		} else {
			this.trained_net = net;
			this.done_backprop = (Backpropagation) net.getLearningRule();
			this.etes = new ArrayList<Double>();
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

	public List<Double> getEtes() {
		return this.etes;
	}
	public void saveEte(Double ete) {
		if (ete != null) {
			this.etes.add(ete);
		}
	}
	public Double computeEte(Benchmark bench) {
		List<Sample> test_samples = bench.getTestSamples();
		List<List<Double>> output_vectors = this
                                            .computeTestOutputVectors(test_samples);
		return this
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

	public Double sumAll(List<Double> values) {
		Double total = 0.0;

		for (Double value: values) {
			total += value;
		}
		return total;
	}
	public Double getMin(List<Double> values) {
		Double min = values.get(0);

		for (Double value: values) {
			min = (value < min) ? value : min;
		}
		return min;
	}
	public Double getMax(List<Double> values) {
		Double max = values.get(0);

		for (Double value: values) {
			max = (value > max) ? value : max;
		}
		return max;
	}

	public Double getAverage(List<Double> values) {
		return (sumAll(values) / values.size());
	}
	public List<Double> getSquareDifferencesFromAvg(List<Double> values) {
		Double avg = this.getAverage(values);
		List<Double> sqdiffs = new ArrayList<Double>();

		for (Double value : values) {
			sqdiffs.add(Math.pow((value - avg), 2));
		}
		return sqdiffs;
	}
	public Double getStandarDeviation(List<Double> values) {
		List<Double> sqdiffs = this.getSquareDifferencesFromAvg(values);

		return Math.sqrt(this.getAverage(sqdiffs));
	}

	/* Classification.
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
	public List<List<Double>> computeTestOutputVectors(List<Sample> test_samples) {
		List<List<Double>> output_vectors = new ArrayList<List<Double>>();
		List<Double> output_vector = null;

		// Clone the net not to mess the original one.

		NeuralNetwork net_copy = this.trained_net.copy();

		for (Sample test_sample: test_samples) {
			output_vector = net_copy.computeOutput(test_sample);
			output_vectors.add(output_vector);
		}
		return output_vectors;
	}
}