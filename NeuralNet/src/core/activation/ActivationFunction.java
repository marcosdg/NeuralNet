package core.activation;

abstract public class ActivationFunction {
	
	abstract public double getOutput(double netInput);

	abstract public double getOutputDerived(double netInput);
}
