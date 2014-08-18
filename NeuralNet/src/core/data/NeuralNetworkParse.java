package core.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.Layer;
import core.NeuralNetwork;
import core.learning.Backpropagation;

public class NeuralNetworkParse {
	private String config_file_path;
	private List<Layer> layers = new ArrayList<Layer>();
	private Backpropagation backpropagation;
	private NeuralNetwork network;
	private Double momentum, learning_rate;
	private Integer max_epochs, strip, generalization_loss, min_training_progress, o_min, o_max;

	public static final String EQUAL = "=";

	public NeuralNetworkParse(String path)
	{
		if (path == null || path.isEmpty()) {
			throw new IllegalArgumentException("Config file must not be null !");
		} else {
			config_file_path = path;
		}
	}

	public void parse() {
		String last_line = "";
		try {
			FileReader freader = new FileReader(this.config_file_path);
			BufferedReader breader = new BufferedReader(freader);

			last_line = this.parseHeader(breader);

			// Prepare data parsing.
			this.parseData(breader, last_line);

			breader.close();

		} catch (FileNotFoundException file_not_found) {
			System.out.println("File not found: " + this.config_file_path);
			file_not_found.printStackTrace();
		} catch (IOException io) {
			System.out.println("Could not read line from file: " +
                                this.config_file_path);
			io.printStackTrace();
		}
	}

	private String parseHeader(BufferedReader breader) throws IOException {

		String line = breader.readLine();

		while (line.contains(EQUAL)) {
			line = line.trim(); // clean leading/trailing spaces.
			this.parseHeaderLine(line);

			line = breader.readLine();
		}
		return line;
	}

	private void parseHeaderLine(String line) {

		// Split line (parameter = value).

		String[] left_right = line.split(Benchmark.EQUAL());

		String left = left_right[0];

		if (left.contains("MOMENTUM")) {
			this.momentum = this.stringToDouble(left_right[1]);
		} else if (left.contains("LEARNING_RATE")) {
			this.learning_rate = this.stringToDouble(left_right[1]);
		} else if (left.contains("MAX_EPOCHS")) {
			this.max_epochs = this.stringToInteger(left_right[1]);
		} else if (left.contains("STRIP")) {
			this.strip = this.stringToInteger(left_right[1]);
		} else if (left.contains("GL")) {
			this.generalization_loss = this.stringToInteger(left_right[1]);
		} else if (left.contains("PK")) {
			this.min_training_progress = this.stringToInteger(left_right[1]);
		} else if (left.contains("O_MIN")) {
			this.o_min = this.stringToInteger(left_right[1]);
		} else if (left.contains("O_MAX")) {
			this.o_max = this.stringToInteger(left_right[1]);
		}
	}

	private void parseData(BufferedReader breader, String last_line) throws IOException {
		// Continue from header parsing.

		String line = last_line;

		while (line != null) {
			line = line.trim(); // clean leading/trailing spaces.
			line = breader.readLine();
		}
	}

	private Double stringToDouble(String value) {
		return Double.parseDouble(value);
	}

	private Integer stringToInteger(String value) {
		return Integer.valueOf(value);
	}
}
