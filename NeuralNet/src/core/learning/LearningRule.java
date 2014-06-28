package core.learning;

import core.NeuralNetwork;
import core.Neuron;
import core.data.DataSet;
import core.util.ConnectionHelper;

abstract public class LearningRule {

	private NeuralNetwork neuralNetwork;
	private DataSet data; // training + validation + test sets

	public LearningRule() {
	}

	public NeuralNetwork getNeuralNetwork() {
		return this.neuralNetwork;
	}
	public void setNeuralNetwork(NeuralNetwork that) {
		this.neuralNetwork = that;
	}

	public DataSet getData() {
		return this.data;
	}
	public void setData(DataSet that) {
		this.data = that;
	}

	abstract public void apply(DataSet data);

	// TODO: DEVELOPMENT



	/* ~ Manu
	public static double getDelta(Neuron neuron, double expectedResult)
	{
		return (neuron.getOutputDerived() * (expectedResult - neuron.getOutput()));
	}

	public static double getDelta(Neuron neuron)
	{
		double delta = ConnectionHelper.getWeightedSumWithDeltas(neuron.getOutputs());

		return neuron.getOutputDerived() * delta;
	}*/
}