package core;

import core.util.ConnectionHelper;

public class LearningRule
{
	public double getDelta(Neuron neuron, int expectedResult)
	{
		return (neuron.getDerivateOutput() * (expectedResult - neuron.getOutput()));		
	}
	
	public double getDelta(Neuron n, double previousDelta)
	{
		double delta = ConnectionHelper.getOutputWeightedSumWithDelta(n.getOutputs(), previousDelta);
		
		return n.getDerivateOutput() * delta;
	}
}
