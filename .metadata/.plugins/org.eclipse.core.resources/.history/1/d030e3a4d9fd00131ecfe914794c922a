package core;

import java.util.List;
import java.util.Random;

/*
 * Represents a collection of Layers.
 */

public class NeuralNetwork {
	
	private List<Layer> layers;
	

// Creation.


	public NeuralNetwork(List<Layer> layers) {
		this.layers = layers;
	}

	
// Processing.
	
	
	public void computeOutput() {
		
		for (Layer layer: this.layers) {
			layer.computeOutput();
		}
	}
	
	/* TODO
	public void learn(DataSet trainingSet){
		
	}*/
	

// Configuration.


	public List<Layer> getLayers() {
		return this.layers;
	}
	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}
	public int numberOfLayers() {
		return this.layers.size();
	}
	
	public boolean addLayer(Layer layer) {
		if (!this.layers.contains(layer)) {
			this.layers.add(layer);
			return true;
		}
		return false;
	}
	public boolean removeLayer(Layer layer) {
		if (this.layers.contains(layer)) {
			this.layers.remove(layer);
			return true;
		}
		
		return false;
	}
	
	public Layer getLayerAt(int at) {
		return this.layers.get(at);
	}
	public Layer getInitialLayer() {
		Layer initial_layer = null;

		// Look in layers.
		
		for (Layer layer: this.layers) {
			
			// Found ?
			
			if (layer.isInitialLayer()) {
				initial_layer = layer;
				break;
			}
		}
		return initial_layer;
	}
	public Layer getOutputLayer() {
		Layer output_layer = null;

		// Look in layers.
		
		for (Layer layer: this.layers) {
			
			// Found ?
			
			if (layer.isOutputLayer()) {
				output_layer = layer;
				break;
			}
		}
		return output_layer;
	}
	
	// Randomization.
	
	public void randomizeWeights(double min, double max, Random generator) {
		if (!this.layers.isEmpty()) {
			
			for (Layer layer: this.layers) {
				layer.randomizeWeights(min, max, generator);
			}
		}
	}
}