package core.propagation;

import java.util.List;

import core.Connection;

public abstract class PropagationFunction {

	public abstract double getOutput(List<Connection> inputs);
}
