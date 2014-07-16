package core.learning.stop;

import core.learning.SupervisedLearning;

public class MaxTrainingErrorStop implements StopCriteria {

	private SupervisedLearning supervised_learning_rule;
	private double max_training_error;


// Creation.


	public MaxTrainingErrorStop(SupervisedLearning rule, double max_training_error) {
		this.supervised_learning_rule = rule;
		this.max_training_error = max_training_error;
	}


// Criteria.


	@Override
	public boolean isMet() {
		boolean met = false;

		if (this.supervised_learning_rule.getCurrentTrainingError() >=
            this.getMaxTrainingError()) {
			met = true;
		}
		return met;
	}


// Error configuration.


	public double getMaxTrainingError() {
		return this.max_training_error;
	}
	public void setMaxTrainingkError(double error) {
		this.max_training_error = error;
	}
}