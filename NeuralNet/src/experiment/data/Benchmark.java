package experiment.data;


import java.util.ArrayList;
import java.util.List;


public class Benchmark {

	// Header and Data splitting format.

    private static final String EQUAL = "=",
                                  ONE_OR_MORE_SPACES = "\\s+";

	// PROBEN1 data files header.

	private Integer num_bool_inputs,
                     num_bool_outputs,
                     num_real_inputs,
                     num_real_outputs,
                     num_training_samples,
                     num_validation_samples,
                     num_test_samples;

	private List<Sample> samples;

	// Distinguish input/output datum in a data row (used by FileParser).

    private Integer total_inputs, total_outputs;

    /*
     * PROBEN1 data files have desired output values scaled
     * within [0.0, 1.0]. Will be used in error normalization.
     */

	private static Double output_min, output_max;

    // ProbenFileParser will set it up.

 	private String path;


// Creation.


	public Benchmark(Integer num_bool_inputs, Integer num_bool_outputs,
                      Integer num_real_inputs, Integer num_real_outputs,
                      Integer num_training, Integer num_validation,
                      Integer num_test,
                      List<Sample> samples,
                      String path) {

		if (num_training < 0 || num_validation < 0 || num_test < 0) {
			throw new IllegalArgumentException("Bad header parameters" +
                                                "(not allowed negative numbers)");
		} else if (samples.isEmpty()) {
			throw new IllegalArgumentException("Empty sample data");
		} else {

			this.num_bool_inputs = num_bool_inputs;
			this.num_bool_outputs = num_bool_outputs;
			this.num_real_inputs = num_real_inputs;
			this.num_real_outputs = num_real_outputs;
			this.num_training_samples = num_training;
			this.num_validation_samples = num_validation;
			this.num_test_samples = num_test;

			this.samples = samples;

			// total_inputs/outputs were set during parsing.

			Benchmark.output_min = 0.0;
			Benchmark.output_max = 1.0;

			this.path = path;
		}
	}

	public Benchmark() {
		this.num_bool_inputs = 0;
		this.num_real_inputs = 0;
        this.num_bool_outputs = 0;
        this.num_real_outputs = 0;
        this.num_training_samples = 0;
        this.num_validation_samples = 0;
        this.num_test_samples = 0;

        this.samples = new ArrayList<Sample>();

        this.total_inputs = 0;
        this.total_outputs = 0;

        Benchmark.output_min = 0.0;
		Benchmark.output_max = 1.0;

        this.path = "";
	}


// Kind.


	public boolean isClassification() {
		return this.num_bool_outputs > 0;
	}
	public boolean isApproximation() {
		return this.num_real_outputs > 0;
	}


// File format.


	public static String EQUAL() {
		return Benchmark.EQUAL;
	}
	public static String ONE_OR_MORE_SPACES() {
		return Benchmark.ONE_OR_MORE_SPACES;
	}


// Boolean inputs/outputs.


	public Integer getNumberOfBooleanInputs() {
		return this.num_bool_inputs;
	}
	public void setNumberOfBooleanInputs(Integer num_bool_inputs) {
		this.num_bool_inputs = num_bool_inputs;
	}
	public Integer getNumberOfBooleanOutputs() {
		return this.num_bool_outputs;
	}
	public void setNumberOfBooleanOutputs(Integer num_bool_outputs) {
		this.num_bool_outputs = num_bool_outputs;
	}


// Real inputs/outputs.


	public Integer getNumberOfRealInputs() {
		return this.num_real_inputs;
	}
	public void setNumberOfRealInputs(Integer num_real_inputs) {
		this.num_real_inputs = num_real_inputs;
	}
	public Integer getNumberOfRealOutputs() {
		return this.num_real_outputs;
	}
	public void setNumberOfRealOutputs(Integer num_real_outputs) {
		this.num_real_outputs = num_real_outputs;
	}


// Number of training samples.


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

	/*
	 * PROBEN1 files assume that samples come in the following order:
	 *
	 *    1. Training.   {0, num_training}
	 *    2. Validation. {num_training, validation_end}
	 *    3. Test.       {validation_end, samples.size()}
	 */

	public List<Sample> getTrainingSamples() {
		return this.samples.subList(this.getTrainingRange()[0],
                                      this.getTrainingRange()[1]);
	}
	public List<Sample> getValidationSamples() {
		return this.samples.subList(this.getValidationRange()[0],
                                      this.getValidationRange()[1]);
	}
	public List<Sample> getTestSamples() {
		return this.samples.subList(this.getTestRange()[0],
                                      this.getTestRange()[1]);
	}

	// Training, Validation and Test sets ranges.

	public Integer[] getTrainingRange() {
		Integer[] training_range = {0, this.num_training_samples};
		return training_range;
	}
	public Integer[] getValidationRange() {
		Integer validation_end = this.num_training_samples +
                                 this.num_validation_samples;

		Integer[] validation_range = {this.num_training_samples,
                                      validation_end};
		return validation_range;
	}
	public Integer[] getTestRange() {
		Integer validation_end = this.num_training_samples +
	                             this.num_validation_samples;

		Integer[] test_range = {validation_end, this.samples.size()};
		return test_range;
	}


// Total inputs/outputs.


	public Integer getTotalInputs() {
		return this.total_inputs;
	}
	public Integer getTotalOutputs() {
		return this.total_outputs;
	}

	/*
	 * 'calculateTotalInputs' and 'calculateTotalOutputs' are used when
	 * parsing data lines. Will allow to distinguish which data is input
	 * and output.
	 */

	public Integer calculateTotalInputs() {

		// boolean inputs ?

		if (this.num_bool_inputs != 0) {
			this.total_inputs = this.num_bool_inputs;

		// or real inputs ?

		} else if (this.num_real_inputs != 0) {
			this.total_inputs = this.num_real_inputs;
		}
		return this.total_inputs;
	}
	public void setTotalInputs(Integer total_inputs) {
		this.total_inputs = total_inputs;
	}
	public Integer calculateTotalOutputs() {

		// boolean outputs ?

		if (this.num_bool_outputs != 0) {
			this.total_outputs = this.num_bool_outputs;

		// or real outputs ?

		} else if (this.num_real_outputs != 0) {
			this.total_outputs = this.num_real_outputs;
		}
		return this.total_outputs;
	}
	public void setTotalOutputs(Integer total_outputs) {
		this.total_outputs = total_outputs;
	}


// Desired output values range.


	public static Double getMinDesiredOutputValue() {
		return Benchmark.output_min;
	}
	public static Double getMaxDesiredOutputValue() {
		return Benchmark.output_max;
	}
	public void setDesiredOutputValuesRange(Double output_min,
                                              Double output_max) {
		if (output_min == null || output_max == null) {
			throw new IllegalArgumentException("Desired output values range is null");
		} else {
			Benchmark.output_min = output_min;
			Benchmark.output_max = output_max;
		}
	}


// Path.


	public String getPath() {
		return this.path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		String[] tree_nodes = this.path.split("/");
		int last = tree_nodes.length - 1;

		return tree_nodes[last];
	}



}