package core.learning;

import java.util.ArrayList;
import java.util.List;

import core.Connection;
import core.InputNode;
import core.Layer;
import core.NeuralNetwork;
import core.Neuron;
import core.Node;
import core.Weight;
import experiment.data.Benchmark;
import experiment.data.Sample;


public class Backpropagation extends SupervisedLearning {

	private double momentum;


// Creation.


	public Backpropagation(double learning_rate, double momentum, Benchmark bench) {

		// SupervisedLearning and LearningRule properties.

		super(learning_rate, bench);

		if (momentum < 0) {
			throw new IllegalArgumentException("Bad parameters");
		} else {
			this.momentum = momentum;
		}
	}
	public Backpropagation copy() {
		Backpropagation backprop_copy = new Backpropagation(this.getLearningRate(),
                                                             this.momentum,
                                                             this.getBenchmark());
		// SupervisedLearning properties.

		backprop_copy.setCurrentEpoch(this.getCurrentEpoch());
		backprop_copy.setNumberOfRelevantEpochs(this.getNumberOfRelevantEpochs());
		backprop_copy.setStopCriterias(this.getStopCriterias());

		List<Double> evas_copy = new ArrayList<Double>(),
                     buffer_etrs_copy = new ArrayList<Double>(),
                     etrs_copy = new ArrayList<Double>(),
                     gls_copy = new ArrayList<Double>(),
                     pks_copy = new ArrayList<Double>();
		List<List<Double>> training_output_vectors_copy = new ArrayList<List<Double>>(),
                           validation_output_vectors_copy = new ArrayList<List<Double>>();

		for (Double eva: this.getEvasRecord()) {
			evas_copy.add(eva);
		}
		for (Double etr: this.getEtrsBuffer()) {
			buffer_etrs_copy.add(etr);
		}
		for (Double etr: this.getEtrsRecord()) {
			etrs_copy.add(etr);
		}
		for (Double gl: this.getGLsRecord()) {
			gls_copy.add(gl);
		}
		for (Double pk: this.getPKsRecord()) {
			pks_copy.add(pk);
		}
		for (List<Double> training_output_vector: this.getTrainingOutputVectors()) {
			training_output_vectors_copy.add(new ArrayList<Double>(training_output_vector));
		}
		for (List<Double> validation_output_vector: this.getValidationOutputVectors()) {
			validation_output_vectors_copy.add(new ArrayList<Double>(validation_output_vector));
		}
		backprop_copy.setEvasRecord(evas_copy);
		backprop_copy.setEtrsBuffer(buffer_etrs_copy);
		backprop_copy.setEtrsRecord(etrs_copy);
		backprop_copy.setGLsRecord(gls_copy);
		backprop_copy.setPKsRecord(pks_copy);
		backprop_copy.setTrainingOutputVectors(training_output_vectors_copy);
		backprop_copy.setValidationOutputVectors(validation_output_vectors_copy);

		// LearningRule properties:
		// The Benchmark is the same (at copy-time) for the copy.
		// The best net will be set during training.

		backprop_copy.setBenchmark(this.getBenchmark());

		return backprop_copy;
	}


// Processing.


	@Override
	public void apply() {
		List<Sample> training_samples = getBenchmark().getTrainingSamples(),
                     validation_samples = getBenchmark().getValidationSamples();
		List<Double> output_errors = new ArrayList<Double>();

		while (!getMaxEpochsStop().isMet()) {

			for (Sample training_sample: training_samples) {

				output_errors = this.forwardPropagate(training_sample);
				this.backwardPropagate();
			}
			this.updateWeightsLastCorrections();
			this.saveEtrAndEva(validation_samples, output_errors);

			// Strip ?

			if ((getCurrentEpoch() != 0) &&
				(getCurrentEpoch() % getEarlyStop().getStripLength()) == 0) {
				boolean early_stop = getEarlyStop().isMet();

				saveGL(getEarlyStop().getCurrentGeneralizationLoss());
				savePK(getEarlyStop().getCurrentTrainingProgress());

				// What now ?

				if (early_stop) {
					break;
				} else {
					this.updateEarlyStopState();
				}
			}
			setCurrentEpoch(getCurrentEpoch() + 1);
		}
		getNeuralNetwork().setTrained(true);
		getBestNeuralNetwork().setTrained(true);
	}

	public void updateWeightsLastCorrections() {
		for (Weight weight: getNeuralNetwork().getAllWeights()) {
			weight.setLastCorrection(weight.getCorrection());
		}
	}
	public void saveEtrAndEva(List<Sample> validation_samples,
                                List<Double> output_errors) {
		List<Double> output_vector = null;
		Double etr = 0.0,
               eva = 0.0;

		// Save Etr in buffer & record.

		etr = getEarlyStop()
              .getAverageErrorPerTrainingSample(getTrainingOutputVectors());
		pushEtr(etr);
		saveEtr(etr);

		// Record Eva.

		for (Sample validation_sample: validation_samples) {
			output_vector = getNeuralNetwork().computeOutput(validation_sample);
			saveValidationOutputVector(output_vector);
		}
		eva = getEarlyStop()
              .getAverageErrorPerValidationSample(getValidationOutputVectors());
		saveEva(eva);
	}
	public void updateEarlyStopState() {
		double now_loss = getEarlyStop().getCurrentGeneralizationLoss(),
                best_loss = getEarlyStop().getBestGeneralizationLoss();

		// Better generalization loss ?

		if (now_loss < best_loss) {

			// Checkpoint.

			getEarlyStop().setBestGeneralizationLoss(now_loss);
			setNumberOfRelevantEpochs(getNumberOfRelevantEpochs() + 1);
			setBestNeuralNetwork(getNeuralNetwork().copy());

		} else if (now_loss > best_loss) {
			// Time-travel to the last checkpoint.

			setNeuralNetwork(getBestNeuralNetwork().copy());
		}
		flushBufferEtrs();
	}


// Forward.


