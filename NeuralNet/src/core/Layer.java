package core;

import java.util.ArrayList;

public class Layer {
	private String label;
	private ArrayList<Neuron> neurons;
	
	public Layer() {
		this.neurons = new ArrayList<Neuron>();
	}
	
	public Layer(ArrayList<Neuron> neurons) {
		this.neurons = neurons;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(ArrayList<Neuron> neurons) {
		this.neurons = neurons;
	}
	
	public void addNeuron(Neuron neuron) {
		
		if (!this.neurons.contains(neuron))
			this.neurons.add(neuron);
	}
	
	public void addNeuron(int index, Neuron neuron) {
		
		if (!this.neurons.contains(neuron))
			this.neurons.add(index, neuron);
	}
	
	public boolean removeNeuronAtPosition(int index, Neuron neuron) {
		
		if (this.neurons.contains(neuron)) {
			this.neurons.remove(index);
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
}
