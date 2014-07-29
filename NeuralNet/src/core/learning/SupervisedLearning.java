package core.learning;

import core.learning.stop.StopCriteria;

//TODO: COMPLETE SUPERVISEDLEARNING CLASS

abstract public class SupervisedLearning extends LearningRule {


	private int current_epoch;

	private double learning_rate;

	private StopCriteria stop_criteria;


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


// Stop criteria configuration.


	public StopCriteria getStopCriteria() {
		return this.stop_criteria;
	}
	public void setStopCriteria(StopCriteria that){
		this.stop_criteria = that;
	}
}
