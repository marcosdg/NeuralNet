package core.algorithm;

import java.util.ArrayList;
import java.util.List;

import core.Layer;
import core.NeuralNetwork;
import core.Neuron;

public class Backpropagation
{
	private NeuralNetwork neuralNetwork;
	
	public Backpropagation(NeuralNetwork neuralNetwork)
	{
		this.neuralNetwork = neuralNetwork;
	}
	
	public void propagation(List<Double> netInput)
	{
		Layer init = this.neuralNetwork.getInitLayer();
		ArrayList<Neuron> neurons = init.getNeurons();
		
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
}
