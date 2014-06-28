package core.propagation;

import java.util.List;

import core.Connection;

abstract public class PropagationFunction {

	public abstract double getOutput(List<Connection> inputs);
}
