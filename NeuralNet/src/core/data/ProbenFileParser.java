package core.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ProbenFileParser {

	private Benchmark benchmark;
	private String proben_file_path;


// Creation.


	public ProbenFileParser(String proben_dir, String proben_file_name) {
		if (proben_dir == "" || proben_file_name == "") {
			throw new IllegalArgumentException("Data file must not be null !");
		} else {

			// Create empty benchmark.

			this.benchmark = new Benchmark();

			// Build the path to 'PROBEN1' file

			this.proben_file_path = new StringBuilder(pathToWorkSpace())
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


// Benchmark.


	public Benchmark getBenchmark() {
		return this.benchmark;
	}
	public void setBenchmark(Benchmark that) {
		this.benchmark = that;
	}


// Parsing.


	// File.

	public void parse() {
		List<Sample> samples = new ArrayList<Sample>();
		String last_line = "";
		try {
			FileReader freader = new FileReader(this.proben_file_path);
			BufferedReader breader = new BufferedReader(freader);

			last_line = this.parseHeader(breader);

			// Prepare data parsing.

			this.benchmark.calculateTotalInputs();
			this.benchmark.calculateTotalOutputs();

			this.parseData(breader, last_line, samples);

			breader.close();

		} catch (FileNotFoundException file_not_found) {
			System.out.println("File not found: " + this.proben_file_path);
			file_not_found.printStackTrace();
		} catch (IOException io) {
			System.out.println("Could not read line from file: " +
                                this.proben_file_path);
			io.printStackTrace();
		}
		// Create Benchmark.

		this.setBenchmark(new Benchmark
                                (this.benchmark.getNumberOfBooleanInputs(),
                                 this.benchmark.getNumberOfBooleanOutputs(),
                                 this.benchmark.getNumberOfRealInputs(),
                                 this.benchmark.getNumberOfRealOutputs(),
                                 this.benchmark.getNumberOfTrainingSamples(),
                                 this.benchmark.getNumberOfValidationSamples(),
                                 this.benchmark.getNumberOfTestSamples(),
                                 samples,
                                 this.proben_file_path));


	}

	// Header.

	public String parseHeader(BufferedReader breader) throws IOException {

		String line = breader.readLine();

		while (line.contains(this.benchmark.EQUAL())) {
			line = line.trim(); // clean leading/trailing spaces.
			this.parseHeaderLine(line);

			line = breader.readLine();
		}
		return line;
	}
	public void parseHeaderLine(String line) {

		// Split line (parameter = integer).

		String[] left_right = line.split(this.benchmark.EQUAL());

		String left = left_right[0];
		Integer right = this.stringToInteger(left_right[1]);

		// Which parameter to be set?

		if (left.contains("real_in")) {
			this.benchmark.setNumberOfRealInputs(right);

		} else if (left.contains("bool_in")) {
			this.benchmark.setNumberOfBooleanInputs(right);

		} else if (left.contains("real_out")) {
			this.benchmark.setNumberOfRealOutputs(right);

		} else if (left.contains("bool_out")) {
			this.benchmark.setNumberOfBooleanOutputs(right);

		} else if (left.contains("training_examples")) {
			this.benchmark.setNumberOfTrainingSamples(right);

		} else if (left.contains("validation_examples")) {
			this.benchmark.setNumberOfValidationSamples(right);

		} else if (left.contains("test_examples")) {
			this.benchmark.setNumberOfTestSamples(right);
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
		Integer total_inputs = this.benchmark.getTotalInputs(),
                total_outputs = this.benchmark.getTotalOutputs();
		int at = 0;

		data = line.split(this.benchmark.ONE_OR_MORE_SPACES());

		for (String datum: data) {

			// Is datum an input ?

			if (at < total_inputs) {
				sample.addInput(this.stringToDouble(datum));

			// Is datum an output ?

			} else if ((total_inputs <= at) &&
                                        (at <= (total_inputs + total_outputs))){
				sample.addOutput(this.stringToDouble(datum));
			}
			at += 1;
		}
		return sample;
	}


// Conversion.


	public Double stringToDouble(String data) {
		return Double.parseDouble(data);
	}
	public Integer stringToInteger(String data) {
		return Integer.valueOf(data);
	}
}