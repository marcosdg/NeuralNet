package core.learning;

import core.NeuralNetwork;
import core.data.Benchmark;

//TODO: COMPLETE LEARNINGRULE CLASS

abstract public class LearningRule {

	private Benchmark benchmark;
	private NeuralNetwork neuralNetwork;

	public LearningRule() {
	}

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

	/* ~ Manu
	public static double getDelta(Neuron neuron, double expectedResult)
	{
		return (neuron.getOutputDerived() * (expectedResult - neuron.getOutput()));
	}

	public static double getDelta(Neuron neuron)
	{
		double delta = ConnectionHelper.getWeightedSumWithDeltas(neuron.getOutputs());

		return neuron.getOutputDerived() * delta;
	}*/
}