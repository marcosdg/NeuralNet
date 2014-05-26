package core;

import core.util.ConnectionHelper;

public class LearningRule
{
	public double getDelta(Neuron n, int expectedResult)
	{
		double neuronOutput = n.getOutput();
		
		return (n.getDerivateOutput() * (expectedResult - neuronOutput));		
	}
	
	public double getDelta(Neuron n, double previousDelta)
	{
		double delta = ConnectionHelper.getOutputWeightedSumWithDelta(n.getOutputs(), previousDelta);
		
		return n.getDerivateOutput() * delta;
	}
}
