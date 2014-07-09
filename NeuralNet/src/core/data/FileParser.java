package core.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileParser {


	// PROBEN1 data files header.

	private Integer num_bool_inputs,
                     num_bool_outputs,
					 num_real_inputs,
                     num_real_outputs,

                     num_training_samples,
                     num_validation_samples,
                     num_test_samples;

	// To distinguish an input/output datum in a data row.

    private Integer total_inputs,
                     total_outputs;

	private String file_path;


// Creation.


	public FileParser(String file_path) {
		if (file_path == null) {
			throw new IllegalArgumentException("Data file must not be null !");
		} else {
			this.num_bool_inputs = 0;
			this.num_real_inputs = 0;
            this.num_bool_outputs = 0;
            this.num_real_outputs = 0;
            this.num_training_samples = 0;
            this.num_validation_samples = 0;
            this.num_test_samples = 0;
            this.total_inputs = 0;
            this.total_outputs = 0;


			this.file_path = file_path;
		}
	}


// Parsing.


	// File.

	public Benchmark parseFile() {
		Exception exception = null;
		List<Sample> samples = new ArrayList<Sample>();

		try {
			FileReader freader = new FileReader(this.file_path);
			BufferedReader breader = new BufferedReader(freader);

			String line = breader.readLine();
			while (line != null) {

				// Is header line ?

				if (line.contains("=")) {
					this.parseHeaderLine(line);

				// Is data line ?

				} else {
					Sample sample = parseDataLine(line);
					samples.add(sample);
				}
				// Advance line.

				line = breader.readLine();
			}
			breader.close();

		} catch (FileNotFoundException file_not_found) {
			System.out.println("File not found: " + this.file_path);
			exception = file_not_found;
		} catch (IOException io) {
			System.out.println("Could not read line from file: " + this.file_path);
			exception = io;
		} finally {
			exception.printStackTrace();
		}
		return new Benchmark(this.num_training_samples,
                                  this.num_validation_samples,
                                  this.num_test_samples,
                                  samples,
                                  this.file_path);
	}

	// Header.

	public void parseHeaderLine(String line) {

		// Split line (parameter = integer).

		String[] left_right = line.split("=");

		// Separate its parts.

		String left = left_right[0];
		Integer right = this.stringToInteger(left_right[1]);

		// Select which parameter to be set.

		Integer which = null;

		if (left.contains("real_in")) {
			which = this.num_real_inputs;

		} else if (left.contains("bool_in")) {
			which = this.num_bool_inputs;

		} else if (left.contains("real_out")) {
			which = this.num_real_outputs;

		} else if (left.contains("bool_out")) {
			which = this.num_bool_outputs;

		} else if (left.contains("training_examples")) {
			which = this.num_training_samples;

		} else if (left.contains("validation_examples")) {
			which = this.num_validation_samples;

		} else if (left.contains("test_examples")) {
			which = this.num_test_samples;
		}
		which = right;
	}

	// Data.

	public Sample parseDataLine(String line) {
		Sample sample = new Sample();
		String[] data = {};
		int at = 0;

		// To separate inputs from outputs within a line.

		this.calculateTotalInputs();
		this.calculateTotalOutputs();

		// Parse.

		data = line.split(" ");
		for (String datum: data) {

			// Is datum an input ?

			if (at <= this.total_inputs) {
				sample.addInput(this.stringToDouble(datum));

			// Is datum an output ?

			} else if ((this.total_inputs < at) &&
                        (at <= (this.total_inputs + this.total_outputs))){
				sample.addOutput(this.stringToDouble(datum));
			}
		}
		return sample;
	}
	public void calculateTotalInputs() {

		// boolean inputs ?

		if (this.num_bool_inputs != 0) {
			this.total_inputs = this.num_bool_inputs;

		// or real inputs ?

		} else if (this.num_real_inputs != 0) {
			this.total_inputs = this.num_real_inputs;
		}
	}
	public void calculateTotalOutputs() {

		// boolean outputs ?

		if (this.num_bool_outputs != 0) {
			this.total_outputs = this.num_bool_outputs;

		// or real outputs ?

		} else if (this.num_real_outputs != 0) {
			this.total_outputs = this.num_real_outputs;
		}
	}


// Conversion.


	public Double stringToDouble(String data) {
		return Double.parseDouble(data);
	}
	public Integer stringToInteger(String data) {
		return Integer.valueOf(data);
	}
}
