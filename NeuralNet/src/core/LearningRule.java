package core;

import core.util.ConnectionHelper;

public class LearningRule
{
	public static final double LEARNING_FACTOR = 0.0;

	public static double getDelta(Neuron neuron, double expectedResult)
	{
		return (neuron.getOutputDerived() * (expectedResult - neuron.getOutput()));		
	}
	
	public static double getDelta(Neuron n)
	{
		double delta = ConnectionHelper.getWeightedSumWithDeltas(n.getOutputs());
		
		return n.getOutputDerived() * delta;
	}
}