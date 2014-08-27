package experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.NeuralNetwork;

import experiment.data.Benchmark;
import experiment.data.NeuralNetworkParser;
import experiment.data.ProbenFileParser;

public class Experiment {

	private int num_runs;
	private boolean run_ready;

	// Parsers.

	private ProbenFileParser proben_parser;
	private NeuralNetworkParser net_parser;

	// Neural Network.

	private NeuralNetwork net;
	private boolean net_ready;

	// Benchmarks.

	private List<Benchmark> benchs;
	private List<String> proben_files;
	private boolean benchs_ready,
                      benchs_passed;

	// Save results from each run individually.

	private List<Statistics> stats;


// Creation.


	public Experiment(String net_dir, String net_file, String proben_dir,
                         List<String> proben_files) {

		if (proben_files.isEmpty()) {
			throw new IllegalArgumentException("Must feed in at least one proben file");
		} else {

			this.num_runs = 0;
			this.run_ready = false;

			// Initially, the first PROBEN file is set up and the rest will be
			// later processed.

			this.proben_parser = new ProbenFileParser(proben_dir,
                                                       proben_files.get(0));
			this.net_parser = new NeuralNetworkParser(net_dir, net_file);

			this.net_ready = false;

			this.benchs = new ArrayList<Benchmark>();
			this.proben_files = proben_files;
			this.benchs_ready = false;
			this.benchs_passed = false;

			this.stats = new ArrayList<Statistics>();
		}
	}


// Processing.


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

	// Loads the net with the the associated configuration file and the
	// first PROBEN file.

	public NeuralNetwork loadNeuralNetwork() {
		if (!this.benchs_ready) {
			throw new IllegalStateException("Benchmarks must be loaded first");
		} else {
			this.net_parser.setBenchmark(this.benchs.get(0));
			this.net_parser.parse();
			this.net = this.net_parser.getNeuralNetwork();
			this.net_ready = true;
		}
		return this.net;
	}

	public void trainNeuralNetwork() {
		if (!this.net_ready) {
			throw new IllegalStateException("Neural net must be loaded first");
		} else {
			// Train 1st one.

			this.net.learn();

			// The rest of them.

			List<Benchmark> left = this.benchs.subList(1, this.benchs.size());

			for (Benchmark bench: left) {
				this.net.getLearningRule().setBenchmark(bench);
				this.net.learn();
			}
			this.benchs_passed = true;
		}
	}

	public void run() {
		if (!this.run_ready) {
			throw new IllegalArgumentException("Set the number of runs first");
		} else {
			for (int run = 0; run < this.num_runs; run += 1) {

				// Learning.

				this.trainNeuralNetwork();

				// Save results.

				Statistics stat = new Statistics(this.net.copy());
				this.stats.add(stat);

				// Prepare next run.

				this.rebootNet();
			}
		}
	}


	/* TODO
	public void writeStatistics() {
		if (!this.benchs_passed) {
			throw new IllegalStateException("Neural net must be trained first");
		} else {
		}
	}
	*/


// Runs.


	public int getNumberOfRuns() {
		return this.num_runs;
	}

	// PROBEN1 recommends to set them to a power of 10 or 30.

	public void setNumberOfRuns(int num_runs) {
		if (num_runs <= 0) {
			throw new IllegalArgumentException("Experiment must be run at least once");
		} else {
			this.num_runs = num_runs;
			this.run_ready = true;
		}
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

	// For an appropriate distribution of statistical results is necessary
	// to re-initialize net's weights.

	public void rebootNet() {
		Random generator = new Random();
		this.net.randomizeAllWeights(-0.1, 0.1, generator);
		this.net.getLearningRule().setBenchmark(this.benchs.get(0));
	}


// Benchmarks.


	public List<Benchmark> getBenchmarks() {
		return this.benchs;
	}


// Stats.


	public List<Statistics> getStats() {
		return this.stats;
	}
}
