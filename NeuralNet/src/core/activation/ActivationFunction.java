package core.activation;

public abstract class ActivationFunction {
	
	abstract public double getOutput(double netInput);

	abstract public double getOutputDerived(double netInput);
}
