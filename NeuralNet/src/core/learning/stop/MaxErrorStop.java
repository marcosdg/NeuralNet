package core.learning.stop;

import core.learning.SupervisedLearning;

public class MaxErrorStop implements StopCriteria {

	private SupervisedLearning supervised_learning_rule;
	private double max_network_error;


// Creation.


	public MaxErrorStop(SupervisedLearning rule, double max_network_error) {
		this.supervised_learning_rule = rule;
		this.max_network_error = max_network_error;
	}


// Criteria.


	@Override
	public boolean isMet() {
		boolean met = false;

		if (this.supervised_learning_rule.getCurrentNetworkError() >=
            this.getMaxNetworkError()) {
			met = true;
		}
		return met;
	}


// Error configuration.


	public double getMaxNetworkError() {
		return this.max_network_error;
	}
	public void setMaxNetworkError(double error) {
		this.max_network_error = error;
	}
}