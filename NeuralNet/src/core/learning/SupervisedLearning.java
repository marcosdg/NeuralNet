package core.learning;

import java.util.List;

import core.learning.stop.StopCriteria;

//TODO: COMPLETE SUPERVISEDLEARNING CLASS

abstract public class SupervisedLearning extends LearningRule {

	private int current_epoch;

	private double learning_rate;

	private List<StopCriteria> stop_criterias; // all combined.

	// Record of all outputs obtained up to current epoch.

	private List<List<Double>> output_vectors;

	private List<Double> evas, // Record of all the evas up to current epoch.
                          etrs; // 'Buffer' with all the etrs during each strip.


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
	public void setStopCriterias(List<StopCriteria> criterias){
		this.stop_criterias = criterias;
	}
	public void addStopCriteria(StopCriteria criteria) {
		if (criteria == null) {
			throw new IllegalArgumentException("Stop criteria must not be null");
		} else {
			this.stop_criterias.add(criteria);
		}
	}


// Output vectors.


	public List<List<Double>> getOutputVectorsRecord() {
		return this.output_vectors;
	}


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
}