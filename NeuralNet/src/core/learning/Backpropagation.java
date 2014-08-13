package core.learning;

import java.util.ArrayList;
import java.util.List;

import core.Layer;
import core.data.Sample;


//TODO: COMPLETE BACKPROPAGATION CLASS


public class Backpropagation extends SupervisedLearning {

	private double momentum;


// Creation.


	public Backpropagation() {
	}


// Processing.


	@Override
	public void apply() {
		List<Double> output_vector,
                     output_errors;

		while (!getMaxEpochsStop().isMet()) {

			List<Sample> training_samples = getBenchmark().getTrainingSamples();

			for (Sample training_sample: training_samples) {

				output_vector = forwardPropagate(training_sample);

				output_errors = getOutputErrors(training_sample.getDesiredOutputVector(),
                                                output_vector);
				backwardPropagate(output_errors);
			}
			// did pass k (strip-length) epochs ?
			//   then check EarlyStop
		}

	}

	public List<Double> forwardPropagate(Sample training_sample) {
		return getNeuralNetwork().computeOutput(training_sample);
	}
	public void backwardPropagate(List<Double> output_errors) {

	}

	/* We assume that both vectors come in order, so that:
	 *
	 * 	desired_output_vector[i] <--> output_vector[i] <--> neurons[i]
	*/
	public List<Double> getOutputErrors(List<Double> desired_output_vector,
                                         List<Double> output_vector) {

		Layer output_layer = getNeuralNetwork().getOutputLayer();

		List<Double> output_errors = new ArrayList<Double>(),
                     outputs_derived = output_layer.getOutputsDerived();

		Double desired_output,
               output,
               output_derived,
               output_error;

		for (int i = 0; i < output_layer.numberOfNodes(); i += 1) {
			desired_output = desired_output_vector.get(i);
			output = output_vector.get(i);
			output_derived = outputs_derived.get(i);

			output_error = output_derived * (desired_output - output);
			output_errors.add(output_error);
		}
		return output_errors;
	}


	// Weights update.


// Momentum configuration.


	public double getMomemtum() {
		return this.momentum;
	}
	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}

	/* ~ Manu


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


		 // Excluding last layer and init layer

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
	*/
}