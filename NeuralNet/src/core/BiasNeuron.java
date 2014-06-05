package core;

public class BiasNeuron
{
	private ConnectionBias output;
	private double netInput;
	
	public BiasNeuron(ConnectionBias connection, double input)
	{
		this.output = connection;
		this.netInput = input;
	}

	public ConnectionBias getOutput() {
		return output;
	}

	public void setOutput(ConnectionBias output) {
		this.output = output;
	}

	public double getNetInput() {
		return netInput;
	}

	public void setNetInput(double netInput) {
		this.netInput = netInput;
	}
	
}
