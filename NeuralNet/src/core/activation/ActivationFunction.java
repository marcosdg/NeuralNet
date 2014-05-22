package core.activation;

/* Base class for all activation functions. 
 */
public abstract class ActivationFunction {

	/**
	 * @param netInput
	 *            Propagation function result.
	 * @return Activation function output.
	 */
	abstract double getOutput(double netInput);

}
