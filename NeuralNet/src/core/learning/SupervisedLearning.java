package core.learning;

import java.util.List;

import core.learning.stop.StopCriteria;

//TODO: COMPLETE SUPERVISEDLEARNING CLASS

abstract public class SupervisedLearning extends LearningRule {


	private int current_epoch;

	private double learning_rate;

	private List<StopCriteria> stop_criterias; // all combined.

	private List<List<Double>> output_vectors;
	private List<Double> evas;





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

}
