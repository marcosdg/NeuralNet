package experiment;

import java.util.ArrayList;
import java.util.List;

import core.NeuralNetwork;

import experiment.data.Benchmark;
import experiment.data.NeuralNetworkParser;
import experiment.data.ProbenFileParser;

public class Experiment {

	private ProbenFileParser proben_parser;
	private NeuralNetworkParser net_parser;

	private NeuralNetwork net;
	private boolean net_ready;

	private List<Benchmark> benchs;
	private List<String> proben_files;
	private boolean benchs_ready;

// Creation.


	public Experiment(String net_dir, String net_file, String proben_dir,
                         List<String> proben_files) {

		if (proben_files.isEmpty()) {
			throw new IllegalArgumentException("Must feed in at least one proben file");
		}

		// Initially, the first PROBEN file is set up and the rest will be later
		// processed.

		this.proben_parser = new ProbenFileParser(proben_dir, proben_files.get(0));
		this.net_parser = new NeuralNetworkParser(net_dir, net_file);

		this.net_ready = false;

		this.benchs = new ArrayList<Benchmark>();
		this.proben_files = proben_files;
		this.benchs_ready = false;
	}


// Processing.


	// Loads the net with the the associated configuration file and the
	// first PROBEN file.

	public NeuralNetwork loadNeuralNetwork() {
		if (!this.benchs_ready) {
			throw new IllegalArgumentException("Benchmarks must be loaded first");
		} else {
			this.net_parser.setBenchmark(this.benchs.get(0));
			this.net_parser.parse();
			this.net = this.net_parser.getNeuralNetwork();
			this.net_ready = true;
		}
		return this.net;
	}

	public List<Benchmark> loadAllBenchmarks() {

		// Load 1st one.

		this.proben_parser.parse();
		this.benchs.add(this.proben_parser.getBenchmark());

		// The rest of them.

		List<String> left = this.proben_files.subList(1,
                                                      this.proben_files.size());

		for (String proben_file: left) {
			this.proben_parser.setProbenFile(proben_file);
			this.proben_parser.parse();
			this.benchs.add(this.proben_parser.getBenchmark());
		}
		this.benchs_ready = true;
		return this.benchs;
	}


// Parsers.


	public ProbenFileParser getProbenFileParser() {
		return this.proben_parser;
	}
	public NeuralNetworkParser getNeuralNetworkParser() {
		return this.net_parser;
	}


// Neural Network.

	public NeuralNetwork getNeuralNetwork() {
		return this.net;
	}


// Benchmarks.

	public List<Benchmark> getBenchmarks() {
		return this.benchs;
	}


}
