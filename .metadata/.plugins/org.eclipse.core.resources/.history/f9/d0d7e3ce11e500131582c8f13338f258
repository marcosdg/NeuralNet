package core;

public class LearningRule
{
	public double getDelta(Neuron n, int expectedResult)
	{
		double neuronOutput = n.getOutput();
		
		return (n.getDerivateOutput() * (expectedResult - neuronOutput));		
	}
}
