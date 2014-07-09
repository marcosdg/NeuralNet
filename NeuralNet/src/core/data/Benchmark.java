package core.data;

import java.util.List;

public class Benchmark {
	private Integer num_training_samples,
                     num_validation_samples,
                     num_test_samples;
	private List<Sample> samples;
	private String label; // parser sets it, by default, to data's file path.


// Creation.


	public Benchmark(Integer num_training, Integer num_validation, Integer num_test,
                      List<Sample> samples, String label) {
		if (num_training < 0 || num_validation < 0 || num_test < 0) {
			throw new IllegalArgumentException("Bad header parameters" +
                                                "(not allowed negative numbers)");
		} else if (samples.isEmpty()) {
			throw new IllegalArgumentException("Empty sample data");
		} else {
			this.num_training_samples = num_training;
			this.num_validation_samples = num_validation;
			this.num_test_samples = num_test;
			this.samples = samples;
			this.label = label;
		}
	}


// Number of training samples .


	public Integer getNumberOfTrainingSamples() {
		return num_training_samples;
	}
	public void setNumberOfTrainingSamples(Integer training_samples) {
		this.num_training_samples = training_samples;
	}


// Number of validation samples.


	public Integer getNumberOfValidationSamples() {
		return num_validation_samples;
	}
	public void setNumberOfValidationSamples(Integer validation_samples) {
		this.num_validation_samples = validation_samples;
	}


// Number of test samples.


	public Integer getNumberOfTestSamples() {
		return num_test_samples;
	}
	public void setNumberOfTestSamples(Integer test_samples) {
		this.num_test_samples = test_samples;
	}


// Samples.


	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}


// Label.


	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
