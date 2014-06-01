package core.activation;

public class Sigmoid extends ActivationFunction {

	@Override
	public double getOutput(double netInput) {
		return (1 / 1 + Math.exp(-netInput));
	}
	
	public double getOutputDerived(double netInput)
	{
		double output = this.getOutput(netInput);
		
		return (output * (1 - output));
	}
}
