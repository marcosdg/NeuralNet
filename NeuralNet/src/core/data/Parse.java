package core.data;

import java.util.*;

import core.Connection;
import core.Layer;
import core.NeuralNetwork;
import core.Neuron;
import core.Weight;
import core.activation.ActivationFunction;
import core.activation.Sigmoid;
import core.input.WeightedSum;

public class Parse {

	public static void main(String[] args) {
		Integer neurons,
				inputs,
				outputs;
		NeuralNetwork nw;
		List<Layer> layers = new ArrayList<Layer>();
		ActivationFunction af = new Sigmoid();
		WeightedSum ws = new WeightedSum();
		
		/* DO NOT UNCOMMENT UNTIL BIAS NEURON IS ADDED
		ParseSamples ps = new ParseSamples();
		Benchmark b = ps.parseFile("/home/manu/cancer1.dt");
		System.out.println("Ejemplos parseados: " + b.getSamples().size());
		
		inputs = b.getSamples().get(0).getInputs().size();
		outputs = b.getSamples().get(0).getOutputs().size();
		
		Layer initLayer = new Layer();
		Layer outputLayer = new Layer();
		Layer lastLayer = null;
		
		// Init Layer 
		for (int i = 0; i < inputs; i++) {
			Neuron n = new Neuron(ws, af, "Init Layer Neuron " + i, null);
			initLayer.addNeuron(n);
		}
		initLayer.setLabel(Layer.INIT_LAYER);
		layers.add(initLayer);
		

		// Hidden Layers
		Scanner scanner = new Scanner(System.in);
		Boolean moreLayers = true;
		while (moreLayers) {
			System.out.println("Introduzca el número de neuronas en esta capa oculta o escriba FIN:");
			String keyString = scanner.nextLine();
			if (keyString.contains("FIN")) {
				moreLayers = false;
				break;
			}
			neurons = Integer.parseInt(keyString);
			Layer hiddenLayer = new Layer();
			hiddenLayer.setLabel(Layer.HIDDEN_LAYER);
			for (int i = 0; i < neurons; i++) {
				//TODO: Add ConnectionBias
				Neuron ni = new Neuron(ws, af, "Hidden Layer Neuron " + i, null);
				lastLayer = getLastLayer(layers);
				for (int j = 0; j < lastLayer.getNeurons().size(); j++) {
					Neuron nj = lastLayer.getNeurons().get(j);
					Connection cij = new Connection(nj, ni, new Weight(0.0));
					nj.addOutputConnection(cij);
					ni.addInputConnection(cij);
				}
				hiddenLayer.addNeuron(ni);
			}
			layers.add(hiddenLayer);
		}
		
		// Output Layer
		for (int i = 0; i < outputs; i++) {
			Neuron ni = new Neuron(ws, af, "Init Layer Neuron " + i, null);
			outputLayer.addNeuron(ni);
			lastLayer = getLastLayer(layers);
			for (int j = 0; j < lastLayer.getNeurons().size(); j++) {
				Neuron nj = lastLayer.getNeurons().get(j);
				Connection cij = new Connection(nj, ni, new Weight(0.0));
				nj.addOutputConnection(cij);
				ni.addInputConnection(cij);
			}
		}
		outputLayer.setLabel(Layer.OUTPUT_LAYER);
		layers.add(outputLayer);
		
		System.out.println("Número de capas totales: " + layers.size());
		for (Layer layer : layers) {
			System.out.println("Capa: " + layer.getLabel() + " Neuronas: " + layer.getNeurons().size());
		}
	*/
	}
	public static Layer getLastLayer(List<Layer> layers)
	{
		return layers.get(layers.size() - 1);
	}
	
}