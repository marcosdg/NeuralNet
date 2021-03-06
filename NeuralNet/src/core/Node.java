package core;

/*
 * We must distinguish between 'Neurons' (process data) and
 * 'Input Nodes' (capture data).
 *
 * Represents generalization of Neurons and InputNodes.
 */

public class Node {

	private Layer parentLayer;
	private String label;


// Creation.


	public Node(Layer parentLayer, String label) {
		if (parentLayer != null && label != null) {
			this.parentLayer = parentLayer;
			this.label = label;
		}
	}


// Layer configuration.


	public Layer getParentLayer() {
		return this.parentLayer;
	}
	public void setParentLayer(Layer parentLayer) {
		this.parentLayer = parentLayer;
	}


// Label configuration.

	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}