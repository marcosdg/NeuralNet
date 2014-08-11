package core.learning;

import java.util.ArrayList;
import java.util.List;

import core.learning.stop.StopCriteria;

//TODO: COMPLETE SUPERVISEDLEARNING CLASS

abstract public class SupervisedLearning extends LearningRule {


	private double learning_rate;
	private List<StopCriteria> stop_criterias;

	private int current_epoch;

	// Record of all the evas up to current epoch.

	private List<Double> evas,

	// 'Buffer' with all the etrs during each strip.

                          etrs;

	// Record of all outputs obtained up to current epoch.

	private List<List<Double>> output_vectors;


// Creation.


	public SupervisedLearning() {
	}


// Learning rate configuration.


	public double getLearningRate() {
		return this.learning_rate;
	}
	public void setLearninRate(double learning_rate) {
		this.learning_rate = learning_rate;
	}


// Stop criterias configuration.


	public List<StopCriteria> getStopCriterias() {
		return this.stop_criterias;
	}
	public void setStopCriterias(List<StopCriteria> criterias) {
		this.stop_criterias = criterias;
	}
	public void addStopCriteria(StopCriteria criteria) {
		if (criteria == null) {
			throw new IllegalArgumentException("Stop criteria must not be null");
		} else {
			this.stop_criterias.add(criteria);
		}
	}

	public boolean anyStopCriteriasMet() {
		boolean met = false;

		for (StopCriteria criteria: this.stop_criterias) {
			if (criteria.isMet()) {
				met = true;

			}
		}
		return met;
	}


// Epochs configuration.


	public int getCurrentEpoch() {
		return this.current_epoch;
	}
	public void setCurrentEpoch(int epoch) {
		this.current_epoch = epoch;
	}


// Errors.


	// To simplify the calculation of the weights' corrections (deltas).

	public List<Double> getOutputErrorVector(List<Double> desired_output_vector,
                                              List<Double> output_vector) {
		Double error = 0.0;
		List<Double> error_vector = new ArrayList<Double>();
		int num_nodes = this
                         .getNeuralNetwork()
                         .getOutputLayer()
                         .numberOfNodes();

		for (int i = 0; i < num_nodes; i += 1) {
			error = desired_output_vector.get(i) - output_vector.get(i);
			error_vector.add(error);
		}
		return error_vector;
	}

	// Evas.

	public List<Double> getEvasRecord() {
		return this.evas;
	}
	public void addEva(Double eva) {
		this.evas.add(eva);
	}

	// Etrs.

	public List<Double> getEtrsRecord() {
		return this.etrs;
	}
	public void addEtr(Double etr) {
		this.etrs.add(etr);
	}
	public void clearEtrs() {
		this.etrs.clear();
	}


// Output vectors.


	public List<List<Double>> getOutputVectorsRecord() {
		return this.output_vectors;
	}
	public void addOutputVector(List<Double> output_vector) {
		this.output_vectors.add(output_vector);
	}
}