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

    // Header and Data splitting format.

    private static final String EQUAL = "=";
    private static final String ONE_OR_MORE_SPACES = "\\s+";

	private String file_path;



// Creation.


	public FileParser(String proben_dir, String proben_file_name) {
		if (proben_dir == "" || proben_file_name == "") {
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
            this.file_path = new StringBuilder(pathToWorkSpace())
                                               .append("/src/proben1/")
                                               .append(proben_dir)
                                               .append("/")
                                               .append(proben_file_name)
                                               .toString();
		}
	}

	// To support loading files with relative paths.

	private String pathToWorkSpace() {
		return System.getProperty("user.dir");
	}


// Parsing.


	// File.

	public Benchmark parseFile() {
		List<Sample> samples = new ArrayList<Sample>();
		String last_line = "";
		try {
			FileReader freader = new FileReader(this.file_path);
			BufferedReader breader = new BufferedReader(freader);

			last_line = this.parseHeader(breader);

			// Prepare data parsing.

			this.calculateTotalInputs();
			this.calculateTotalOutputs();

			this.parseData(breader, last_line, samples);

			breader.close();

		} catch (FileNotFoundException file_not_found) {
			System.out.println("File not found: " + this.file_path);
			file_not_found.printStackTrace();
		} catch (IOException io) {
			System.out.println("Could not read line from file: " + this.file_path);
			io.printStackTrace();
		}
		return new Benchmark(this.num_training_samples,
                              this.num_validation_samples,
                              this.num_test_samples,
                              samples,
                              this.file_path);
	}

	// Header.

	public String parseHeader(BufferedReader breader) throws IOException {

		String line = breader.readLine();

		while (line.contains(this.EQUAL)) {
			line = line.trim(); // clean leading/trailing spaces.
			this.parseHeaderLine(line);

			line = breader.readLine();
		}
		return line;
	}
	public void parseHeaderLine(String line) {

		// Split line (parameter = integer).

		String[] left_right = line.split(this.EQUAL);

		// Separate its parts.

		String left = left_right[0];
		Integer right = this.stringToInteger(left_right[1]);

		// Select which parameter to be set.

		if (left.contains("real_in")) {
			this.num_real_inputs = right;

		} else if (left.contains("bool_in")) {
			this.num_bool_inputs = right;

		} else if (left.contains("real_out")) {
			this.num_real_outputs = right;

		} else if (left.contains("bool_out")) {
			this.num_bool_outputs = right;

		} else if (left.contains("training_examples")) {
			this.num_training_samples = right;

		} else if (left.contains("validation_examples")) {
			this.num_validation_samples = right;

		} else if (left.contains("test_examples")) {
			this.num_test_samples = right;
		}
	}

	// Data.

	public void parseData(BufferedReader breader, String last_line, List<Sample> samples) throws IOException {

		// Continue from header parsing.

		String line = last_line;

		while (line != null) {

			line = line.trim(); // clean leading/trailing spaces.
			samples.add(this.parseDataLine(line));

			line = breader.readLine();
		}
	}
	public Sample parseDataLine(String line) {
		Sample sample = new Sample();
		String[] data = {};
		int at = 0;

		data = line.split(this.ONE_OR_MORE_SPACES);

		for (String datum: data) {

			// Is datum an input ?

			if (at < this.total_inputs) {
				sample.addInput(this.stringToDouble(datum));

			// Is datum an output ?

			} else if ((this.total_inputs <= at) &&
                        (at <= (this.total_inputs + this.total_outputs))){
				sample.addOutput(this.stringToDouble(datum));
			}
			at += 1;
		}
		return sample;
	}

	/*
	 * 'calculateTotalInputs' and 'calculateTotalOutputs' are used when
	 * parsing data lines. They allow to distinguish which data is input
	 * and output.
	 */

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