	public List<Double> forwardPropagate(Sample training_sample) {

		// Propagate.

        List<Double> output_vector = getNeuralNetwork()
                                     .computeOutput(training_sample),
        // Compute out errors.

        desired_output_vector = training_sample.getDesiredOutputVector(),
        output_errors = this.getOutputErrors(desired_output_vector,
                                              output_vector);
        // Store them.

        List<Node> output_nodes = getNeuralNetwork().getOutputLayer().getNodes();
        double output_error = 0.0;

		for (int i = 0; i < output_nodes.size(); i += 1) {
			output_error = output_errors.get(i);
			((Neuron) output_nodes.get(i)).setError(output_error);
		}
		// To measure Etr after backpropagate.

		saveTrainingOutputVector(output_vector);

		return output_errors;
	}

	/* We assume that both vectors come in order, so that:
	 *
	 * 	desired_output_vector[i] <==> output_vector[i] <==> neurons[i]
	 */
	public List<Double> getOutputErrors(List<Double> desired_output_vector,
                                        List<Double> output_vector) {

		Layer output_layer = getNeuralNetwork().getOutputLayer();

		List<Double> output_errors = new ArrayList<Double>(),
                     outputs_derived = output_layer.getOutputsDerived();

		double desired_output = 0.0,
                output = 0.0,
                output_derived = 0.0,
                output_error = 0.0;

		for (int i = 0; i < output_layer.numberOfNodes(); i += 1) {
			desired_output = desired_output_vector.get(i);
			output = output_vector.get(i);
			output_derived = outputs_derived.get(i);

			output_error = output_derived * (desired_output - output);
			output_errors.add(output_error);
		}
		return output_errors;
	}


// Backward.


	public void backwardPropagate() {
		NeuralNetwork net = getNeuralNetwork();

		for (int l = (net.getNumberOfLayers() - 2); l > 0; l -= 1) {
			Layer from = net.getLayerAt(l);
			Layer to = net.getLayerAt(l + 1);
			this.correctWeights(from, to);
		}
		this.correctInputLayerWeights();
	}

	// Weights corrections.

	public void correctInputLayerWeights() {
		Weight weight = null;
		InputNode from = null;
		Neuron to = null;
		List<Connection> data_synapses = getNeuralNetwork().getInputDataSynapses(),
	                     bias_synapses = getNeuralNetwork().getBiasSynapses(),
	                     input_synapses = new ArrayList<Connection>();
		input_synapses.addAll(data_synapses);
		input_synapses.addAll(bias_synapses);

		for (Connection input_synapse: input_synapses) {
			weight = input_synapse.getWeight();
			from = (InputNode) input_synapse.getSource();
			to = (Neuron) input_synapse.getTarget();

			this.correctWeight(weight, from.getOutput(), to.getError());
		}
	}

	public void correctWeights(Layer from, Layer to) {
		NeuralNetwork net = getNeuralNetwork();
		List<Neuron> to_neurons = to.getNeurons(),
                     from_neurons = from.getNeurons();

		for (Neuron from_neuron: from_neurons) {
			from_neuron.setError(this.computeHiddenError(from_neuron, to));

			for (Neuron to_neuron: to_neurons) {
				this.correctWeight(net.getNeuroWeight(from_neuron, to_neuron),
			                       from_neuron.getOutput(),
			                       to_neuron.getError());
			}
		}
	}
	public void correctWeight(Weight weight, double output, double error) {

		double last_fix = this.getMomemtum() * weight.getLastCorrection(),
                now_fix = this.getLearningRate() * output * error,
                fix = now_fix + last_fix,
                fresh = weight.getValue() + fix;

		weight.setValue(fresh);
		weight.setCorrection(fix);
	}

	// Hidden errors.

	public Double computeHiddenError(Neuron from, Layer to) {
		return (from.getOutputDerived() * this.sumWeightedErrors(from, to));
	}
	public Double sumWeightedErrors(Neuron from_neuron, Layer to) {
		NeuralNetwork net = getNeuralNetwork();
		List<Neuron> to_neurons = to.getNeurons();
		Weight weight = null;
		double weighted_error = 0.0,
                total_weighted_error = 0.0;

		for (Neuron to_neuron: to_neurons) {

			weight = net.getNeuroWeight(from_neuron, to_neuron);
			weighted_error = weight.getValue() * to_neuron.getError();

			total_weighted_error += weighted_error;
		}
		return total_weighted_error;
	}


// Momentum configuration.


	public double getMomemtum() {
		return this.momentum;
	}
	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}
}