package core.learning.error;

public class MeanSquaredError extends SquaredError {


// Creation.


	public MeanSquaredError() {

		// initialize desired and output vectors.
		super();
	}


//   [SUM{i,n}: (desired{i} - output{i}) ^ 2] / n


	public Double getMeanSquaredError() {

		int n = this.getOutputVector().size();

		return this.getSquaredError() / n;
	}
}