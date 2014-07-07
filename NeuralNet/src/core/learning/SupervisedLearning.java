package core.learning;

import core.learning.stop.StopCriteria;

//TODO: COMPLETE SUPERVISEDLEARNING CLASS

abstract public class SupervisedLearning extends LearningRule {


	private double learning_rate,
                     current_network_error;

	private int current_epoch;

	private StopCriteria stop_criteria;



// Learning Rate configuration.


	public double getLearningRate() {
		return this.learning_rate;
	}
	public void setLearninRate(double learning_rate) {
		this.learning_rate = learning_rate;
	}


// Error configuration.


	public double getCurrentNetworkError() {
		return this.current_network_error;
	}
	public void setCurrentNetworkError(double error) {
		this.current_network_error = error;
	}


// Epochs configuration.


	public int getCurrentEpoch() {
		return this.current_epoch;
	}


// Stop criteria configuration


	public StopCriteria getStopCriteria() {
		return this.stop_criteria;
	}
	public void setStopCriteria(StopCriteria that){
		this.stop_criteria = that;
	}
}
