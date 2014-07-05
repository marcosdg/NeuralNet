package core.learning;

import core.learning.stop.StopCriteria;

//TODO: DEVELOPMENT

abstract public class IterativeLearning extends LearningRule {


	private double learning_rate;

	private int current_epoch,
                 max_epochs;

	private StopCriteria stop_criteria;



// Learning Rate configuration.


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

	public int getMaxEpochs() {
		return this.max_epochs;
	}
	public void setMaxEpochs() {
		this.max_epochs = max_epochs;
	}
}
