package test;

import java.util.List;

import core.NeuralNetwork;

public class TestNeuralNetworkPerformance {

	private NeuralNetwork trained_net;
	private List<Double> etts; // errors test set.


// Creation.


	public TestNeuralNetworkPerformance(NeuralNetwork net) {
		if (!net.isTrained()) {
			throw new IllegalArgumentException("Must train the net first");
		} else {
			this.trained_net = net;
		}
	}

}
