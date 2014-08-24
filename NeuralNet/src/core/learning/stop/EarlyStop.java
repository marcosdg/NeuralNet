package core.learning.stop;

import java.util.List;

import core.data.Sample;
import core.learning.SupervisedLearning;
import core.learning.error.SquaredError;

public class EarlyStop extends StopCriteria {

	private SupervisedLearning supervised_learning_rule;

	private int strip_length;

	private double best_generalization_loss,
                     now_generalization_loss,
                     now_training_progress,
                     max_generalization_loss,
                     min_training_progress;

// Creation.


	public EarlyStop(SupervisedLearning rule, int strip_length,
                      double max_generalization_loss,
                      double min_training_progress) {

		this.supervised_learning_rule = rule;
		this.strip_length = strip_length;

		// Any net should not have more than max_generalization_loss
		// so that's a good value to start off.
		this.best_generalization_loss = max_generalization_loss;

		this.now_generalization_loss = 0.0;
		this.now_training_progress = 0.0;

		this.max_generalization_loss = max_generalization_loss;
		this.min_training_progress = min_training_progress;
	}


// Strip.


	public int getStripLength() {
		return this.strip_length;
	}


// Checking.


	@Override
	public boolean isMet() {
		boolean gl_met = this.isMaxGeneralizationLossMet(),
				 pk_met = this.isMinTrainingProgressMet();

		return (gl_met || pk_met);
	}

	/* By separating them, will be easier to check at
	 * the end of the learning process which one happened.
	 */

	public boolean isMaxGeneralizationLossMet() {
		List<Double> evas = this.supervised_learning_rule.getEvasRecord();
		List<List<Double>> output_vectors = this
                                            .supervised_learning_rule
                                            .getTrainingOutputVectors();

		this.computeGeneralizationLoss(output_vectors, evas);
		return (this.now_generalization_loss > this.max_generalization_loss);
	}
	public boolean isMinTrainingProgressMet() {
		List<Double> buffer_etrs = this.supervised_learning_rule.getEtrsBuffer();

		this.computeTrainingProgress(buffer_etrs);
		return (this.now_training_progress < this.min_training_progress);
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
		boolean is_classification = this
                                     .supervised_learning_rule
                                     .getBenchmark()
                                     .isClassification();
		Double squared_error = 0.0;

		if (is_classification) {
			squared_error = SquaredError
                            .getSquaredErrorPercentage(training_samples,
                                                       output_vectors,
                                                       num_output_nodes,
                                                       true);
		} else {
			squared_error = SquaredError
                            .getSquaredErrorPercentage(training_samples,
                                                       output_vectors,
                                                       num_output_nodes,
                                                       false);
		}
		return squared_error;
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
		boolean is_classification = this
                                     .supervised_learning_rule
                                     .getBenchmark()
                                     .isClassification();
		Double squared_error = 0.0;

		if (is_classification) {
			squared_error = SquaredError
                            .getSquaredErrorPercentage(validation_samples,
                                                       output_vectors,
                                                       num_output_nodes,
                                                       true);
		} else {
			squared_error = SquaredError
                            .getSquaredErrorPercentage(validation_samples,
                                                       output_vectors,
                                                       num_output_nodes,
                                                       false);
		}

		return squared_error;
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






// Generalization Loss configuration.

	// GL(t).

	public void computeGeneralizationLoss(List<List<Double>> output_vectors,
			List<Double> evas) {
		Double eva = this.getAverageErrorPerValidationSample(output_vectors),
               eopt = this.getMinAverageErrorPerValidationSample(evas);

		this.now_generalization_loss = 100 * ((eva / eopt) - 1);
	}
	public double getMaxGeneralizationLoss() {
		return this.max_generalization_loss;
	}
	public double getCurrentGeneralizationLoss() {
		return this.now_generalization_loss;
	}
	public double getBestGeneralizationLoss() {
		return this.best_generalization_loss;
	}
	public void setBestGeneralizationLoss(double best){
		this.best_generalization_loss = best;
	}


// Training Progress configuration.


	// Pk(t). Etrs obtained from the last k (strip length) epochs.

	public void computeTrainingProgress(List<Double> etrs) {
		Double total_etrs = 0.0,
               min_etr = etrs.get(0);

		for (Double etr : etrs) {

			// Get the minimum etr.

			if (etr < min_etr) {
				min_etr = etr;
			}
			// Get the total of etrs.

			total_etrs += etr;
		}
		this.now_training_progress = 1000 * ((total_etrs / (this.getStripLength() * min_etr)) - 1);
	}
	public double getMinTrainingProgress() {
		return this.min_training_progress;
	}
	public double getCurrentTrainingProgress() {
		return this.now_training_progress;
	}
}