package experiment.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.InputNode;
import core.Layer;
import core.NeuralNetwork;
import core.Neuron;
import core.Node;
import core.activation.Sigmoid;
import core.learning.Backpropagation;
import core.learning.stop.EarlyStop;
import core.learning.stop.MaxEpochsStop;
import core.learning.stop.StopCriteria;
import core.propagation.WeightedSum;

public class NeuralNetworkParser {
	private String config_file_path;

	// Header parameters.

	private Double momentum,
                    learning_rate,
                    max_generalization_loss,
                    min_training_progress,
                    o_min,
                    o_max;
	private Integer max_epochs, strip;
	private String net_design;

	// Neural Network (layers and neurons must come ordered).

	public static String NETWORK_LABEL;
	private NeuralNetwork network;
	private List<Layer> layers;
	private List<Neuron> neurons;
	private WeightedSum weightedSum;
	private Sigmoid sigmoid;
	private Backpropagation backpropagation;

	private Benchmark benchmark;
	private boolean ready_bench;

	// Format.

	public static final String EQUAL = "=";
	public static final String SPACE = " ";
	public static final String COMMA = ",";
	public static final String POINT = ".";

	public static final int DATA_LAYER = 0;
	private int currentNeuron;


	public NeuralNetworkParser(String config_dir, String config_file_name) {
		if (config_file_name.isEmpty() || config_dir.isEmpty()) {
			throw new IllegalArgumentException("Config file must not be null !");
		} else {
			config_file_path = new StringBuilder(pathToHomeDir())
						            .append("/networks/")
						            .append(config_dir)
						            .append("/")
						            .append(config_file_name)
						            .toString();

			// Header.

			this.momentum = 0.0;
			this.learning_rate = 0.0;
			this.max_generalization_loss = 0.0;
			this.min_training_progress = 0.0;
			this.o_min = 0.0;
			this.o_max = 0.0;
			this.net_design = "";

			// Neural Network.

			NeuralNetworkParser.NETWORK_LABEL = (new File(this.config_file_path))
                                               .getName();
			this.layers = new ArrayList<Layer>();
			this.neurons = new ArrayList<Neuron>();
			this.weightedSum = new WeightedSum();
			this.sigmoid = new Sigmoid();
			this.currentNeuron = 0;

			// Benchmark.
			this.ready_bench = false;
		}
	}

	// To support loading files with relative paths.

	private String pathToHomeDir() {
		return System.getProperty("user.home");
	}


// Neural Network.


	public NeuralNetwork getNeuralNetwork() {
		return this.network;
	}
	public String getNetworkDesign() {
		return this.net_design;
	}


// Benchmark.


	public void setBenchmark(Benchmark bench) {
		if (bench == null) {
			throw new IllegalArgumentException("Benchmark does not exists");
		} else {
			this.benchmark = bench;
			this.ready_bench = true;
		}
	}


// Parsing.


	// File.

	public void parse() {
		String last_line = "";
		if (!this.ready_bench) {
			throw new IllegalStateException("Must set a benchmark first");
		}
		try {
			FileReader freader = new FileReader(this.config_file_path);
			BufferedReader breader = new BufferedReader(freader);

			last_line = this.parseHeader(breader);

			this.createBackpropagationAndNetwork();

			if (this.net_design.equals("CUSTOM")) {
				this.parseCustomNetStructure(breader, last_line);
			} else {
				this.createDefaultInputDataLayer();
				this.parseInitialAndHiddenLayersStructure(breader, last_line);
				this.createDefaultOutputLayer();
				this.fullyConnectNetwork();
			}
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
		} else if (left.contains("NET_DESIGN")) {
			this.net_design = (left_right[1]);
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
                                          NeuralNetworkParser.NETWORK_LABEL);
	}

	// Default Network structure.

