package cor.util;

import java.util.List;

import core.Connection;

public class ConnectionHelper
{
	public static double getInputSum(List<Connection> connections)
	{
		double totalInput = 0.0;

		for (Connection c : connections) {
			totalInput += c.getWeightedInput();
		}
		
		return totalInput;
	}
}
