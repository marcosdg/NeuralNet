package core.activation;

import java.util.List;

import cor.util.ConnectionHelper;
import core.Connection;

public class ActivationFunction {
	
	/**
	 * @param connections
	 * @return neuron's output normalized
	 */
	public double activationFuntion(List<Connection> connections)
	{
		double totalInput = ConnectionHelper.getInputSum(connections);
		
		return (1/(1+ Math.pow(Math.E, -totalInput)));
	}
}