	private void createDefaultInputDataLayer() {
		Layer input_data_layer = new Layer(Layer.INPUT_DATA_LAYER);
		this.layers.add(input_data_layer);

		Integer num_input_data_nodes = this.benchmark.getTotalInputs();

		for (int i = 0; i < num_input_data_nodes; i += 1) {
			InputNode input_node = new InputNode(input_data_layer,
					                              "Input Node " + i,
					                              InputNode.INPUT_DATA_NODE());
			input_node.setInputData(1);
			input_data_layer.addNode(input_node);
		}
	}
	private void parseInitialAndHiddenLayersStructure(BufferedReader breader, String last_line) throws IOException {
		// Continue from header parsing.

		String line = last_line;
		String[] left_right = {""};

		while (line != null) {
			line = line.trim(); // clean leading/trailing spaces.
			left_right = line.split(SPACE);
			if (left_right[0].contains("T")) {
				this.createDefaultInitialAndHiddenLayers(left_right[1]);
			}
			line = breader.readLine();
		}
	}
	private void createDefaultInitialAndHiddenLayers(String right) {
		String[] topology = right.split(COMMA);
		int numOfHiddenLayers = topology.length - 1;

		// Empty.

		Layer initial_layer = new Layer(Layer.INITIAL_LAYER);
		this.layers.add(initial_layer);

		this.completeHiddenLayers(numOfHiddenLayers);

		// Fill them.

		String label = "";

		for (int at = 0; at < topology.length; at += 1) {
			Integer num_hidden_neurons = stringToInteger(topology[at]);
			for (int n = 1; n <= num_hidden_neurons; n += 1) {
				Neuron neuron = null;
				InputNode bias = null;
				Layer input_data_layer = this.layers.get(0);
				// Initial.

				if (at == 0) {
					neuron = new Neuron(this.weightedSum,
                                         this.sigmoid,
                                         initial_layer,
                                         "Neuron " + this.currentNeuron);
					bias = new InputNode(input_data_layer,
                                          "Bias for " + neuron.getLabel(),
                                          InputNode.BIAS_NODE());
					initial_layer.addNode(neuron);
					input_data_layer.addNode(bias);

				// Hidden.

				} else {
					neuron = new Neuron(this.weightedSum,
                                         this.sigmoid,
                                         this.layers.get(at + 1),
                                         "Neuron " + this.currentNeuron);
					bias = new InputNode(input_data_layer,
                                          "Bias for " + neuron.getLabel(),
                                           InputNode.BIAS_NODE());
					this.layers.get(at + 1).addNode(neuron);
					input_data_layer.addNode(bias);
				}
				label =  "Connection: " + bias.getLabel() + " -> " + neuron.getLabel();
				this.network.createConnection(bias, neuron, -1, label);

				this.currentNeuron += 1;
			}
		}
	}
	private void createDefaultOutputLayer() {
		Layer output_layer = new Layer(Layer.OUTPUT_LAYER);
		this.layers.add(output_layer);

		Integer num_output_neurons = this.benchmark.getTotalOutputs();
		String label = "";

		for (int i = 0; i < num_output_neurons; i += 1) {
			Layer input_data_layer = this.layers.get(0);
			Neuron output_neuron = new Neuron(this.weightedSum,
                                               this.sigmoid,
                                               output_layer,
                                               "Neuron " + this.currentNeuron);
			InputNode bias = new InputNode(input_data_layer,
                                            "Bias for " + output_neuron.getLabel(),
                                            InputNode.BIAS_NODE());
			output_layer.addNode(output_neuron);
			input_data_layer.addNode(bias);

			label =  "Connection: " + bias.getLabel() + " -> " + output_neuron.getLabel();
			this.network.createConnection(bias, output_neuron, -1, label);

			this.currentNeuron += 1;
		}
	}
	private void fullyConnectNetwork() {
		String label = "";
		List<InputNode> bias_nodes = this.layers.get(0).getBiasNodes();

		for (int i = 0; i < (this.layers.size() - 1); i += 1) {
			Layer at = this.layers.get(i);
			Layer next = this.layers.get(i + 1);

			for (Node node : at.getNodes()) {

				if (!bias_nodes.contains(node)) {
					for (Neuron neuron : next.getNeurons()) {

						label = "Connection: " + node.getLabel() + " -> "
								+ neuron.getLabel();
						this.network.createConnection(node, neuron, 1.0, label);
					}
				}
			}
		}
		// PROBEN1 recommends the range [-0.1, 0.1]

		Random generator = new Random();
		this.network.randomizeAllWeights(-0.1, 0.1, generator);
	}

	// Custom Network structure.

	private void parseCustomNetStructure(BufferedReader breader, String last_line) throws IOException {
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

		Layer input_data_layer = new Layer(Layer.INPUT_DATA_LAYER),
              initial_layer = new Layer(Layer.INITIAL_LAYER),
              output_layer = new Layer(Layer.OUTPUT_LAYER);
		this.layers.add(input_data_layer);
		this.layers.add(initial_layer);

		this.completeHiddenLayers(numOfHiddenLayers);

		this.layers.add(output_layer);

		int currentNeuron = 1;
		for (int i=0; i < split.length; i++) {
			Integer neuronsInLayer = stringToInteger(split[i]);
			for (int j=1; j <= neuronsInLayer; j++) {
				Neuron neuron = null;

				// Initial.

				if (i == 0) {
					neuron = new Neuron(weightedSum, sigmoid, initial_layer, "Neuron " + currentNeuron);
					initial_layer.addNode(neuron);

				// Output.

				} else if (i == split.length - 1) {
					neuron = new Neuron(weightedSum, sigmoid, output_layer, "Neuron " + currentNeuron);
					output_layer.addNode(neuron);

				// Hidden.

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
		if (numOfHiddenLayers > 0) {
			for (int i = 0; i < numOfHiddenLayers; i++) {
				Layer hiddenLayer = new Layer(Layer.HIDDEN_LAYER);
				this.layers.add(hiddenLayer);
			}
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
						"Connection: " + bias.getLabel() + " -> " + "Neuron" + (i + 1));
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

				// input data must be filled with sample data
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


// Format.


	private Double stringToDouble(String value) {
		return Double.parseDouble(value);
	}

	private Integer stringToInteger(String value) {
		return Integer.valueOf(value);
	}
}