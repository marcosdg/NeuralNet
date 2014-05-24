package core;

import java.util.Random;

public class Weight {

	private double value;

	
// Creates a Weight.
	
	public Weight(double initial) {
		this.value = initial;
	}
	
// Creates a random Weight within a given interval.
	
	public Weight(double min, double max, Random generator) {
		this.value = this.randomWithin(min, max, generator);
	}
	
// Value configuration.
	
	public void decrement(double amount) {
		this.value -= amount;
	}
	public void increment(double amount) {
		this.value += amount;
	}
	
	public double getValue() {
		return this.value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	// Random within interval.
	
	public double randomWithin(double min, double max, Random generator) {
		double random = 0.0;
		if (generator != null) {
			
			random = (min + (generator.nextDouble() * (max - min))); 
		}
		return random;
	}
	
	// Randomize Weight value.
	
	public void randomize(double min, double max, Random generator) {
		this.value = this.randomWithin(min, max, generator);
	}
	
	public String toString() {
		return String.valueOf(value);
	}
}
