package utils;

import experiment.Experiment;

// TODO: complete CSVWriter.

public class CSVWriter {

	private Experiment experiment;


// Creation.


	public CSVWriter(Experiment experiment) {
		if (!experiment.isDone()) {
			throw new IllegalArgumentException("Must run the experiment first");
		} else {
			this.experiment = experiment;
		}
	}


// Experiment.


	public Experiment getExperiment() {
		return this.experiment;
	}
	public void setExperiment(Experiment that) {
		this.experiment = that;
	}
}