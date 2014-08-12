package core.activation;

public class Sigmoid extends ActivationFunction {

	public Sigmoid() {
	}

	@Override
	public double getOutput(double netInput) {
		return (1 / (1 + Math.exp(-netInput)));
	}

	@Override
	public double getOutputDerived(double netInput) {
		double output = this.getOutput(netInput);

		return (output * (1 - output));
	}
}
