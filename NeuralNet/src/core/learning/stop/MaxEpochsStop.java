package core.learning.stop;

import core.learning.IterativeLearning;

public class MaxEpochsStop implements StopCriteria {

	private IterativeLearning iterative_learning_rule;

	public MaxEpochsStop() {
	}

	@Override
	public boolean isMet() {
		boolean met = false;

		if (iterative_learning_rule.getCurrentEpoch() >= iterative_learning_rule.getMaxEpochs()) {
			met = true;
		}
		return met;
	}

}
