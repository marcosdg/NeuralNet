package core.data;

import java.util.ArrayList;
import java.util.List;

public class Sample
{
	private List<Double> inputs;
	private List<Double> outputs;
	
	public Sample()
	{
		this.inputs = new ArrayList<Double>();
		this.outputs = new ArrayList<Double>();
	}
	
	public void addInput(String input)
	{
		this.inputs.add(Double.parseDouble(input));
	}
	
	public void addOutput(String output)
	{
		this.outputs.add(Double.parseDouble(output));
	}

	public List<Double> getInputs() {
		return inputs;
	}

	public void setInputs(List<Double> inputs) {
		this.inputs = inputs;
	}

	public List<Double> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<Double> outputs) {
		this.outputs = outputs;
	}
	
}