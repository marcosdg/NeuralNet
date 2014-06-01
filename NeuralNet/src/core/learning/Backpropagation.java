package core.learning;

import java.util.ArrayList;
import java.util.List;

import core.Connection;
import core.Layer;
import core.NeuralNetwork;
import core.Neuron;
import core.Weight;

public class Backpropagation
{
	private NeuralNetwork neuralNetwork;
	public static final double MOMENTUM = 0.0;
	
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
	
	public void backPropagation(List<Double> expectedOutputs) {
		List<Connection> hiddenOutputs = new ArrayList<Connection>();
		List<Neuron> hiddenNeurons = new ArrayList<Neuron>();
		double deltaWeight = 0.0,
				error = 0.0;

		Layer lastLayer = this.neuralNetwork.getOutputLayer();
		ArrayList<Neuron> lastNeurons = lastLayer.getNeurons();
		
		if (expectedOutputs.size() != lastNeurons.size()) {
			throw new IllegalArgumentException("Wrong number of output parameters");
		}
		
		Neuron lastNeuron;
		
		for (int i = 0; i < lastNeurons.size(); i++) {
			lastNeuron = lastNeurons.get(i);
			error = LearningRule.getDelta(lastNeuron, expectedOutputs.get(i));
			lastNeuron.setError(error);
		}
		
		List<Layer> layers = this.neuralNetwork.getLayers();
		
		/**
		 * Excluding last layer and init layer
		 */
		for(int i = layers.size() - 1; i > 0; i--) {
			hiddenNeurons = layers.get(i).getNeurons();
			
			for (Neuron hiddenNeuron : hiddenNeurons) {
				
				//g'(ini) * SUM(wji * deltai)
				
				error = LearningRule.getDelta(hiddenNeuron);
				hiddenNeuron.setError(error);
				
				// Update hidden neuron weights.
				
				hiddenOutputs = hiddenNeuron.getOutputs();
				for (Connection hiddenOutput : hiddenOutputs) {
					
					// Get its delta.
					
					deltaWeight = LearningRule.LEARNING_FACTOR * 
                                  hiddenNeuron.getOutput() *
                                  hiddenOutput.getTarget().getError();
					
					// Add it to old weight.
					
					//TODO: Add momentum
					Weight oldWeight = hiddenOutput.getWeight();
					oldWeight.setValue(oldWeight.getValue() + deltaWeight);
				}
			}
		}
	}
}
