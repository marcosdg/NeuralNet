package core.activation;

public class Linear extends ActivationFunction {

	private double slope;


// Creation.
	
	
	public Linear(double slope) {
		this.slope = slope;
	}


// Configuration.

	
	@Override
	public double getOutput(double netInput) {

		// y = m * x
		return this.slope * netInput;
	}
	@Override
	public double getOutputDerived(double netInput) {
		
		// y' = m		
		return this.slope;
	}
	
	public double getSlope() {
		return this.slope;
	}
	public void setSlope(double slope) {
		this.slope = slope;
	}
}
