package core.learning;

import core.Neuron;
import core.util.ConnectionHelper;

public class LearningRule {
	public static final double LEARNING_FACTOR = 0.0;

	public static double getDelta(Neuron neuron, double expectedResult)
	{
		return (neuron.getOutputDerived() * (expectedResult - neuron.getOutput()));		
	}
	
	public static double getDelta(Neuron neuron)
	{
		double delta = ConnectionHelper.getWeightedSumWithDeltas(neuron.getOutputs());
		
		return neuron.getOutputDerived() * delta;
	}
}