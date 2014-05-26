package core.activation;

public class Sigmoid extends ActivationFunction {

	@Override
	public double getOutput(double netInput) {
		return (1/1+(Math.pow(Math.E, (-netInput))));
	}
	
	public double getOutputDerivative(double netInput)
	{
		double output = this.getOutput(netInput);
		
		return (output * (1 - output));
	}

}
