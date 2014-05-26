package core.util;

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
	
	public static double getOutputWeightedSumWithDelta(List<Connection> connections, double previousDelta)
	{
		double total = 0.0;
		
		for (int i = 0; i < connections.size(); i++) {
			total += connections.get(i).getWeight().getValue() * previousDelta;
		}
		
		return total;
	}
}
