package core.data;

import java.util.List;

public class Benchmark
{
	private Integer training_samples,
					validation_samples,
					test_samples;
	private List<Sample> samples;
	
	public Benchmark(Integer training, Integer validation, Integer test, List<Sample> samples)
	{
		this.training_samples = training;
		this.validation_samples = validation;
		this.test_samples = test;
		this.samples = samples;
	}

	public Integer getTraining_samples() {
		return training_samples;
	}

	public void setTraining_samples(Integer training_samples) {
		this.training_samples = training_samples;
	}

	public Integer getValidation_samples() {
		return validation_samples;
	}

	public void setValidation_samples(Integer validation_samples) {
		this.validation_samples = validation_samples;
	}

	public Integer getTest_samples() {
		return test_samples;
	}

	public void setTest_samples(Integer test_samples) {
		this.test_samples = test_samples;
	}

	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}
	
	
}
