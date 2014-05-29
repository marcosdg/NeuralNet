package core;

import core.util.ConnectionHelper;

public class LearningRule
{
	public static double getDelta(Neuron neuron, double expectedResult)
	{
		return (neuron.getDerivateOutput() * (expectedResult - neuron.getOutput()));		
	}
	
	public static double getDelta(Neuron n)
	{
		double delta = ConnectionHelper.getWeightedSumWithDeltas(n.getOutputs());
		
		return n.getDerivateOutput() * delta;
	}
}
