package core.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.InputNode;
import core.Layer;
import core.NeuralNetwork;
import core.Neuron;
import core.activation.Linear;
import core.activation.Sigmoid;
import core.learning.Backpropagation;
import core.learning.stop.EarlyStop;
import core.learning.stop.MaxEpochsStop;
import core.learning.stop.StopCriteria;
import core.propagation.WeightedSum;

public class NeuralNetworkParse {
	private String config_file_path;
	// These lists must be in order
	private List<Layer> layers = new ArrayList<Layer>();
	private List<Neuron> neurons = new ArrayList<Neuron>();
	private Backpropagation backpropagation;
	private NeuralNetwork network;
	private Double momentum, learning_rate,max_generalization_loss, min_training_progress, o_min, o_max;;
	private Integer max_epochs, strip;
	private WeightedSum weightedSum;
	private Sigmoid sigmoid;
	private int currentNeuron = 0;
	private Benchmark benchmark;

	public static final String EQUAL = "=";
	public static final String SPACE = " ";
	public static final String COMMA = ",";
	public static final String POINT = ".";

	public static final int DATA_LAYER = 0;
	public static String NETWORK_LABEL;

	public NeuralNetworkParse(String config_dir, String config_file_name, Benchmark benchmark)
	{
		if (config_file_name.isEmpty() || config_dir.isEmpty()) {
			throw new IllegalArgumentException("Config file must not be null !");
		} else {
			config_file_path = new StringBuilder(pathToWorkSpace())
						            .append("/src/networks/")
						            .append(config_dir)
						            .append("/")
						            .append(config_file_name)
						            .toString();

			NeuralNetworkParse.NETWORK_LABEL = (new File(this.config_file_path)).getName();

			this.weightedSum = new WeightedSum();
			this.sigmoid = new Sigmoid();
			this.benchmark = benchmark;

		}
	}

	// To support loading files with relative paths.

	private String pathToWorkSpace() {
		return System.getProperty("user.dir");
	}


// Neural Network.


	public NeuralNetwork getNeuralNetwork() {
		return this.network;
	}


// Parsing.


	// File.

	public void parse() {
		String last_line = "";
		try {
			FileReader freader = new FileReader(this.config_file_path);
			BufferedReader breader = new BufferedReader(freader);

			last_line = this.parseHeader(breader);

			this.createBackpropagationAndNetwork();
			// Prepare data parsing.
			this.parseNetStructure(breader, last_line);

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

	// Header.

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

		String[] left_right = line.split(EQUAL);

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
			this.max_generalization_loss = this.stringToDouble(left_right[1]);
		} else if (left.contains("PK")) {
			this.min_training_progress = this.stringToDouble(left_right[1]);
		} else if (left.contains("O_MIN")) {
			this.o_min = this.stringToDouble(left_right[1]);
		} else if (left.contains("O_MAX")) {
			this.o_max = this.stringToDouble(left_right[1]);
		}
	}

	private void createBackpropagationAndNetwork() {
		this.benchmark.setDesiredOutputValuesRange(this.o_min, this.o_max);

		this.backpropagation = new Backpropagation(this.learning_rate,
                                                    this.momentum,
                                                    this.benchmark);
		StopCriteria epochs_stop = new MaxEpochsStop(this.backpropagation,
                                                      this.max_epochs);
		StopCriteria early_stop = new EarlyStop(this.backpropagation,
                                                this.strip,
                                                this.max_generalization_loss,
                                                this.min_training_progress);
		this.backpropagation.addStopCriteria(epochs_stop);
		this.backpropagation.addStopCriteria(early_stop);

		this.network = new NeuralNetwork(this.layers,
                                          this.backpropagation,
                                          NeuralNetworkParse.NETWORK_LABEL);
	}

	// Network structure

	private void parseNetStructure(BufferedReader breader, String last_line) throws IOException {
		// Continue from header parsing.

		String line = last_line;

		while (line != null) {
			line = line.trim(); // clean leading/trailing spaces.
			this.parseNetLine(line);
			line = breader.readLine();
		}
	}

