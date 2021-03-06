package core.learning;

import java.util.ArrayList;
import java.util.List;

import core.learning.stop.EarlyStop;
import core.learning.stop.MaxEpochsStop;
import core.learning.stop.StopCriteria;
import experiment.data.Benchmark;

abstract public class SupervisedLearning extends LearningRule {

	private double learning_rate;
	private int current_epoch,

	// Number of epochs in which GL got better

                 num_relevant_epochs;

	private List<StopCriteria> stop_criterias;

	// Record of all the evas up to current epoch (life-time: entire execution).

	private List<Double> evas,

	// Temporarily store the etrs during the strip (life-time: strip length).

                          buffer_etrs,

   // Record of all etrs.
                          etrs,


    // Record of all generalization losses.

                          gls,

    // Record of all training progresses.

                          pks;

	// Record of all outputs obtained up to current epoch.

	private List<List<Double>> training_output_vectors,
                                validation_output_vectors;


// Creation.


	public SupervisedLearning(double learning_rate, Benchmark bench) {
		super(bench);
		if (learning_rate < 0) {
			throw new IllegalArgumentException("Bad parameters");
		} else {
			this.current_epoch = 0;
			this.num_relevant_epochs = 0;
			this.learning_rate = learning_rate;
			this.stop_criterias = new ArrayList<StopCriteria>();

			this.evas = new ArrayList<Double>();
			this.buffer_etrs = new ArrayList<Double>();
			this.etrs = new ArrayList<Double>();
			this.gls = new ArrayList<Double>();
			this.pks = new ArrayList<Double>();

			this.training_output_vectors = new ArrayList<List<Double>>();
			this.validation_output_vectors = new ArrayList<List<Double>>();
		}
	}


// Learning rate configuration.


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
	public void setCurrentEpoch(int epoch) {
		this.current_epoch = epoch;
	}
	public int getNumberOfRelevantEpochs() {
		return this.num_relevant_epochs;
	}
	public void setNumberOfRelevantEpochs(int elapsed) {
		this.num_relevant_epochs = elapsed;
	}


// Stop criterias configuration.


	public List<StopCriteria> getStopCriterias() {
		return this.stop_criterias;
	}
	public void setStopCriterias(List<StopCriteria> criterias) {
		this.stop_criterias = criterias;
	}
	public void addStopCriteria(StopCriteria criteria) {
		if (criteria == null) {
			throw new IllegalArgumentException("Stop criteria must not be null");
		} else {
			this.stop_criterias.add(criteria);
		}
	}

	// Max Epochs.

	public MaxEpochsStop getMaxEpochsStop() {
		MaxEpochsStop max_epochs_stop = null;

		for (StopCriteria criteria: this.stop_criterias) {

			if (StopCriteria.isMaxEpochsStop(criteria)) {
				max_epochs_stop = (MaxEpochsStop) criteria;
				break;
			}
		}
		return max_epochs_stop;
	}

	// Early Stop.

	public EarlyStop getEarlyStop() {
		EarlyStop early_stop = null;

		for (StopCriteria criteria: this.stop_criterias) {

			if (StopCriteria.isEarlyStop(criteria)) {
				early_stop = (EarlyStop) criteria;
				break;
			}
		}
		return early_stop;
	}


// Errors.


	// Evas.

	public List<Double> getEvasRecord() {
		return this.evas;
	}
	public void setEvasRecord(List<Double> evas) {
		this.evas = evas;
	}
	public void saveEva(Double eva) {
		this.evas.add(eva);
	}
	public void clearEvasRecord() {
		this.evas.clear();
	}

	// Etrs Buffer.

	public List<Double> getEtrsBuffer() {
		return this.buffer_etrs;
	}
	public void setEtrsBuffer(List<Double> etrs) {
		this.buffer_etrs = etrs;
	}
	public void pushEtr(Double etr) {
		this.buffer_etrs.add(etr);
	}
	public void flushBufferEtrs() {
		this.buffer_etrs.clear();
	}

	// Etrs Record.

	public List<Double> getEtrsRecord() {
		return this.etrs;
	}
	public void setEtrsRecord(List<Double> etrs) {
		this.etrs = etrs;
	}
	public void saveEtr(Double etr) {
		this.etrs.add(etr);
	}
	public void clearEtrsRecord() {
		this.etrs.clear();
	}

	// GLs.

	public List<Double> getGLsRecord() {
		return this.gls;
	}
	public void setGLsRecord(List<Double> gls) {
		this.gls = gls;
	}
	public void saveGL(Double gl) {
		this.gls.add(gl);
	}
	public void clearGLs() {
		this.gls.clear();
	}

	// PKs.

	public List<Double> getPKsRecord() {
		return this.pks;
	}
	public void setPKsRecord(List<Double> pks) {
		this.pks = pks;
	}
	public void savePK(Double pk) {
		this.pks.add(pk);
	}
	public void clearPKs() {
		this.pks.clear();
	}


// Training output vectors.


	public List<List<Double>> getTrainingOutputVectors() {
		return this.training_output_vectors;
	}
	public void setTrainingOutputVectors(List<List<Double>> those) {
		this.training_output_vectors = those;
	}
	public void saveTrainingOutputVector(List<Double> output_vector) {
		this.training_output_vectors.add(output_vector);
	}
	public void clearTrainingOutputVectors() {
		this.training_output_vectors.clear();
	}


// Validation output vectors.


	public List<List<Double>> getValidationOutputVectors() {
		return this.validation_output_vectors;
	}
	public void setValidationOutputVectors(List<List<Double>> those) {
		this.validation_output_vectors = those;
	}
	public void saveValidationOutputVector(List<Double> output_vector) {
		this.validation_output_vectors.add(output_vector);
	}
	public void clearValidationOutputVectors() {
		this.validation_output_vectors.clear();
	}
}