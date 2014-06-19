package core;

import java.util.ArrayList;
import java.util.Random;


/* 
 * Represents a collection of Neurons.
 */

public class Layer {

	private ArrayList<Neuron> neurons;

	// Kinds.

	public static final String INIT_LAYER = "Initial layer",
                                 HIDDEN_LAYER = "Hidden layer",
                                 OUTPUT_LAYER = "Output layer";
	private String label;


// Creation.


	public Layer(ArrayList<Neuron> neurons, String label) {
		this.neurons = neurons;
		this.label = label;
	}


// Processing.

	
	public void computeOutput() {
		
		for (Neuron neuron: this.neurons) {
			neuron.computeOutput();
		}
	}
	

// Configuration.
	
	
	public ArrayList<Neuron> getNeurons() {
		return this.neurons;
	}
	public void setNeurons(ArrayList<Neuron> neurons) {
		this.neurons = neurons;
	}
	public int numberOfNeurons() {
		return this.neurons.size();
	}
	
	public void addNeuron(Neuron neuron) {
		if (!this.neurons.contains(neuron)) {
		
			this.neurons.add(neuron);
		}
	}
	public void addNeuron(int index, Neuron neuron) {
		if (!this.neurons.contains(neuron)) {
			
			this.neurons.add(index, neuron);
		}
	}
	
	public boolean removeNeuronAtPosition(int at) {
		if (this.neurons.get(at) != null) {
			
			this.neurons.remove(at);
			return true;
		}
		return false;
	}
	public boolean removeNeuron(Neuron neuron) {
		if (this.neurons.contains(neuron)) {
			
			this.neurons.remove(neuron);
			return true;
		}
		return false;
	}
	
	// Randomization.
	
	public void randomizeWeights(double min, double max, Random generator) {
		if (!this.neurons.isEmpty()) {

			for (Neuron neuron : this.neurons) {
				neuron.randomizeWeights(min, max, generator);
			}
		}
	}
	
	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}