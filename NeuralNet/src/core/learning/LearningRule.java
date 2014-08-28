package core.learning;

import core.NeuralNetwork;
import experiment.data.Benchmark;

abstract public class LearningRule {

	private Benchmark benchmark;
	private NeuralNetwork neuralNetwork,

	/*
	 * Initially, 'best_net' will be a copy of 'neuralNetwork'.
	 * Because of EarlyStop, that will change during the learning process.
	 */
                           best_net;


// Creation.


	public LearningRule(Benchmark bench) {
		if (bench == null) {
			throw new IllegalArgumentException("Bad parameters");
		} else {
			this.benchmark = bench;
		}
	}

	public NeuralNetwork getNeuralNetwork() {
		return this.neuralNetwork;
	}
	public void setNeuralNetwork(NeuralNetwork that) {
		this.neuralNetwork = that;
	}
	public NeuralNetwork getBestNeuralNetwork() {
		return this.best_net;
	}
	public void setBestNeuralNetwork(NeuralNetwork that) {
		this.best_net = that;
	}

	public Benchmark getBenchmark() {
		return this.benchmark;
	}
	public void setBenchmark(Benchmark that) {
		this.benchmark = that;
	}

	abstract public void apply();
}