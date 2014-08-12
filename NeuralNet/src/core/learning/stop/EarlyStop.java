package core.learning.stop;

import java.util.List;

import core.data.Sample;
import core.learning.SupervisedLearning;
import core.learning.error.SquaredError;

// TODO: complete EarlyStop.

public class EarlyStop extends StopCriteria {

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
		return (this.isMaxGeneralizationLossMet() ||
	             this.isMinTrainingProgressMet());
	}

	/* By separating them, will be easier to check at
	 * the end of the learning process which one happened.
	 */

	public boolean isMaxGeneralizationLossMet() {
		List<Double> evas = this.supervised_learning_rule.getEvasRecord();
		List<List<Double>> output_vectors = this
                                            .supervised_learning_rule
                                            .getOutputVectorsRecord();

		Double loss = this.getGeneralizationLoss(output_vectors, evas);

		return (loss > this.max_generalization_loss);
	}
	public boolean isMinTrainingProgressMet() {
		List<Double> etrs = this.supervised_learning_rule.getEtrsRecord();
		Double progress = this.getTrainingProgress(etrs);

		return (progress < this.min_training_progress);
	}


// Error measures.


	// Etr(t).

	public Double getAverageErrorPerTrainingSample(List<List<Double>> output_vectors) {
		List<Sample> training_samples = this
                                        .supervised_learning_rule
                                        .getBenchmark()
                                        .getTrainingSamples();
		int num_output_nodes = this
                                .supervised_learning_rule
                                .getNeuralNetwork()
                                .getOutputLayer()
                                .numberOfNodes();

		return SquaredError.getSquaredErrorPercentage(training_samples,
                                                       output_vectors,
                                                       num_output_nodes);
	}

	// Eva(t).

	public Double getAverageErrorPerValidationSample(List<List<Double>> output_vectors) {
		List<Sample> validation_samples = this
                                          .supervised_learning_rule
                                          .getBenchmark()
                                          .getValidationSamples();
		int num_output_nodes = this
                                .supervised_learning_rule
                                .getNeuralNetwork()
                                .getOutputLayer()
                                .numberOfNodes();

		return SquaredError.getSquaredErrorPercentage(validation_samples,
                                                       output_vectors,
                                                       num_output_nodes);
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

	// Pk(t). Etrs obtained from the last k (strip length) epochs.

	public Double getTrainingProgress(List<Double> etrs) {

		Double total_etrs = 0.0,
               min_etr = etrs.get(0);

		for (Double etr: etrs) {

			// Get the minimum etr.

			if (etr < min_etr) {
				min_etr = etr;
			}

			// Get the total of etrs.

			total_etrs += etr;
		}
		return 1000 * ((total_etrs / (EarlyStop.strip_length * min_etr)) - 1);
	}
}