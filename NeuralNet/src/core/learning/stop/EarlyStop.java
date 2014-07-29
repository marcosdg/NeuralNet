package core.learning.stop;

import java.util.List;

import core.data.Sample;
import core.learning.SupervisedLearning;
import core.learning.error.SquaredError;

// TODO: complete EarlyStop.

public class EarlyStop implements StopCriteria {

	private SupervisedLearning supervised_learning_rule;

	private static int strip_length;

	private double max_generalization_loss,
                     min_training_progress;

	public EarlyStop(SupervisedLearning rule, int strip_length,
                      double max_generalization_loss,
                      double min_training_progress) {

		this.supervised_learning_rule = rule;
		EarlyStop.strip_length = strip_length;
		this.max_generalization_loss = max_generalization_loss;
		this.min_training_progress = min_training_progress;
	}


	@Override
	public boolean isMet() {
		return false;
	}


	// Etr(t).

	public Double getAverageErrorPerTrainingSample(List<List<Double>> output_vectors) {
		List<Sample> training_samples = this
                                        .supervised_learning_rule
                                        .getBenchmark()
                                        .getTrainingSamples();

		return SquaredError.getSquaredErrorPercentage(training_samples,
                                                       output_vectors);
	}

	// Eva(t).

	public Double getAverageErrorPerValidationSample(List<List<Double>> output_vectors) {
		List<Sample> validation_samples = this
                                          .supervised_learning_rule
                                          .getBenchmark()
                                          .getValidationSamples();

		return SquaredError.getSquaredErrorPercentage(validation_samples,
                                                       output_vectors);
	}
}