package core;

public class LearningRule
{
	public double getDelta(Neuron neuron, int expectedResult)
	{
		return (neuron.getDerivateOutput() * (expectedResult - neuron.getOutput()));		
	}
}
