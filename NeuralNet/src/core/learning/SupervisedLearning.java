package core.learning;

import java.util.List;

import core.learning.stop.EarlyStop;
import core.learning.stop.MaxEpochsStop;
import core.learning.stop.StopCriteria;

//TODO: COMPLETE SUPERVISEDLEARNING CLASS

abstract public class SupervisedLearning extends LearningRule {


	private double learning_rate;
	private int current_epoch;
	private List<StopCriteria> stop_criterias;

	// Record of all the evas up to current epoch.

	private List<Double> evas,

	// 'Buffer' with all the etrs during each strip.

                          etrs;

	// Record of all outputs obtained up to current epoch.

	private List<List<Double>> output_vectors;



// Learning rate configuration.


	public double getLearningRate() {
		return this.learning_rate;
	}
	public void setLearninRate(double learning_rate) {
		this.learning_rate = learning_rate;
	}


// Epochs configuration.


	public int getCurrentEpoch() {
		return this.current_epoch;
	}
	public void setCurrentEpoch(int epoch) {
		this.current_epoch = epoch;
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

	// Get them individually,

	public MaxEpochsStop getMaxEpochsStop() {
		MaxEpochsStop max_epochs_stop = null;

		for (StopCriteria criteria: this.stop_criterias) {

			if (StopCriteria.isMaxEpochsStop(criteria)) {
				max_epochs_stop = (MaxEpochsStop) criteria;
			}
		}
		return max_epochs_stop;
	}
	public EarlyStop getEarlyStop() {
		EarlyStop early_stop = null;

		for (StopCriteria criteria: this.stop_criterias) {

			if (StopCriteria.isEarlyStop(criteria)) {
				early_stop = (EarlyStop) criteria;
			}
		}
		return early_stop;
	}

	/*
	public boolean anyStopCriteriasMet() {
		boolean met = false;

		for (StopCriteria criteria: this.stop_criterias) {
			if (criteria.isMet()) {
				met = true;

			}
		}
		return met;
	}*/


// Errors.


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