	private void parseNetLine(String line) {
		String[] left_right = line.split(SPACE);

		String left = left_right[0];

		if (left.contains("T")) {
			this.createLayersAndNeurons(left_right[1]);
		} else if (left.contains("B")) {
			this.createBias(left_right[1]);
		} else if (left.contains("I")) {
			this.createInputNode(left_right[1]);
		} else if (left.contains("N")) {
			this.createConnectionBetweenNeurons(left_right[1]);
		}
	}

	private void createLayersAndNeurons(String values) {
		String[] split = values.split(COMMA);
		int numOfHiddenLayers = split.length - 2;

		// Data.

		Layer input_data_layer = new Layer(Layer.INPUT_DATA_LAYER);

		// Initial.

		Layer initial_layer = new Layer(Layer.INITIAL_LAYER);


		Layer output_layer = new Layer(Layer.OUTPUT_LAYER);

		this.layers.add(input_data_layer);
		this.layers.add(initial_layer);

		// Hidden
		if (numOfHiddenLayers > 0) {
			this.completeHiddenLayers(numOfHiddenLayers);
		}

		// Output.

		this.layers.add(output_layer);
		int currentNeuron = 1;

		for (int i=0; i < split.length; i++) {
			Integer neuronsInLayer = stringToInteger(split[i]);
			for (int j=1; j <= neuronsInLayer; j++) {
				Neuron neuron = null;
				if (i == 0) {
					neuron = new Neuron(weightedSum, sigmoid, initial_layer, "Neuron " + currentNeuron);
					initial_layer.addNode(neuron);
				} else if (i == split.length - 1) {
					neuron = new Neuron(weightedSum, sigmoid, output_layer, "Neuron " + currentNeuron);
					output_layer.addNode(neuron);
				} else {
					//Hidden Layer (i+1) because this.layers contains an extra data layer at the beginning
					neuron = new Neuron(weightedSum, sigmoid, this.layers.get(i+1), "Neuron " + currentNeuron);
					this.layers.get(i+1).addNode(neuron);
				}
				currentNeuron++;
				this.neurons.add(neuron);
			}
		}

	}

	private void completeHiddenLayers(int numOfHiddenLayers) {
		// In that case we need to add hidden layers in order
		// Previously we have added data layer and init layer
		for (int i = 0; i < numOfHiddenLayers; i++) {
			Layer hiddenLayer = new Layer(Layer.HIDDEN_LAYER);
			this.layers.add(hiddenLayer);
		}
	}

	private void createBias(String values) {
		String[] split = values.split(COMMA);
		for (int i = 0; i < split.length; i++) {
			if (!split[i].contains(POINT)) {
				Double biasWeight = stringToDouble(split[i]);
				InputNode bias = new InputNode(this.layers.get(DATA_LAYER), "Bias for Neuron " + (i + 1),
                        InputNode.BIAS_NODE());
				bias.setInputData(1);
				this.layers.get(DATA_LAYER).addNode(bias);
				this.network.createConnection(bias, this.neurons.get(i), biasWeight,
						"Connection: Bias -> Neuron" + (i + 1));
			}
		}
	}

	private void createInputNode(String values) {
		String[] split = values.split(COMMA);
		for (int i = 0; i < split.length; i++) {
			if (!split[i].contains(POINT)) {
				Double inputWeight = stringToDouble(split[i]);
				InputNode input_node = new InputNode(this.layers.get(DATA_LAYER), "Input Node " + (i + 1),
                        InputNode.INPUT_DATA_NODE());
				// TODO input data must be filled with sample data
				input_node.setInputData(1);
				this.layers.get(DATA_LAYER).addNode(input_node);
				this.network.createConnection(input_node, this.neurons.get(i), inputWeight,
						"Connection: " + input_node.getLabel() + " -> Neuron" + (i + 1));
			}
		}
	}

	private void createConnectionBetweenNeurons(String values) {
		String[] split = values.split(COMMA);
		for (int i = 0; i < split.length; i++) {
			if (!split[i].contains(POINT)) {
				Double weight = stringToDouble(split[i]);
				this.network.createConnection(this.neurons.get(this.currentNeuron),
						this.neurons.get(i), weight,
						"Connection: Neuron " + (this.currentNeuron + 1) + " -> Neuron" + (i + 1));
			}
		}

		this.currentNeuron++;
	}

	private Double stringToDouble(String value) {
		return Double.parseDouble(value);
	}

	private Integer stringToInteger(String value) {
		return Integer.valueOf(value);
	}
}