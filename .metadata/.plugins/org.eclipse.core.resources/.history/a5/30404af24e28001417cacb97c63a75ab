package core.learning;

import core.NeuralNetwork;
import core.data.Benchmark;

abstract public class LearningRule {

	private Benchmark benchmark;
	private NeuralNetwork neuralNetwork;


	public NeuralNetwork getNeuralNetwork() {
		return this.neuralNetwork;
	}
	public void setNeuralNetwork(NeuralNetwork that) {
		this.neuralNetwork = that;
	}

	public Benchmark getBenchmark() {
		return this.benchmark;
	}
	public void setBenchmark(Benchmark that) {
		this.benchmark = that;
	}

	abstract public void apply();
}