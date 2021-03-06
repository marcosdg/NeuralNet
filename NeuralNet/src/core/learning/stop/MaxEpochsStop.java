package core.learning.stop;

import core.learning.SupervisedLearning;

public class MaxEpochsStop extends StopCriteria {

	private SupervisedLearning supervised_learning_rule;
	private int max_epochs;


// Creation.


	public MaxEpochsStop(SupervisedLearning rule, int max_epochs) {
		this.supervised_learning_rule = rule;
		this.max_epochs = max_epochs;
	}


// Checking.


	@Override
	public boolean isMet() {
		return (this.supervised_learning_rule.getCurrentEpoch() >=
                 this.getMaxEpochs());
	}


// Epochs configuration,


	public int getMaxEpochs() {
		return this.max_epochs;
	}
	public void setMaxEpochs(int epochs) {
		this.max_epochs = epochs;
	}
}
