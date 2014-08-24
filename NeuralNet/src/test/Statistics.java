package test;

import java.util.ArrayList;
import java.util.List;

import core.NeuralNetwork;
import core.learning.Backpropagation;

public class Statistics {

	private Backpropagation done_backprop;
	private NeuralNetwork trained_net;
	private List<Double> etts; // errors test set.


// Creation.


	public Statistics(NeuralNetwork net) {
		if (!net.isTrained()) {
			throw new IllegalArgumentException("Must train the net first");
		} else {
			this.trained_net = net;
			this.done_backprop = (Backpropagation) net.getLearningRule();
			this.etts = new ArrayList<Double>();
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

	public List<Double> getEtts() {
		return this.etts;
	}
	public void saveEtt(Double ett) {
		if (ett != null) {
			this.etts.add(ett);
		}
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


	public static Double sumAll(List<Double> values) {
		Double total = 0.0;

		for (Double value: values) {
			total += value;
		}
		return total;
	}
	public static Double getMin(List<Double> values) {
		Double min = values.get(0);

		for (Double value: values) {
			min = (value < min) ? value : min;
		}
		return min;
	}
	public static Double getMax(List<Double> values) {
		Double max = values.get(0);

		for (Double value: values) {
			max = (value > max) ? value : max;
		}
		return max;
	}

	public static Double getAverage(List<Double> values) {
		return (sumAll(values) / values.size());
	}
	public static List<Double> getSquareDifferencesFromAvg(List<Double> values) {
		Double avg = Statistics.getAverage(values);
		List<Double> sqdiffs = new ArrayList<Double>();

		for (Double value : values) {
			sqdiffs.add(Math.pow((value - avg), 2));
		}
		return sqdiffs;
}
	public static Double getStandarDeviation(List<Double> values) {
		List<Double> sqdiffs = Statistics.getSquareDifferencesFromAvg(values);

		return Math.sqrt(Statistics.getAverage(sqdiffs));
	}
}