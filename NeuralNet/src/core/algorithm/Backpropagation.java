package core.algorithm;

import java.util.ArrayList;
import java.util.List;

import core.BiasNeuron;
import core.Connection;
import core.ConnectionBias;
import core.Layer;
import core.LearningRule;
import core.NeuralNetwork;
import core.Neuron;
import core.Weight;

public class Backpropagation
{
	private NeuralNetwork neuralNetwork;
	
	public Backpropagation(NeuralNetwork neuralNetwork)
	{
		this.neuralNetwork = neuralNetwork;
	}
	
	public void forwardPropagation(List<Double> netInput)
	{
		Layer init = this.neuralNetwork.getInitLayer();
		ArrayList<Neuron> neurons = init.getNeurons();
		
		if (netInput.size() != neurons.size()) {
			throw new IllegalArgumentException("Wrong number of input parameters");
		}
		
		/**
		 * This bucle propagates the input sample to the second layer
		 */
		for (int i = 0; i < neurons.size(); i++) {
			neurons.get(i).setNetInput(netInput.get(i));
		}
		
		List<Layer> layers = this.neuralNetwork.getLayers();
		
		/**
		 * Excluding last layer and init layer
		 */
		for(int i = 1; i < layers.size() - 1; i++) {
			neurons = layers.get(i).getNeurons();
			for (Neuron n : neurons) {
				// ini
				n.computeNeuronInput();
				// ai <- g(ini)
				n.computeOutput();
			}
		}
	}
	
	public void backPropagation(List<Double> expectedOutputs)
	{
		Layer init = this.neuralNetwork.getOutputLayer();
		ArrayList<Neuron> neurons = init.getNeurons();
		
		if (expectedOutputs.size() != neurons.size()) {
			throw new IllegalArgumentException("Wrong number of output parameters");
		}
		
		Double error;
		Neuron currentNeuron;
		
		for (int i = 0; i < neurons.size(); i++) {
			currentNeuron = neurons.get(i);
			error = LearningRule.getDelta(currentNeuron, expectedOutputs.get(i));
			currentNeuron.setError(error);
		}
		
		List<Layer> layers = this.neuralNetwork.getLayers();
		List<Connection> connections;
		
		/**
		 * Excluding last layer and init layer
		 */
		for(int i = layers.size() - 1; i < 0; i--) {
			neurons = layers.get(i).getNeurons();
			for (Neuron n : neurons) {
				//g'(ini)*sum(wji*deltai)
				error = LearningRule.getDelta(n);
				n.setError(error);
				//Update weights
				connections = n.getOutputs();
				for (Connection c : connections) {
					//n * aj * deltai
					double deltaWeight = LearningRule.LEARNING_FACTOR * n.getOutput() * c.getTarget().getError();
					Weight updatedWeight = c.getWeight();
					deltaWeight += NeuralNetwork.MOMENTUM * updatedWeight.getDeltaValue();
					updatedWeight.setDeltaValue(deltaWeight);
					updatedWeight.setValue(updatedWeight.getValue() + deltaWeight);
				}
				
				//Update bias weight
				if (n.getBias() != null) {
					ConnectionBias bias = n.getBias();
					BiasNeuron biasNeuron = bias.getSource();
					double deltaWeight = LearningRule.LEARNING_FACTOR * biasNeuron.getNetInput() * bias.getTarget().getError();
					Weight updatedWeight = bias.getWeight();
					deltaWeight += NeuralNetwork.MOMENTUM * updatedWeight.getDeltaValue();
					updatedWeight.setDeltaValue(deltaWeight);
					updatedWeight.setValue(updatedWeight.getValue() + deltaWeight);
				}
			}
		}
	}
}