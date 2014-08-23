package test;

import core.NeuralNetwork;

public class TestNeuralNetworkPerformance {

	private NeuralNetwork trained_net;


// Creation.


	public TestNeuralNetworkPerformance(NeuralNetwork net) {
		if (!net.isTrained()) {
			throw new IllegalArgumentException("Must train the net first");
		} else {
			this.trained_net = net;
		}
	}
}