package core;

import java.util.List;

public class NeuralNetwork
{
	private List<Layer> layers;
	
	public NeuralNetwork(List<Layer> layers)
	{
		this.layers = layers;
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}
	
	public boolean addLayer(Layer layer)
	{
		if (!this.layers.contains(layer)) {
			this.layers.add(layer);
			return true;
		}
		
		return false;
	}
	
	public boolean removeLayer(Layer layer)
	{
		if (this.layers.contains(layer)) {
			this.layers.remove(layer);
			return true;
		}
		
		return false;
	}
	
	public Layer getInitLayer()
	{
		Layer result = null;

		for (int i = 0; i < this.layers.size(); i++) {
			if (this.layers.get(i).getLabel() == Layer.INIT_LAYER) {
				result = this.layers.get(i);
			}
		}
		
		return result;
	}
	
	public Layer getOutputLayer()
	{
		Layer result = null;

		for (int i = 0; i < this.layers.size(); i++) {
			if (this.layers.get(i).getLabel() == Layer.OUTPUT_LAYER) {
				result = this.layers.get(i);
			}
		}
		
		return result;
	}
	
}
