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


// Creation.

	public EarlyStop(SupervisedLearning rule, int strip_length,
                      double max_generalization_loss,
                      double min_training_progress) {

		this.supervised_learning_rule = rule;
		EarlyStop.strip_length = strip_length;
		this.max_generalization_loss = max_generalization_loss;
		this.min_training_progress = min_training_progress;
	}


// Checking.

	@Override
	public boolean isMet() {
		return false;
	}


// Error measures.


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

	// Eopt(t).

	public Double getMinAverageErrorPerValidationSample(List<Double> evas) {

		Double min_eva = evas.get(0);

		for (Double eva: evas) {
			if (eva < min_eva) {
				min_eva = eva;
			}
		}
		return min_eva;
	}

	// GL(t).

	public Double getGeneralizationLoss(List<List<Double>> output_vectors,
                                      List<Double> evas) {
		Double eva = this.getAverageErrorPerValidationSample(output_vectors),
               eopt = this.getMinAverageErrorPerValidationSample(evas);

		return 100 * ((eva / eopt) - 1);
	}
}